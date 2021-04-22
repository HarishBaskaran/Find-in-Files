package com.dev88.find.in.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class FindDifference {

	private static String words;
	private static File file1;
	private static File file2;

	BufferedReader br1 = null;
	BufferedReader br2 = null;
	String sCurrentLine;
	List<String> list1 = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();

	private static List<File> rootFolders1;
	private static List<File> rootFolders2;

	public static void main(String[] args) throws Exception {

		File fck = new File ("C:\\Harish\\Automation\\pms");
		
		rootFolders1.add(new File("C:\\Harish\\Automation\\pms"));
		rootFolders2.add(new File("C:\\Harish\\Automation\\PMS_VCTRS-69309"));

		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("positiveOutput.txt", false), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		for (File root : rootFolders1) {
			findPositiveDifferenceRecursively(root);
		}
		
		
		try {
			out = new PrintStream(new FileOutputStream("negativeOutput.txt", false), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
			
		for (File root : rootFolders2) {
			findNegativeDifferenceRecursively(root);
		}

	}
	
	private static void findPositiveDifferenceRecursively(final File folder) throws Exception {

		for (File file : folder.listFiles()) {

			if (file.isDirectory()) {
				findDifferenceRecursively(file);

			} else if (file.isFile()) {
				String file1 = file.toString().replace("Automation\\pms", "Automation\\PMS_VCTRS-69309");
				File f = new File(file1);
				if (f.exists()) {
				} else if (file1.contains(".java"))
					System.out.println(f.getCanonicalPath().toString());

			}
		}
	}

	private static void findNegativeDifferenceRecursively(final File folder) throws Exception {

		for (File file : folder.listFiles()) {

			if (file.isDirectory()) {
				findDifferenceRecursively(file);

			} else if (file.isFile()) {
				String file1 = file.toString().replace("Automation\\pms", "Automation\\PMS_VCTRS-69309");
				File f = new File(file1);
				if (f.exists()) {
				} else if (file1.contains(".java"))
					System.out.println(f.getCanonicalPath().toString());

			}
		}
	}
	private static void findDifferenceRecursively(final File folder) throws Exception {

		for (File file : folder.listFiles()) {

			if (file.isDirectory()) {
				findDifferenceRecursively(file);

			} else if (file.isFile()) {
				String file1 = file.toString().replace("Automation\\pms", "Automation\\PMS_VCTRS-69309");
				File f = new File(file1);
				if (f.exists()) {
				} else if (file1.contains(".java"))
					System.out.println(f.getCanonicalPath().toString());

			}
		}
	}

	public void Check() {

		try {
			br1 = new BufferedReader(new FileReader(file1));
			br2 = new BufferedReader(new FileReader(file2));

			while ((sCurrentLine = br1.readLine()) != null) {
				list1.add(sCurrentLine);
			}
			while ((sCurrentLine = br2.readLine()) != null) {
				list2.add(sCurrentLine);
			}

		} catch (Exception e) {
			return;
		}

		positiveDifference();
		negativeDifference();
	}

	public void positiveDifference() {

		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("positiveDifference.txt", true), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		List<String> tmpList = new ArrayList<String>(list1);
		tmpList.removeAll(list2);
		for (int i = 0; i < tmpList.size(); i++) {
			words = tmpList.get(i);
			checkContent(file1);
		}
	}

	public void negativeDifference() {
		PrintStream out;
		try {
			out = new PrintStream(new FileOutputStream("positiveDifference.txt", true), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		List<String> tmpList = list2;
		tmpList.removeAll(list1);
		for (int i = 0; i < tmpList.size(); i++) {
			words = tmpList.get(i);
			checkContent(file1);
		}
	}

	private void checkContent(File file) {

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
					message.append(file.getAbsolutePath() + "\t" + lineNumber + "\t" + line.trim() + "\n");
				}
			}

			reader.close();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

		if (message.length() > 0) {
			System.out.println(message.toString());

		}
	}

	private boolean matchesAnyWord(String line) {

		if (line.contains(words)) {
			return true;
		}

		return false;
	}
}
