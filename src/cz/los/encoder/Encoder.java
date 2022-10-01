package cz.los.encoder;

import cz.los.cmd.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Encoder {

    private final Path originFile;
    private final Path encodedFile;
    private final int key;

    public Encoder(Configuration configuration) {
        this.originFile = configuration.getSourceFilePath();
        this.encodedFile = Paths.get(originFile.getParent().toString() + originFile.getFileName() + "_encoded.txt");
        this.key = configuration.getKey();
    }

    public void encode() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Encoder{" +
                "originFile=" + originFile +
                ", encodedFile=" + encodedFile +
                ", key=" + key +
                '}';
    }
}
