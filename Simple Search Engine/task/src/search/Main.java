package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split(" ");
        String search = scanner.nextLine();

        for (int i = 0; i < words.length; i++) {
            if (search.equals(words[i])) {
                System.out.println(i + 1);
                System.exit(0);
            }
        }
        System.out.println("Not found");
    }
}
