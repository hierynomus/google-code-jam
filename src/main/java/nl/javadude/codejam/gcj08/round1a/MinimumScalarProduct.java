package nl.javadude.codejam.gcj08.round1a;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.javadude.codejam.AbstractMultiProblemSolver;

public class MinimumScalarProduct extends AbstractMultiProblemSolver<MinimumScalarProductProblem> {

	@Override
	protected MinimumScalarProductProblem readProblem(BufferedReader lines, int problemNumber) throws Exception {
		MinimumScalarProductProblem problem = new MinimumScalarProductProblem();
		problem.problemNumber = problemNumber;
		problem.vectorLength = readLineAsInt(lines);
		problem.vector1 = lines.readLine();
		problem.vector2 = lines.readLine();
		return problem;
	}

	@Override
	protected List<String> solveProblem(MinimumScalarProductProblem problem) {
		int[] vector1 = new int[problem.vectorLength];
		int[] vector2 = new int[problem.vectorLength];
		
		String[] splitVector1 = problem.vector1.split(" ");
		String[] splitVector2 = problem.vector2.split(" ");
		for (int i = 0; i < problem.vectorLength; i++) {
			vector1[i] = Integer.parseInt(splitVector1[i]);
			vector2[i] = Integer.parseInt(splitVector2[i]);
		}
		
		Arrays.sort(vector1);
		Arrays.sort(vector2);
		
		long product = 0;
		for (int i = 0, j = problem.vectorLength - 1; i < problem.vectorLength; i++, j--) {
			product += (vector1[i] * vector2[j]); 
		}
		
		return Collections.singletonList("Case #" + problem.problemNumber + ": " + product);
	}

}
