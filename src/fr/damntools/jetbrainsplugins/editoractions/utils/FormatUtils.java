package fr.damntools.jetbrainsplugins.editoractions.utils;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import fr.damntools.jetbrainsplugins.editoractions.models.Storage;
import fr.damntools.jetbrainsplugins.editoractions.processors.ReformatCodeProcessor;

/**
 * FormatUtils
 *
 * @author XEKG473
 * @version 1.0
 * @date 08/06/2018
 * @copyright La Poste 2018
 */
public abstract class FormatUtils {

	public static void formatCode(Project project, Document document){
		new ReformatCodeProcessor(project, PsiDocumentManager.getInstance(project).getPsiFile(document), ServiceManager.getService(project, Storage.class)).run();
	}

}
