/**
 * 
 */
package nl.javadude.codejam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import nl.javadude.codejam.gcj08.qualify.SavingTheUniverse;

/**
 * Main class that reads an input file, executes an Algorithm and writes an output file.
 * @author ajvanerp
 *
 */
public class CodeJam {
	private static File inputFile;
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		if (args.length == 0) {
			System.out.println("Should supply an input file");
			System.exit(-1);
		}
		
		inputFile = new File(args[0]);
		BufferedReader input = readInput(inputFile);
		
		Algorithm a = new SavingTheUniverse();
		
		List<String> outputLines = a.run(input);
		writeOutput(outputLines);
		
	}

	private static void writeOutput(List<String> outputLines) throws IOException {
		String fileName = inputFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		File outputFile = new File(inputFile.getParentFile(), fileName + ".out");
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		
		for (String s : outputLines) {
			writer.write(s);
			writer.write("\n");
		}
		writer.flush();
		writer.close();
	}

	private static BufferedReader readInput(File input) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(input));
		return reader;
	}
}
