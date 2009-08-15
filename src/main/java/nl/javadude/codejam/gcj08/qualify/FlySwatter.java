package nl.javadude.codejam.gcj08.qualify;

import java.io.BufferedReader;
import java.util.List;

import nl.javadude.codejam.AbstractMultiProblemSolver;

public class FlySwatter extends AbstractMultiProblemSolver<FlySwatterProblem> {

	@Override
	protected FlySwatterProblem readProblem(BufferedReader lines, int problemNumber) throws Exception {
		FlySwatterProblem problem = new FlySwatterProblem();
		readLines(lines, 1, problem.new FlySwatterClosure());
		return problem;
	}

	@Override
	protected List<String> solveProblem(FlySwatterProblem problem) {
		// TODO Auto-generated method stub
		return null;
	}

}
