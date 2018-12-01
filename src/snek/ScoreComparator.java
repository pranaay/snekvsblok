package snek;

import java.util.Comparator;

/**
 * Contains definitions for ScoreComparator. It is used to compare the custom Score objects.
 */
public class ScoreComparator implements Comparator<Score> {
	/**
	 * A comparator to compare two custom objects, of type Score.
	 * @param o1 Object 1
	 * @param o2 Object 2
	 * @return Integer
	 */
	@Override
	public int compare(Score o1, Score o2) {
		return o1.getScore() - o2.getScore();
	}
}
