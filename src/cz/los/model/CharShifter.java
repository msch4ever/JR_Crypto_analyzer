package cz.los.model;

import cz.los.cmd.Configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class CharShifter {

    protected static final int KILOBYTE = 1024;
    protected final Path originFile;
    protected final Integer key;
    protected final Path destinationFile;
    protected final Map<Character, Character> shiftedDictionary;

    public CharShifter(Configuration configuration) {
        this.originFile = configuration.getSourceFilePath();
        this.key = configuration.getKey();
        String destinationFileName = resolveDestinationFileName();
        this.destinationFile = Paths.get(originFile.getParent().toString() + "/" + destinationFileName);
        this.shiftedDictionary = this.key == null ? Collections.emptyMap() : resolveDictionary(configuration);
    }

    protected abstract String resolveDestinationFileName();

    protected abstract Map<Character, Character> resolveDictionary(Configuration configuration);

    public void shiftCharsInFile() {
        long start = System.currentTimeMillis();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(originFile.toFile()), KILOBYTE);
             BufferedWriter fileWriter = new BufferedWriter(new FileWriter(destinationFile.toFile()), KILOBYTE)) {
            System.out.println("Reading and writing in chunks...");
            int counter = 1;
            while (fileReader.ready()) {
                char[] buffer = new char[KILOBYTE];
                int readCount = fileReader.read(buffer);
                if (readCount != KILOBYTE) {
                    buffer = Arrays.copyOf(buffer, readCount);
                }
                introduceOffset(buffer);
                fileWriter.write(buffer);
                Arrays.fill(buffer, (char) 0);
                System.out.println(String.format("Writing of chunk #%s done...", counter));
                counter++;
            }
        } catch (IOException e) {
            System.out.println("SAD! Something went wrong!");
            e.printStackTrace();
            System.out.println("Exiting...");
            System.exit(1);
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("File processing took %d milliseconds.", end - start));
    }

    private void introduceOffset(char[] buffer) {
        for (int i = 0; i < buffer.length; i++) {
            char current = buffer[i];
            if (Character.isAlphabetic(current)) {
                current = shiftedDictionary.get(current);
                buffer[i] = current;
            }
        }
    }
}
