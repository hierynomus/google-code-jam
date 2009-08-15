package nl.javadude.codejam;

import nl.javadude.codejam.gcj08.round1a.MinimumScalarProduct;

public class CodeJamTest {

	public static void main(String[] args) throws Exception {
		CodeJam.a = new MinimumScalarProduct();
		CodeJam.main(new String[] {"src/main/resources/gcj08/round1a/A-large-practice.in"});
	}
}
