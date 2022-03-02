package com.alexwala;


import java.util.*;

public class Wordle {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        String word = "ERROR";

        char[] wordArray = new char[5];
        for (int i = 0; i < 5; i++) {
            wordArray[i] = word.charAt(i);
        }

        System.out.println(wordArray);

        System.out.println("Take a guess at the word");

        Scanner scan = new Scanner(System.in);
        HashMap<Character, Integer> wordHash = new HashMap<>();
        HashMap<Character, ArrayList<Integer>> exceptionKey = new HashMap<>();


        wordHash = checkFor(wordArray);

        char[] guess = {'R', 'R', 'R', 'R', 'R'};

        ArrayList<Character> exceptionIndex = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            index.clear();
            //This checks if the letter has more than one re-occurance
            if (wordHash.get(guess[i]) > 0) {


                for (int x = 0; x < 5; x++) {
                    if (guess[i] == guess[x]) {
                        index.add(x);
                    }
                }

                ArrayList<Integer> wordIndex = new ArrayList<>();

                for (int x = 0; x < 5; x++) {
                    if (wordArray[x] == guess[i]) {
                        wordIndex.add(x);
                    }
                }

                System.out.println(guess[i]);

                System.out.println(index);
                System.out.println(wordIndex);


                index.retainAll(wordIndex);
                if (!exceptionIndex.contains(guess[i])) {
                    wordHash.put(guess[i], wordHash.get(guess[i]) - index.size());
                    exceptionIndex.add(guess[i]);
                    exceptionKey.put(guess[i], index);
                }
            }

            System.out.println(ANSI_GREEN + index + ANSI_RESET);
            System.out.println(ANSI_YELLOW + wordHash + ANSI_RESET);
            System.out.println(exceptionKey);






            /*if (index.contains(i)) {
                System.out.print(ANSI_GREEN + guess[i] + ANSI_RESET);
            }

            if (wordHash.get(guess[i]) == null || wordHash.get(guess[i]) == 0) {
                System.out.print(guess[i]);
            } else if (wordHash.get(guess[i]) > 0) {
                System.out.print(ANSI_YELLOW + guess[i] + ANSI_RESET);
                wordHash.replace(guess[i], wordHash.get(guess[i]) - 1);
            } */

        }



        /*while (true) {
            guess = scan.nextLine().toUpperCase();
            if (guess.equals("END")) {
                break;
            }

            for (int i = 0; i < 5; i++) {
                guessArray[i] = guess.charAt(i);
            }



            for (int i = 0; i < 5; i++) {
                if (guessArray[i] == wordArray[i]) {
                    System.out.print(ANSI_GREEN + guessArray[i] + ANSI_RESET);
                } else if () {
                    System.out.print(ANSI_YELLOW + guessArray[i] + ANSI_RESET);
                } else {
                    System.out.print(guessArray[i]);
                }
            }
            System.out.println("");
        } */

        /*System.out.println("Thank for playing JAVA Wordle!");
        System.out.println("Developed by AlexWala"); */


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
}
