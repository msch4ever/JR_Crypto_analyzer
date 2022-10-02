package cz.los.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextAnalyzer {

    private final Map<String, WordStats> textStats = new HashMap<>();
    private long wordCount;

    public void analyzeText(Path file) {

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.toFile()))) {
            while (fileReader.ready()) {
                String[] splitInput = fileReader.readLine().toLowerCase().split("[^a-zа-яё-]+");

                countWordsByLength(splitInput);
            }
            textStats.remove("");
            this.wordCount = textStats.values().stream().map(it -> it.count).reduce(0L, Long::sum);

        } catch (IOException e) {
            System.out.println("SAD! Something went wrong!");
            e.printStackTrace();
            System.out.println("Exiting...");
            System.exit(1);
        }

    }

    public Map<String, WordStats> getTextStats() {
        return textStats;
    }

    public long getWordCount() {
        return wordCount;
    }

    private void countWordsByLength(String[] splitInput) {
        for (String fragment : splitInput) {
            textStats.merge(fragment, new WordStats(fragment), ((old, stub) ->  {
                old.incrementCount();
                return old;
            }));
        }
    }

    public List<WordStats> getSortedStats() {
        return textStats.values().stream().sorted().collect(Collectors.toList());
    }

    public static class WordStats implements Comparable<WordStats> {
        public final String word;
        private long count = 1;

        public WordStats(String word) {
            this.word = word;
        }

        public void incrementCount() {
            count++;
        }

        @Override
        public int compareTo(WordStats o) {
            return Long.compare(o.count, this.count);
        }
    }
}
