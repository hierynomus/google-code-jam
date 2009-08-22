package nl.javadude.codejam.gcj08.qualify;

import nl.javadude.codejam.Closure;
import nl.javadude.codejam.Problem;

public class FlySwatterProblem implements Problem {
	double outerRadius;
	double thickness;
	double stringRadius;
	double gap;
	double flyRadius;
	
	public class FlySwatterClosure implements Closure {
		public void execute(String string) {
			String[] split = string.split(" ");
			outerRadius = Double.parseDouble(split[0]);
			thickness = Double.parseDouble(split[1]);
			stringRadius = Double.parseDouble(split[2]);
			gap = Double.parseDouble(split[3]);
			flyRadius = Double.parseDouble(split[4]);
		}
		
	}
}
