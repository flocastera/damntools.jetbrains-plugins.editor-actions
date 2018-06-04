package fr.damntools.jetbrainsplugins.editoractions.processors;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import fr.damntools.jetbrainsplugins.editoractions.models.Storage;

/**
 * ReformatCodeProcessor
 *
 * @author flocastera
 * @version 1.0
 * @date 02/06/2018
 */
public class ReformatCodeProcessor extends com.intellij.codeInsight.actions.ReformatCodeProcessor implements Processor {
	private static final String ID_CHANGED_TEXT = "ReformatChangedText";

	private static final String ID_ALL_TEXT = "ReformatAllText";

	private final Storage storage;

	public ReformatCodeProcessor(Project project, PsiFile file, Storage storage) {
		super(project, file, null, processChangedTextOnly(project, file, storage));
		this.storage = storage;
	}

	@Override
	public void run() {
		try {
			super.run();
		} catch (Exception e) {
		}
	}

	@Override
	public int order() {
		return 2;
	}

	@Override
	public String toString() {
		return ID_CHANGED_TEXT;
	}

	private static boolean processChangedTextOnly(Project project, PsiFile psiFile, Storage storage) {
		return false;
	}
}
