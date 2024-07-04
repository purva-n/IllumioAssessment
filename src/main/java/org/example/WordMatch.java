package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Class to set up batching input lines for multi-threaded {@link WordMatch}
 */
public class WordMatch {
    private ConcurrentSkipListMap<String, Integer> map;
    private FileProcessor fp;
    private List<String> inputSentences;
    private List<String> predefinedWords;

    public WordMatch(String predefinedWordsFile, String inputFile) {
        fp = FileProcessor.getInstance(predefinedWordsFile, inputFile);
        map = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        inputSentences = new ArrayList<>();
        predefinedWords = new ArrayList<>();
    }

    public WordMatch(List<String> predefinedWordsList, List<String> inputLinesList) {
        this.predefinedWords = predefinedWordsList;
        this.inputSentences = inputLinesList;
        this.map = new ConcurrentSkipListMap<>(String.CASE_INSENSITIVE_ORDER);
        //this.fp = new FileProcessor();
    }

    public boolean matchWords() {
        boolean outputReady = false;
        if(fp != null) {
            if (inputSentences.isEmpty() || predefinedWords.isEmpty()) {
                this.inputSentences = fp.getInputToBeProcessed();
                this.predefinedWords = fp.getPredefinedWordList();
            }
        }

        initializeMapOfPredefinedWords(predefinedWords);

        if (inputSentences != null && !inputSentences.isEmpty()
                && predefinedWords != null && !predefinedWords.isEmpty()) {
            int tenPercentofTotalData = inputSentences.size() * 10 / 100;
            final int BATCH_SIZE = tenPercentofTotalData > 0 ? tenPercentofTotalData : inputSentences.size();

            ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
            int nThreads = inputSentences.size() / BATCH_SIZE;
            CountDownLatch latch = new CountDownLatch(nThreads);

            for (int i = 0; i < nThreads; i++) {
                int finalI = i;
                service.execute(
                        new Tokenizer(
                                this.map,
                                inputSentences.stream().skip((long) finalI * BATCH_SIZE).limit(BATCH_SIZE).toList(), latch)
                );
            }

            try {
                latch.await();
                service.shutdown();
            } catch (InterruptedException e) {
                System.err.println("There was a runtime exception");
            }
        }

        if(fp != null) {
            outputReady = fp.writeOutputToFile(map);
            if (outputReady) {
                System.out.println("Output file is ready!");
            } else {
                System.err.println("There was an error to exporting output file.");
            }
        }

        return outputReady;
    }

    private void initializeMapOfPredefinedWords(List<String> predefinedWords) {
        for(String predefinedWord : predefinedWords) {
            this.map.putIfAbsent(predefinedWord, 0);
        }
    }

    /**
     * Gets the concurrent tree map of words and match count
     *
     * @return Returns a copy of the concurrent tree map of words and match count
     */
    public ConcurrentSkipListMap getWordMatchMap() {
        return new ConcurrentSkipListMap<>(this.map);
    }
}
