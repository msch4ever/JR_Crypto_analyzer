package cz.los.model;

import cz.los.cmd.Configuration;
import cz.los.util.Dictionary;

import java.util.Collections;
import java.util.Map;

public class Encoder extends CharShifter {

    public Encoder(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected String resolveDestinationFileName() {
        return originFile.getFileName().toString().replace(".txt", "_encoded.txt");
    }

    @Override
    protected Map<Character, Character> resolveDictionary(Configuration configuration) {
        return Collections.unmodifiableMap(Dictionary.provideShiftedDictionary(configuration.getKey()));
    }

    public void encode() {
        System.out.println("Starting encoding process...");
        shiftCharsInFile();
        System.out.println(String.format("Encoding done. Check the file at %s", destinationFile));
    }
}
