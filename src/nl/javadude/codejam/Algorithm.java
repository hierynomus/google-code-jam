package nl.javadude.codejam;

import java.util.List;

/**
 * A Code Jam algorithm that solves a Google Code Jam problem.
 * @author ajvanerp
 *
 */
public interface Algorithm {
	/**
	 * Run the algorithm
	 * @param lines the input lines from the file.
	 * @return The output lines.
	 */
	List<String> run(List<String> lines);
}
