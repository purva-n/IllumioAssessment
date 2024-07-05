package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that represents a Runnable thread
 * to process input sentences and increment the count of predefined word map on occurence
 */
public class Tokenizer implements Runnable {

    private ConcurrentSkipListMap<String, AtomicInteger> map;
    private List<String> inputs;
    private CountDownLatch latch;

    /**
     * Constructor to build a Runnable thread
     * @param map Concurrent tree map reference for all threads to update match count
     * @param inputs batch of input strings for which the match count needs to be computed
     */
    public Tokenizer(ConcurrentSkipListMap<String, AtomicInteger> map, List<String> inputs, CountDownLatch latch) {
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
                    //System.out.println(input);
                    String inputStripLeadingTrailingNumbersPunctuation =
                            input.replaceAll("^\\p{Punct}*|^[0-9]*|\\p{Punct}*$|[0-9]*$", "");
                    //System.out.println("Cleaned:" + inputStripLeadingTrailingNumbersPunctuation);
                    List<String> tokens = Arrays.stream(inputStripLeadingTrailingNumbersPunctuation.split("\s"))
                            .toList();

                    tokens.forEach(t -> {
                        if (map.containsKey(t)) {
                            map.get(t).incrementAndGet();
                        }
                    });
                }
            }

            latch.countDown();
        }
}
