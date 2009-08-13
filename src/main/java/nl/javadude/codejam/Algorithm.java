package nl.javadude.codejam;

import java.io.BufferedReader;
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
	 * @throws Exception 
	 */
	List<String> run(BufferedReader lines) throws Exception;
}
