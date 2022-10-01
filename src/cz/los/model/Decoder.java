package cz.los.model;

import cz.los.cmd.Configuration;
import cz.los.util.Dictionary;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Decoder extends CharShifter {

    private final Path sampleFile;

    public Decoder(Configuration configuration) {
       super(configuration);
       this.sampleFile = configuration.getSampleFilePath();
    }

    @Override
    protected String resolveDestinationFileName() {
        return originFile.getFileName().toString().replace(".txt", "_decoded.txt");
    }

    @Override
    protected Map<Character, Character> resolveDictionary(Configuration configuration) {
        Map<Character, Character> defaultShiftedDictionary =
                Collections.unmodifiableMap(Dictionary.provideShiftedDictionary(configuration.getKey()));
        return defaultShiftedDictionary.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public void decode() {
        System.out.println("Starting decoding process...");
        shiftCharsInFile();
        System.out.println(String.format("Decoding done. Check the file at %s", destinationFile));
    }
}
