package fr.damntools.jetbrainsplugins.editoractions.processors;

import java.util.Comparator;

/**
 * Processor
 *
 * @author flocastera
 * @version 1.0
 * @date 02/06/2018
 */
public interface Processor {

	void run();

	int order();

	class ProcessorComparator implements Comparator<Processor> {

		@Override
		public int compare(Processor o1, Processor o2) {
			if (o1.order() == o2.order()) {
				return 0;
			}
			return o1.order() < o2.order() ? -1 : 1;
		}

	}

}
