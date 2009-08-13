package nl.javadude.codejam;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMultiProblemSolver implements Algorithm {
	protected List<String> problemsInput = null;
	
	public List<String> run(List<String> lines) {
		problemsInput = lines;
		return solveAllProblems();
	}

	private List<String> solveAllProblems() {
		int numberOfProblems = readNumberOfProblems();
		List<String> solutions = new ArrayList<String>();

		for (int i = 0; i < numberOfProblems; i++) {
			Problem problem = readProblem();
			solutions.addAll(solveProblem(problem));
		}

		return solutions;
	}


	private int readNumberOfProblems() {
		String string = problemsInput.get(0);
		problemsInput.remove(0);
		return Integer.parseInt(string);
	}
	
	protected abstract List<String> solveProblem(Problem problem);
	protected abstract Problem readProblem();
}
