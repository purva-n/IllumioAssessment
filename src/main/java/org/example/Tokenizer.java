package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Class that represents a Runnable thread
 * to process input sentences and increment the count of predefined word map on occurence
 */
public class Tokenizer implements Runnable {

    private ConcurrentSkipListMap<String, Integer> map;
    private List<String> inputs;
    private CountDownLatch latch;

    /**
     * Constructor to build a Runnable thread
     * @param map Concurrent tree map reference for all threads to update match count
     * @param inputs batch of input strings for which the match count needs to be computed
     */
    public Tokenizer(ConcurrentSkipListMap<String, Integer> map, List<String> inputs, CountDownLatch latch) {
        this.map = map;
        this.inputs = inputs;
        this.latch = latch;
    }

    /**
     * Starts the match counting for the batch of input strings
     */
    @Override
    public void run() {
            for (String input : this.inputs) {
                if(!input.isEmpty()) {
                    List<String> tokens = Arrays.stream(input.replaceAll("\\p{Punct}", " ").split("\s")).toList();

                    tokens.forEach(t -> {
                        if (map.containsKey(t)) {
                            map.put(t, map.get(t) + 1);
                        }
                    });
                }
            }

            latch.countDown();
        }
}
