package nl.javadude.codejam.gcj08.round1a;

import java.io.BufferedReader;
import java.util.List;

import nl.javadude.codejam.AbstractMultiProblemSolver;

public class Milkshakes extends AbstractMultiProblemSolver<MilkshakesProblem> {

	@Override
	protected MilkshakesProblem readProblem(BufferedReader lines, int problemNumber) throws Exception {
		MilkshakesProblem problem = new MilkshakesProblem();
		problem.problemNumber = problemNumber;
		problem.nrFlavours = readLineAsInt(lines);
		problem.nrCustomers = readLineAsInt(lines);
		return null;
	}

	@Override
	protected List<String> solveProblem(MilkshakesProblem problem) {
		// TODO Auto-generated method stub
		return null;
	}

}
