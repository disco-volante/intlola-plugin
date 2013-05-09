package za.ac.sun.cs.intlola.file;

import java.io.File;
import java.util.Calendar;

import com.google.gson.JsonObject;

public class IndividualFile implements IntlolaFile {

	private final boolean	hasContents;
	private final char		mod;
	private final int		num;

	private final String	path, name, pkg;

	private final long		time;

	public IndividualFile(final String path, final char mod, final int num,
			final boolean hasContents) {
		this.path = path;
		this.mod = mod;
		this.num = num;
		this.hasContents = hasContents;
		time = Calendar.getInstance().getTimeInMillis();
		final String[] spd = path.split(File.separator);
		name = spd[spd.length - 1];
		pkg = FileUtils.getPackage(spd, spd.length - 1, FileUtils.NAME_SEP);
	}

	public IndividualFile(final String path, final String name,
			final String pkg, final char mod, final int num, final long time,
			final boolean hasContents) {
		this.path = path;
		this.mod = mod;
		this.num = num;
		this.hasContents = hasContents;
		this.time = time;
		this.name = name;
		this.pkg = pkg;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public boolean hasContents() {
		return hasContents;
	}

	@Override
	public JsonObject toJSON() {
		final JsonObject ret = new JsonObject();
		ret.addProperty(Const.NAME, name);
		if (hasContents()) {
			final String ext = name.split("\\.")[1];
			ret.addProperty(Const.FTYPE, ext);
			if (ext.equals(Const.JAVA)) {
				ret.addProperty(Const.TYPE, Const.SRC);
			} else if (ext.equals(Const.CLASS)) {
				ret.addProperty(Const.TYPE, Const.EXEC);
			}
		} else {
			ret.addProperty(Const.FTYPE, Const.EMPTY);
			ret.addProperty(Const.TYPE, Const.CHANGE);
		}
		ret.addProperty(Const.PKG, pkg);
		ret.addProperty(Const.MOD, mod);
		ret.addProperty(Const.NUM, num);
		ret.addProperty(Const.TIME, time);
		return ret;
	}
}
