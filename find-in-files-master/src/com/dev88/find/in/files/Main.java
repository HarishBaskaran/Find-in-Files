package com.dev88.find.in.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.dev88.find.in.files.constraint.Constraint;

public class Main {
	private static List<File> rootFolders;
	private static List<Constraint> constraints;
	private static List<String> words;

	public static void main(String[] args) throws IOException {

		int number = 1;
		rootFolders = ConfigReader.getRootFolders();
		constraints = ConfigReader.getConstraints();
		fileClean("./output.txt");
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("output.txt", true), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		while (true) {
			words = ConfigReader.getWords(number++);
			System.out.println("-----------------------------------------------------");
			System.out.println(number - 1 + ". word = " + words);
			if (words == null) {
				break;
			}
			for (File root : rootFolders) {
				findRecursively(root);
			}
			System.out.println("-----------------------------------------------------");
		}
	}

	public static void fileClean(String file) {
		String lines = "";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(lines);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void findRecursively(final File folder) {

		for (File file : folder.listFiles()) {

			if (file.isDirectory()) {
				findRecursively(file);

			} else if (file.isFile() && anyConstraintIsValid(file)) {
				checkContent(file);
			}
		}
	}

	private static boolean anyConstraintIsValid(final File file) {
		if (constraints.isEmpty()) {
			return true;
		}

		for (Constraint constraint : constraints) {
			if (constraint.test(file)) {
				return true;
			}
		}
		return false;
	}

	private static void checkContent(File file) {
		// System.out.println("Checking: " + file.getAbsolutePath());

		StringBuilder message = new StringBuilder();

		BufferedReader reader;
		String line;
		int lineNumber;
		try {
			reader = new BufferedReader(new FileReader(file));
			lineNumber = 0;

			while (reader.ready()) {
				lineNumber++;
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (matchesAnyWord(line)) {
					message.append(file.getName() + "\t- " + lineNumber + "\t- " + line.trim() + "\t- " + file.getAbsolutePath() + "\n");
				}
			}

			reader.close();

		} catch (Exception e) {
//			e.printStackTrace(System.out);
		}

		if (message.length() > 0) {
//			System.out.print(file.getAbsolutePath());
			System.out.println(message.toString());

		}
	}

	private static boolean matchesAnyWord(String line) {
		if (words.isEmpty()) {
			return true;
		}

		for (String word : words) {
			if (line.contains(word)) {
				return true;
			}
		}
		return false;
	}

}
