package fr.damntools.jetbrainsplugins.editoractions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import fr.damntools.jetbrainsplugins.editoractions.models.Storage;
import fr.damntools.jetbrainsplugins.editoractions.processors.ReformatCodeProcessor;

import java.lang.String;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GenerateFieldCommentsAction
 *
 * @author xekg473
 * @version 1.0
 * @date 29/05/2018
 */
class GenerateFieldCommentsAction extends AnAction {

	@Override
	public void update(AnActionEvent e) {

	}

	@Override
	public void actionPerformed(AnActionEvent anActionEvent) {

		final Project project = anActionEvent.getProject();
		assert project != null;

		final Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
		assert editor != null;

		final Document document = editor.getDocument();
		final SelectionModel selectionModel = editor.getSelectionModel();

		if (selectionModel.hasSelection()) {

			WriteCommandAction.runWriteCommandAction(project, () -> {

				String selectedText = selectionModel.getSelectedText();
				assert selectedText != null;

				Matcher matcher = Pattern.compile("((private|public)(\\sstatic\\s|\\sfinal\\s|\\s)?([^\\s]+)\\s([^\\s]+).*;)").matcher(selectedText);
				StringBuilder newText = new StringBuilder();

				boolean matches = matcher.matches();

				while(matcher.find()) {
					String line = matcher.group();
					String fieldName = matcher.group(5);
					newText.append(String.format("/** %s */\n%s\n\n", fieldName, line));
				}
				if( matches)
					document.replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), newText.toString());
			});

			selectionModel.removeSelection();
			new ReformatCodeProcessor(project, PsiDocumentManager.getInstance(project).getPsiFile(document), ServiceManager.getService(project, Storage.class)).run();
		}
	}
}
