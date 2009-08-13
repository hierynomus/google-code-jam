package nl.javadude.codejam.gcj08.qualify;

import java.io.BufferedReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.javadude.codejam.AbstractMultiProblemSolver;

public class SavingTheUniverse extends AbstractMultiProblemSolver<SavingTheUniverseProblem> {

	@Override
	protected SavingTheUniverseProblem readProblem(BufferedReader lines, int problemNumber) throws Exception {
		SavingTheUniverseProblem problem = new SavingTheUniverseProblem();
		problem.problemNumber = problemNumber;
		problem.nrEngines = readLineAsInt(lines);
		readLinesToList(lines, problem.nrEngines, problem.engines);
		problem.nrSearches = readLineAsInt(lines);
		readLinesToList(lines, problem.nrSearches, problem.searches);
		return problem;
	}

	@Override
	protected List<String> solveProblem(SavingTheUniverseProblem problem) {
		int switches = 0;
		Set<String> enginesEncountered = new HashSet<String>();
		for (String search : problem.searches) {
			enginesEncountered.add(search);
			if (enginesEncountered.size() == problem.nrEngines) {
				switches++;
				enginesEncountered.clear();
				enginesEncountered.add(search);
			}
		}
		
		return Collections.singletonList("Case #" + problem.problemNumber + ": " + 	switches);
	}

}
