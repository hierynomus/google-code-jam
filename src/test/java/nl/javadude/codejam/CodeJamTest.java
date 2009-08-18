package nl.javadude.codejam;

import nl.javadude.codejam.gcj08.round1a.Milkshakes;

public class CodeJamTest {

	public static void main(String[] args) throws Exception {
		CodeJam.a = new Milkshakes();
		CodeJam.main(new String[] {"src/main/resources/gcj08/round1a/B-large-practice.in"});
	}
}
