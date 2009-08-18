package nl.javadude.codejam.gcj08.round1a;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.List;

import nl.javadude.codejam.AbstractMultiProblemSolver;
import nl.javadude.codejam.gcj08.round1a.MilkshakesProblem.Customer;

public class Milkshakes extends AbstractMultiProblemSolver<MilkshakesProblem> {

	@Override
	protected MilkshakesProblem readProblem(BufferedReader lines, int problemNumber) throws Exception {
		MilkshakesProblem problem = new MilkshakesProblem();
		problem.problemNumber = problemNumber;
		problem.nrFlavours = readLineAsInt(lines);
		problem.nrCustomers = readLineAsInt(lines);
		readLines(lines, problem.nrCustomers, problem.new CustomerClosure());
		return problem;
	}

	@Override
	protected List<String> solveProblem(MilkshakesProblem problem) {
		boolean[] shakes = new boolean[problem.nrFlavours + 1];
		for (int i = 1; i <= problem.nrFlavours; i++) {
			shakes[i] = false;
		}
		
		boolean needsRechecking = true;
		boolean impossible = false;
		while (needsRechecking) {
			needsRechecking = false;
			for (Customer c : problem.customers) {
				if (!c.satisfied(shakes)) {
					if (c.satisfy(shakes)) {
						needsRechecking = true;
					}
					else {
						impossible = true;
						break;
					}
				}
			}
		}
		
		if (impossible) {
			return Collections.singletonList("Case #" + problem.problemNumber + ": IMPOSSIBLE");
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < shakes.length; i++) {
			builder.append(" ").append(shakes[i] ? "1" : "0");
		}
		return Collections.singletonList("Case #" + problem.problemNumber + ":" + builder.toString());
	}

}
