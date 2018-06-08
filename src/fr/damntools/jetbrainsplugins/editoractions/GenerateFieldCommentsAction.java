package fr.damntools.jetbrainsplugins.editoractions;


import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import fr.damntools.jetbrainsplugins.editoractions.utils.FormatUtils;

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

				Matcher matcher = Pattern.compile("(@[^\\s]+\\s+)?((private\\s|public\\s|protected\\s)?(static\\s|final\\s)*([^\\s]+)\\s([^\\s]+).*;)").matcher(selectedText);
				StringBuilder newText = new StringBuilder();
				boolean matches = false;

				while(matcher.find()) {
					String line = matcher.group();
					System.out.println("|" + line + "|");
					String fieldName = matcher.group(6);
					newText.append(String.format("\n/** %s %s */\n%s", getVisibilitySymbol(matcher.group(3)), fieldName, line));
					matches = true;
				}
				if( matches)
					document.replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), newText.toString());
			});

			selectionModel.removeSelection();
			FormatUtils.formatCode(project, document);
		}
	}

	private String getVisibilitySymbol(String visibility){
		System.out.println("|" + visibility + "|");
		if( visibility == null)
			return "~";
		switch (visibility){
			case "public ": return "+";
			case "private ": return "-";
			case "protected ": return "#";
			default: return "~";
		}
	}
}
