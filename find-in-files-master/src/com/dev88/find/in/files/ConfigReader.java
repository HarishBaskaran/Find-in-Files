package com.dev88.find.in.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dev88.find.in.files.constraint.Constraint;
import com.dev88.find.in.files.constraint.ConstraintName;

public class ConfigReader {

	static List<File> getRootFolders() throws IOException {
		BufferedReader pathsReader = new BufferedReader(new FileReader("search-paths.txt"));
		String line;
		List<File> folders = new ArrayList<File>(5);
		while (pathsReader.ready()) {
			line = pathsReader.readLine();
			if (line == null)
				break;

			folders.add(new File(line));
		}
		pathsReader.close();
		return folders;
	}

	static List<String> getWords(int n) throws IOException {
		BufferedReader wordsReader = new BufferedReader(new FileReader("search-words.txt"));
		String line = null;
		List<String> words = new ArrayList<String>(5);
		// while (wordsReader.ready()) {
		for (int i = 0; i < n; i++) {
			wordsReader.readLine();
			System.out.println(wordsReader);
		}
		line = wordsReader.readLine();
		if (line.contains("LAST_WORD")) {
			System.out.println("LAST WORD");
			// break;
			return null;
		}

		words.add(line);
		// }
		// wordsReader.close();
		return words;
	}

	public static List<Constraint> getConstraints() {
		List<Constraint> constraints = new ArrayList<Constraint>(10);

		File constraintFile = new File("search-constraints.txt");
		if (!constraintFile.exists()) {
			return constraints;
		}

		BufferedReader constraintsReader;
		try {
			constraintsReader = new BufferedReader(new FileReader(constraintFile));
			String line;
			while (constraintsReader.ready()) {
				line = constraintsReader.readLine();
				if (line == null)
					break;

				line = line.trim();
				if (line.startsWith("//") || line.isEmpty()) {
					continue;
				}

				if (line.startsWith("name=")) {
					constraints.add(new ConstraintName(line.substring(5)));
				}
			}
			constraintsReader.close();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		return constraints;
	}

}
