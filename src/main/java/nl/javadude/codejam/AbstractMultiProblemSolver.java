package nl.javadude.codejam;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMultiProblemSolver<P extends Problem> implements Algorithm {
	public List<String> run(BufferedReader lines) throws Exception {
		return solveAllProblems(lines);
	}

	private List<String> solveAllProblems(BufferedReader lines) throws Exception {
		int numberOfProblems = readLineAsInt(lines);
		List<String> solutions = new ArrayList<String>();

		for (int i = 0; i < numberOfProblems; i++) {
			P problem = readProblem(lines, i + 1);
			solutions.addAll(solveProblem(problem));
		}

		return solutions;
	}

	protected abstract List<String> solveProblem(P problem);
	protected abstract P readProblem(BufferedReader lines, int problemNumber) throws Exception;
	
	protected void readLinesToList(BufferedReader lines, int nrOfLines, List<String> list) throws IOException {
		for (int i = 0; i < nrOfLines; i++) {
			String line = lines.readLine();
			if (line == null) throw new IllegalStateException ("Could not read enough lines");
			list.add(line);
		}
	}
	
	protected int readLineAsInt(BufferedReader lines) throws IOException {
		String line = lines.readLine();
		if (line == null) throw new IllegalStateException("Could not read line to int");
		return Integer.parseInt(line);
	}
}
