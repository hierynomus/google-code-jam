package nl.javadude.codejam;

import nl.javadude.codejam.gcj08.qualify.TrainTimetable;

public class CodeJamTest {

	public static void main(String[] args) throws Exception {
		CodeJam.a = new TrainTimetable();
		CodeJam.main(new String[] {"src/main/resources/gcj08/qualify/B-large-practice.in"});
	}
}
