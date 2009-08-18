package nl.javadude.codejam.gcj08.round1a;

import java.util.ArrayList;
import java.util.List;

import nl.javadude.codejam.Closure;
import nl.javadude.codejam.Problem;

public class MilkshakesProblem implements Problem {
	int problemNumber;
	int nrFlavours;
	int nrCustomers;
	List<Customer> customers = new ArrayList<Customer>();
	
	class CustomerClosure implements Closure {
		public void execute(String string) {
			Customer c = new Customer();
			String[] split = string.split(" ");
			for (int i = 1; i + 1 < split.length; i+=2) {
				Integer flavour = Integer.valueOf(split[i]);
				if ("0".equals(split[i+1])) {
					c.likesUnmalted.add(flavour);
				}
				else {
					c.likesMalted = flavour;
				}
			}
			customers.add(c);
		}
	}
	
	class Customer {
		List<Integer> likesUnmalted = new ArrayList<Integer>();
		Integer likesMalted;

		boolean satisfied(boolean[] shakes) {
			for (int i : likesUnmalted) {
				if (i != 0 && !shakes[i]) {
					return true;
				}
			}
			
			if (likesMalted != null && shakes[likesMalted]) {
				return true;
			}
			
			return false;
		}
		
		boolean satisfy(boolean[] shakes) {
			if (likesMalted != null) {
				shakes[likesMalted] = true;
				return true;
			}
			return false;
		}
	}
}
