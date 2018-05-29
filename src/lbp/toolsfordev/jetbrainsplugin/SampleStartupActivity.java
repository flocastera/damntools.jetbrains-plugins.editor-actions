package lbp.toolsfordev.jetbrainsplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

/**
 * SampleStartupActivity
 *
 * @author xekg473
 * @version 1.0
 * @date 29/05/2018
 * @copyright La Poste 2018
 */
public class SampleStartupActivity implements StartupActivity {

	@Override
	public void runActivity(@NotNull Project project) {
		// This code is executed after the project was opened.
		System.out.println("Hello World! Loaded project: " + project.getName());
	}
}
