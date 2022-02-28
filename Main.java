package com.alexwala;

import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        String word = "FIRST";

        char[] wordArray = new char[5];
        for (int i = 0; i < 5; i++) {
            wordArray[i] = word.charAt(i);
        }

        System.out.println("Take a guess at the word");

        Scanner scan = new Scanner(System.in);

        String guess;
        char[] guessArray = new char[5];

        while (true) {
            guess = scan.nextLine();
            if (guess.equals("END")) {
                break;
            }

            for (int i = 0; i < 5; i++) {
                guessArray[i] = guess.charAt(i);
            }

            for (int i = 0; i < 5; i++) {
                if (guessArray[i] == wordArray[i]) {
                    System.out.print(ANSI_GREEN + guessArray[i] + ANSI_RESET);
                } else if (checkFor(wordArray, guessArray, i)) {
                    System.out.print(ANSI_YELLOW + guessArray[i] + ANSI_RESET);
                } else {
                    System.out.print(guessArray[i]);
                }
            }
            System.out.println("");
        }

        System.out.println("Thank for playing JAVA Wordle!");
        System.out.println("Developed by AlexWala");


    }

    public static boolean checkFor(char[] wordArray, char[] guessArray, int index) {
        int found = 0;
        for (int i = 0; i < 5; i++) {
            if (wordArray[i] == guessArray[index]) {
                found++;
            }
        }

        if (found > 0) {
            return true;
        }

        return false;
    }
}
