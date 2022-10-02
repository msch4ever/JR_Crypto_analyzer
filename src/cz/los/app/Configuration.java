package cz.los.app;

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

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
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

    public static class ConfigBuilder {

        private Mode mode;
        private Path sourceFilePath;
        private Integer key;
        private Path sampleFilePath;

        public Configuration build() {
            return new Configuration(mode, sourceFilePath, key, sampleFilePath);
        }

        public ConfigBuilder mode(Mode mode) {
            this.mode = mode;
            return this;
        }

        public ConfigBuilder sourceFilePath(Path sourceFilePath) {
            this.sourceFilePath = sourceFilePath;
            return this;
        }

        public ConfigBuilder key(Integer key) {
            this.key = key;
            return this;
        }

        public ConfigBuilder sampleFilePath(Path sampleFilePath) {
            this.sampleFilePath = sampleFilePath;
            return this;
        }
    }
}
