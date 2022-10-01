package cz.los.cmd;

import java.nio.file.Path;

public class Configuration {

    private final Mode mode;
    private final Path sourceFilePath;
    private final Integer key;
    private final Path sampleFilePath;

    public Configuration(Mode mode, Path sourceFilePath, Integer key, Path sampleFilePath) {
        this.mode = mode;
        this.sourceFilePath = sourceFilePath;
        this.key = key;
        this.sampleFilePath = sampleFilePath;
    }

    public Mode getMode() {
        return mode;
    }

    public Path getSourceFilePath() {
        return sourceFilePath;
    }

    public Integer getKey() {
        return key;
    }

    public Path getSampleFilePath() {
        return sampleFilePath;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "mode=" + mode +
                ", sourceFilePath=" + sourceFilePath +
                ", key=" + key +
                ", sampleFilePath=" + sampleFilePath +
                '}';
    }
}
