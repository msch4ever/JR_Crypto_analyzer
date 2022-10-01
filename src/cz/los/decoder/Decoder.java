package cz.los.decoder;

import cz.los.cmd.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Decoder {

    private final Path encodedFile;
    private final Path sampleFile;
    private final Path decodedFile;

    public Decoder(Configuration configuration) {
        this.encodedFile = configuration.getSourceFilePath();
        this.sampleFile = configuration.getSampleFilePath();
        this.decodedFile = Paths.get(encodedFile.getParent().toString() + encodedFile.getFileName() + "_decoded.txt");
    }

    public void decode() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Decoder{" +
                "encodedFile=" + encodedFile +
                ", sampleFile=" + sampleFile +
                ", decodedFile=" + decodedFile +
                '}';
    }
}
