import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.util.Scanner;

public class Main {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		int times = Integer.parseInt(scanner.next());
		for (int i = 0; i < times; i++) {
			int start = Integer.parseInt(scanner.next());
			int end = Integer.parseInt(scanner.next());
			for (; start <= end; start++) {
				boolean flag = false;
				for (int j = 1; i < start; j++) {
					if(start/j==0)flag=true;
				}
				if(!flag)System.out.println(start);
			}
			System.out.println();
		}
	}
}
