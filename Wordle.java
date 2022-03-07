package com.alexwala;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Wordle {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("C:/Users/Alex Wang/IdeaProjects/Wordle/src/com/alexwala/WordsCSV.csv");
        String word = getRandomWord().toUpperCase();

        Random rand = new Random();

        int tries = 6;

        char[] wordArray = new char[5];
        for (int i = 0; i < 5; i++) {
            wordArray[i] = word.charAt(i);
        }

        Scanner scan = new Scanner(System.in);
        HashMap<Character, Integer> wordHash = new HashMap<>();
        HashMap<Character, ArrayList<Integer>> exceptionKey = new HashMap<>();

        boolean won = false;


        while (tries > 0) {

            int correct = 0;
            System.out.println("[" + tries + "] Enter in your guess:");

            String input = scan.nextLine().toUpperCase();

            if (input.length() != 5) {
                System.err.println("Enter in a 5 letter word");
            } else {

                if (validInput(input)) {

                    wordHash.clear();

                    wordHash = checkFor(wordArray);

                    char[] guess = new char[5];

                    for (int i = 0; i < 5; i++) {
                        guess[i] = input.charAt(i);
                    }

                    ArrayList<Character> exceptionIndex = new ArrayList<>();
                    ArrayList<Integer> index = new ArrayList<>();
                    ArrayList<Integer> wordIndex = new ArrayList<>();

                    for (int i = 0; i < 5; i++) {
                        index.clear();
                        //This checks if the letter has more than one re-occurance
                        if (wordHash.containsKey(guess[i])) {
                            if (wordHash.get(guess[i]) > 0) {
                                if (!exceptionIndex.contains(guess[i])) {

                                    for (int x = 0; x < 5; x++) {
                                        if (guess[i] == guess[x]) {
                                            index.add(x);
                                        }
                                    }

                                    for (int x = 0; x < 5; x++) {
                                        if (wordArray[x] == guess[i]) {
                                            wordIndex.add(x);
                                        }
                                    }


                                    wordIndex.retainAll(index);
                                    wordHash.put(guess[i], wordHash.get(guess[i]) - wordIndex.size());

                                    exceptionKey.put(guess[i], new ArrayList<Integer>(wordIndex));
                                }
                            }

                            exceptionIndex.add(guess[i]);
                        }


                    }


                    for (int i = 0; i < 5; i++) {
                        if (!wordHash.containsKey(guess[i])) {
                            System.out.print(guess[i]);
                        } else {
                            if (wordHash.get(guess[i]) == 0 && exceptionKey.get(guess[i]).contains(i)) {
                                System.out.print(ANSI_GREEN + guess[i] + ANSI_RESET);
                                correct++;
                            } else if (wordHash.get(guess[i]) > 0 && exceptionKey.get(guess[i]).contains(i)) {
                                System.out.print(ANSI_GREEN + guess[i] + ANSI_RESET);
                                correct++;
                                wordHash.replace(guess[i], wordHash.get(guess[i]) - 1);
                            } else if (wordHash.get(guess[i]) > 0) {
                                System.out.print(ANSI_YELLOW + guess[i] + ANSI_RESET);
                                wordHash.replace(guess[i], wordHash.get(guess[i]) - 1);
                            } else if (!exceptionKey.get(guess[i]).contains(i) || wordHash.get(guess[i]) == 0) {
                                System.out.print(guess[i]);
                            }
                        }
                    }

                    if (correct == 5) {
                        won = true;
                        break;
                    }

                    tries--;

                    System.out.println();
                } else {
                    System.err.println("INVALID WORD");
                    System.out.println();
                }
            }
        }

        System.out.println();

        if (won) {
            System.out.println();
            System.out.println("CONGRATULATIONS! YOU WIN!");
        } else {
            System.out.println("You ran out of tries :(");
            System.out.println("The word was " + ANSI_RED + word + ANSI_RESET);
        }

        TimeUnit.SECONDS.sleep(4);

        System.out.println();

        System.out.println("---------------------------------------");

        System.out.println();
        System.out.println("Thanks for playing my " + ANSI_GREEN + "WORDLE" + ANSI_RESET + " remake!");
        System.out.println("If there are any " + ANSI_RED + "bugs" + ANSI_RESET + ", please submit an issue request on " + ANSI_BLUE + "GitHub." + ANSI_RESET);
        System.out.println("Developed by " + ANSI_YELLOW + "AlexWala" + ANSI_RESET);
        System.out.println();
        System.out.println("More " + ANSI_CYAN + "features" + ANSI_RESET + " coming soon! Stay tuned...");


    }

    public static HashMap<Character, Integer> checkFor(char[] wordArray) {
        HashMap<Character, Integer> wordHash = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            char checked = wordArray[i];
            int amount = 0;

            for (int x = 0; x < 5; x++) {
                if (checked == wordArray[x]) {
                    amount++;
                }
            }

            wordHash.put(checked, amount);
        }
        return wordHash;
    }

    public static boolean validInput(String input) throws FileNotFoundException {
        File file = new File("C:/Users/Alex Wang/IdeaProjects/Wordle/src/com/alexwala/WordsCSV.csv");
        Scanner scan = new Scanner(file);

        int found = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine().toUpperCase();

            if (line.equals(input.toUpperCase())) {
                found++;
            }
        }

        if (found > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getRandomWord() throws IOException {
        File file = new File("C:/Users/Alex Wang/IdeaProjects/Wordle/src/com/alexwala/WordsCSV.csv");
        Path path = Paths.get(String.valueOf(file));

        Random rand = new Random();



        int lines = Integer.parseInt(String.valueOf(Files.lines(path).count()));

        int random = rand.nextInt(lines + 1);

        String word;

        try (Stream<String> output = Files.lines(Paths.get(String.valueOf(file)))) {
            word = output.skip(random - 1).findFirst().get();
        }

        return word;
    }
}