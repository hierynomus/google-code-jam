package nl.javadude.codejam;

import java.util.ArrayList;
import java.util.List;

public class Problem {
	private List<String> lines;
	
	public Problem() {
		lines = new ArrayList<String>();
	}
	
	public void addLine(String line) {
		lines.add(line);
	}
	
	public List<String> getLines() {
		return lines;
	}
}
