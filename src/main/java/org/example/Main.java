package org.example;

public class Main {
    public static void main(String[] args) {
        String predefinedWordsFileName = null;
        String inputFileName = null;
        try {
            predefinedWordsFileName = args[0];
            inputFileName = args[1];
        } catch (Exception e) {

        }

        if(inputFileName == null || inputFileName.isEmpty()) {
            System.err.println("Input file name is empty or null!.");
            return;
        }

        if(predefinedWordsFileName == null || predefinedWordsFileName.isEmpty()) {
            System.err.println("Predefined words file name is empty or null!.");
            return;
        }

        WordMatch wd = new WordMatch(predefinedWordsFileName, inputFileName);

        boolean successful = wd.matchWords();

        if (successful) {
            System.out.println("Output file is ready!");
        } else {
            System.err.println("There was an error to exporting output file.");
        }

    }


}