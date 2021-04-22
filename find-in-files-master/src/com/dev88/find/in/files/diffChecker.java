package com.dev88.find.in.files;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;
import java.math.BigInteger;

public class diffChecker {
	static final long mod = (int) 1e9 + 7;

	public static void main(String[] args) throws Exception {

		String master = "C:\\Harish\\Automation\\pms\\accountservice\\ServiceDomain\\src\\main\\java\\com\\agilysys\\pms\\account\\data\\domain\\PantryItemDetailsDomain.java";
		String branch = "C:\\Harish\\Automation\\PMS_VCTRS-69309\\accountservice\\ServiceDomain\\src\\main\\java\\com\\agilysys\\pms\\account\\data\\domain\\PantryItemDetailsDomain.java";

		new diffChecker().DifferenceChecker(master, branch);
		new diffChecker().findDifference(master, branch);
	}

	public void DifferenceChecker(String masterFile, String branchFile) throws Exception {

		File master = new File(masterFile);
		File branch = new File(branchFile);

		Scanner ina = new Scanner(master);
		Scanner inb = new Scanner(branch);

		PrintWriter pw = new PrintWriter(System.out);
		ArrayList<String> a = new ArrayList();
		ArrayList<String> b = new ArrayList();
		while (ina.hasNextLine())
			a.add(ina.nextLine());
		while (inb.hasNextLine())
			b.add(inb.nextLine());
		int[][] dp = new int[b.size() + 1][a.size() + 1];
		int[][] path = new int[b.size() + 1][a.size() + 1];
		Arrays.fill(path[0], 2);
		for (int i = 1; i <= b.size(); i++)
			path[i][0] = 1;
		for (int i = 1; i <= b.size(); i++) {
			for (int j = 1; j <= a.size(); j++) {
				if (dp[i][j - 1] >= dp[i - 1][j]) {
					dp[i][j] = dp[i][j - 1];
					path[i][j] = 2;
				} else {
					dp[i][j] = dp[i - 1][j];
					path[i][j] = 1;
				}
				if (match(a.get(j - 1), b.get(i - 1))) {
					if (dp[i - 1][j - 1] + 1 > dp[i][j - 1] && dp[i - 1][j - 1] + 1 > dp[i - 1][j]) {
						dp[i][j] = dp[i - 1][j - 1] + 1;
						path[i][j] = 3;
					}
				}
			}
		}

		String lines = find(b.size(), a.size(), path, a, b);
//		pw.print(lines);
		pw.flush();

		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream("positiveOutput.txt", false), true);
			System.setOut(out);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(lines);
		out.flush();
		out.close();
	}

	public void findDifference(String masterFile, String branchFile) {
		PrintStream out = null;
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		BufferedReader br3 = null;
		String sCurrentLine;
		List<String> list1 = new LinkedList<String>();
		List<String> master = new LinkedList<String>();
		List<String> branch = new LinkedList<String>();

		System.out.println("***********************************");
		System.out.println(masterFile + " 8888888888" + branchFile);
		System.out.println("***********************************");

		try {
			br1 = new BufferedReader(new FileReader(masterFile));

			while ((sCurrentLine = br1.readLine()) != null) {
				System.out.println(sCurrentLine);
				master.add(sCurrentLine);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		try {
			br2 = new BufferedReader(new FileReader(branchFile));

			while ((sCurrentLine = br2.readLine()) != null) {
				branch.add(sCurrentLine);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		try {
			br3 = new BufferedReader(new FileReader("positiveOutput.txt"));

			while ((sCurrentLine = br3.readLine()) != null) {
				list1.add(sCurrentLine);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		for (int i = 0, j = 1, k = 1; i < list1.size(); i++) {

			String line = list1.get(i);
			try {
				if (line.contains("//added")) {
					out = null;
					try {
						out = new PrintStream(new FileOutputStream("positiveDifference.txt", true), true);
						System.setOut(out);
					} catch (FileNotFoundException e) {
						System.out.println(e.getMessage());
					}

					for (; j < branch.size(); j++) {
						try {
							if (branch.get(j).contains(line.split("//added")[0]))
								break;
						} catch (Exception e) {
						}
					}
					int p = j + 1;
					if (Pattern.matches("^(?=\\s*\\S).*$", line.split("//added")[0]))
						System.out.println("\t" + p + "\t" + branch.get(j));
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				if (line.contains("//deleted")) {
					out = null;
					try {
						out = new PrintStream(new FileOutputStream("negativeDifference.txt", true), true);
						System.setOut(out);
					} catch (FileNotFoundException e) {
						System.out.println(e.getMessage());
					}

					for (; k < master.size(); k++) {
						try {
							if (master.get(k).contains(line.split("//deleted")[0]))
								break;
						} catch (Exception e) {
						}
					}
					int p = k + 1;
					if (Pattern.matches(".*[a-z].*", line.split("//deleted")[0]))
						System.out.println("\t" + p + "\t" + master.get(k));
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static String find(int i, int j, int[][] path, ArrayList<String> a, ArrayList<String> b) {
		if (i == 0 && j == 0)
			return "";

		if (path[i][j] == 1)
			return find(i - 1, j, path, a, b) + b.get(i - 1) + "//added \n";

		if (path[i][j] == 2)
			return find(i, j - 1, path, a, b) + a.get(j - 1) + "//deleted \n";

		return find(i - 1, j - 1, path, a, b) + a.get(j - 1) + "\n";
	}

	static boolean match(String a, String b) {
		if (a.length() > b.length() || a.length() < b.length())
			return false;

		for (int i = 0; i < a.length(); i++)
			if (a.charAt(i) != b.charAt(i))
				return false;

		return true;
	}
}
