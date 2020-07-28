package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> data = new ArrayList<>();
        File file = new File(args[1]);
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                data.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + args[1]);
        }

        Map<String, ArrayList<Integer>> invertedIndex = createInvertedIndex(data);

        while (true) {
            System.out.print("=== Menu ===\n" +
                    "1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit\n");
            int menuChoice = Integer.parseInt(scanner.nextLine());

            switch (menuChoice) {
                case 1:
                    findAPerson(scanner, data, invertedIndex);
                    break;
                case 2:
                    for (String person: data) {
                        System.out.println(person);
                    }
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }

    }

    private static void findAPerson(Scanner scanner, ArrayList<String> data, Map<String, ArrayList<Integer>> invertedIndex) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String searchStrategy = scanner.nextLine().trim().toUpperCase();
        if (searchStrategy.matches("^(ALL|ANY|NONE)$")) {
            System.out.println("Enter data to search people:");
            String[] searchString = scanner.nextLine().toUpperCase().split(" ");

            ArrayList<String> result = new ArrayList<>();

            switch (searchStrategy) {
                case "ALL":
                    result = (ArrayList<String>) data.clone();
                    for (String searchElement: searchString) {
                        if (! invertedIndex.containsKey(searchElement)) {
                            result.clear();
                            break;
                        } else {
                            result.removeIf(s -> !s.toUpperCase().matches("^.*" + searchElement + ".*$"));
                        }
                    }
                    break;
                case "ANY":
                    for (String searchElement: searchString) {
                        if (invertedIndex.containsKey(searchElement)) {
                            for (int i : invertedIndex.get(searchElement)) {
                                if (!result.contains(data.get(i))) {
                                    result.add(data.get(i));
                                }
                            }
                        }
                    }
                    break;
                case "NONE":
                    result = (ArrayList<String>) data.clone();
                    for (String searchElement: searchString) {
                        if (invertedIndex.containsKey(searchElement)) {
                            for (int i : invertedIndex.get(searchElement)) {
                                result.remove(data.get(i));
                            }
                        }
                    }
                    break;

            }

            if (result.size() == 0) {
                System.out.println("No matching people found.\n");
            } else {
                System.out.println("Found people:");
                for (String s: result) {
                    System.out.println(s);
                }
            }
        } else {
            System.out.println("unknown search strategy");
        }
    }

    private static Map<String, ArrayList<Integer>> createInvertedIndex(ArrayList<String> data) {
        Map<String, ArrayList<Integer>> result = new HashMap<>();
        for (Integer i = 0; i < data.size(); i++) {
            String[] words = data.get(i).toUpperCase().split(" ");
            for (String word : words ) {
                if (! result.containsKey(word)) {
                    result.put(word, new ArrayList<>());
                }
                if (! result.get(word).contains(i)) {
                    result.get(word).add(i);
                }
            }
        }
        return result;
    }
}
