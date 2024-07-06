package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that processes input and output files.
 */
public class FileProcessor {
    private File predefinedWords;
    private File inputToBeProcessed;
    private static FileProcessor fileProcessor = null;
    private static final String RELATIVE_FILE_UPLOAD_PATH_INPUT = "./src/resources/input/";
    private static final String RELATIVE_FILE_UPLOAD_PATH_OUTPUT = "./src/resources/output/";

    private FileProcessor(String predefinedWordsFileName, String inputToBeProcessedFileName) {
        predefinedWords = new File( RELATIVE_FILE_UPLOAD_PATH_INPUT + predefinedWordsFileName);
        inputToBeProcessed = new File(RELATIVE_FILE_UPLOAD_PATH_INPUT + inputToBeProcessedFileName);
    }

    /**
     * Singleton to access the fileProcessor Instance
     * @param predefinedWordsFileName Name of predefinedWords file
     * @param inputToBeProcessedFileName Name of input sentences file for which predefined word match needs to be done.
     * @return Single instance of FileProcessor
     */
    public static FileProcessor getInstance(String predefinedWordsFileName, String inputToBeProcessedFileName) {

        try {
            if (predefinedWordsFileName == null || predefinedWordsFileName.isEmpty())
            { throw new FileNotFoundException("Check Predefined File or spelling"); }

            if (inputToBeProcessedFileName == null || inputToBeProcessedFileName.isEmpty())
            { throw new FileNotFoundException("Check Predefined File or spelling"); }
            if (fileProcessor == null) {
                fileProcessor = new FileProcessor(predefinedWordsFileName, inputToBeProcessedFileName);
                return fileProcessor;
            } else {
                return fileProcessor;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Collates lines from Input sentences file
     * @return List of Input sentences separated by newline
     */
    public List<String> getInputToBeProcessed() {
        try {
            return Files.readAllLines(this.inputToBeProcessed.toPath()).stream().filter(l -> !l.isEmpty()).toList();
        } catch (IOException e) {
            //throw new RuntimeException(e);
            return null;
        }
    }

    /**
     * Collates lines from predefined Words file
     * @return List of Predefined Words separated by newline
     */
    public List<String> getPredefinedWordList() {
        try {
            return Files.readAllLines(this.predefinedWords.toPath()).stream().filter(l -> !l.isEmpty()).toList();
        } catch (IOException e) {
            //throw new RuntimeException(e);
            return null;
        }
    }

    /**
     * Writes the word match list and count to a new file
     * @param map is the Concurrent tree map where the predefined word and its corresponding match count stored
     * @return whether the output file was created.
     */
    public boolean writeOutputToFile(ConcurrentSkipListMap<String, AtomicInteger> map)  {
        try {
            File output = new File(RELATIVE_FILE_UPLOAD_PATH_OUTPUT +  "output_"+ new Date().getTime()+".csv");
            try (PrintWriter pw = new PrintWriter(output)) {
                pw.println("PredefinedWord, MatchCount");
                map.entrySet().forEach(e -> pw.println(e.getKey() + "," + e.getValue().get()));
            }

            return output.exists();
        } catch (Exception e) {
            return false;
        }
    }
}
