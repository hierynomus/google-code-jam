/**
 * 
 */
package nl.javadude.codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class that reads an input file, executes an Algorithm and writes an output file.
 * @author ajvanerp
 *
 */
public class CodeJam {
	private static String inputFile;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		if (args.length == 0) {
			System.out.println("Should supply an input file");
			System.exit(-1);
		}
		
		inputFile = args[0];
		List<String> inputLines = readInput(inputFile);
		
		Algorithm a = new DummyAlgorithm();
		
		List<String> outputLines = a.run(inputLines);
		writeOutput(outputLines);
		
	}

	private static void writeOutput(List<String> outputLines) throws IOException {
		String outputFile = inputFile.substring(0, inputFile.indexOf(".")) + ".out";
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		
		for (String s : outputLines) {
			writer.write(s);
			writer.write("\n");
		}
	}

	private static List<String> readInput(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		List<String> list = new ArrayList<String>();
		String line = reader.readLine();
		while (line != null) {
			list.add(line);
			line = reader.readLine();
		}
		return list;
	}
}
