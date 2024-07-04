package org.example;

public class Main {
    public static void main(String[] args) {
        String predefinedWordsFileName = args[0];
        String inputFileName = args[1];
        WordMatch wd = new WordMatch(predefinedWordsFileName, inputFileName);

        boolean successful = wd.matchWords();

        if(successful) {
            //get final map
        }

    }


}