package cz.los.encoder;

import cz.los.cmd.Configuration;
import cz.los.util.Dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Encoder {

    public static final int KILOBYTE = 1024;
    private final Path originFile;
    private final Path encodedFile;
    private final Map<Character, Character> shiftedDictionary;

    public Encoder(Configuration configuration) {
        this.originFile = configuration.getSourceFilePath();
        String encodedFileName = originFile.getFileName().toString().replace(".txt", "_encoded.txt");
        this.encodedFile = Paths.get(originFile.getParent().toString() + "/" + encodedFileName);
        this.shiftedDictionary = Collections.unmodifiableMap(Dictionary.provideShiftedDictionary(configuration.getKey()));
    }

    public void encode() {
        System.out.println("Starting encoding process...");
        long start = System.currentTimeMillis();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(originFile.toFile()), KILOBYTE);
             BufferedWriter fileWriter = new BufferedWriter(new FileWriter(encodedFile.toFile()), KILOBYTE)) {
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
        System.out.println(String.format("Encoding done. Check the file at %s", encodedFile));
        System.out.println(String.format("File encoding took %d milliseconds.", end - start));

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
