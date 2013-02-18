package za.ac.sun.cs.intlola;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;

public class IntlolaMonitor implements ILaunchListener {

	public void launchAdded(ILaunch launch) {
	}

	public void launchChanged(ILaunch launch) {
		ILaunchConfiguration config = launch.getLaunchConfiguration();
		String projectName = "";
		try {
			projectName = config.getAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, "");
		} catch (CoreException e) {
		}
		if (projectName != "") {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (project != null) {
				if (Intlola.getLocalStatus(project)) {
					IntlolaVisitor.copyOrTouchLocal(project, Intlola.LAUNCHED);
				}
//				else if (Intlola.getRemoteStatus(project)) {
//					IntlolaRemoteVistor.copyOrTouch);
//				}
			}
		}
	}

	public void launchRemoved(ILaunch launch) {
	}

}