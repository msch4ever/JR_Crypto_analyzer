package cz.los.model;

import cz.los.cmd.Configuration;
import cz.los.cmd.Mode;
import cz.los.util.Dictionary;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BruteForceDecoder extends Decoder {

    private final Path sampleFile;

    public BruteForceDecoder(Configuration configuration) {
        super(configuration);
        this.sampleFile = configuration.getSampleFilePath();
    }

    @Override
    protected String resolveDestinationFileName() {
        if (this.key == null) {
            return "";
        }
        return originFile.getFileName().toString().replace(".txt", "_" + key + ".txt");
    }

    public void decodeBruteForce() {
        System.out.println("Trying to decode using sample text provided..");
        long start = System.currentTimeMillis();
        TextAnalyzer sampleAnalyzer = new TextAnalyzer();
        sampleAnalyzer.analyzeText(sampleFile);
        TextAnalyzer sourceAnalyzer = new TextAnalyzer();
        sourceAnalyzer.analyzeText(originFile);
        List<TextAnalyzer.WordStats> tenMostUsedInSample = sampleAnalyzer.getSortedStats().subList(0, 10);
        List<TextAnalyzer.WordStats> tenMostUsedInOrigin = sourceAnalyzer.getSortedStats().subList(0, 10);

        tenMostUsedInSample.removeIf(it -> it.word.length() == 1);
        tenMostUsedInOrigin.removeIf(it -> it.word.length() == 1);
        int offset = calculateOffset(tenMostUsedInOrigin, tenMostUsedInSample);
        long end = System.currentTimeMillis();

        System.out.println(String.format("Analyzing phase took %d milliseconds", end - start));
        System.out.println("Attempting to decode using offset=" + offset);
        new BruteForceDecoder(new Configuration(Mode.DECODE, originFile, offset, null)).decode();
    }

    private int calculateOffset(List<TextAnalyzer.WordStats> tenMostUsedInOrigin,
                                List<TextAnalyzer.WordStats> tenMostUsedInSample) {
        List<String> originWords = tenMostUsedInOrigin.stream().map(it -> it.word).collect(Collectors.toList());
        List<String> sampleWords = tenMostUsedInSample.stream().map(it -> it.word).collect(Collectors.toList());
        List<Integer> offsets = new ArrayList<>();

        for (String word : sampleWords) {
            List<String> sameLengthWordsFromOrigin = originWords.stream()
                    .filter(it -> it.length() == word.length())
                    .collect(Collectors.toList());
            for (String originWord : sameLengthWordsFromOrigin) {
                int currentOffset = analyzePair(word.toCharArray(), originWord.toCharArray());
                if (currentOffset != -1) {
                    offsets.add(currentOffset);
                }
            }
        }

        offsets = offsets.stream().distinct().collect(Collectors.toList());

        if (offsets.size() > 1) {
            System.out.println("SAD! Could not find uniform offset");
            System.exit(1);
        }
        return offsets.get(0);
    }

    private int analyzePair(char[] firstChars, char[] secondChars) {
        Integer offset = null;
        for (int i = 0; i < firstChars.length; i++) {
            int currentOffset = secondChars[i] - firstChars[i];
            if (currentOffset < 0) {
                int alphabetSize = getCorrespondingAlphabetSize(secondChars[i]);
                currentOffset = currentOffset + alphabetSize;
            }
            if (offset == null) {
                offset = currentOffset;
            }
            if (offset != currentOffset) {
                return -1;
            }
        }
        return offset;
    }

    private int getCorrespondingAlphabetSize(char c) {
        if (Dictionary.LOWERCASE_LATIN.contains(Character.toLowerCase(c))) {
            return Dictionary.LATIN_ALPHABET_SIZE;
        }
        if (Dictionary.LOWERCASE_CYRILLIC.contains(Character.toLowerCase(c))) {
            return Dictionary.CYRILLIC_ALPHABET_SIZE;
        }
        return 0;
    }
}