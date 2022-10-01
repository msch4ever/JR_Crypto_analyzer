package cz.los.cmd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class CmdParser {

    public Configuration parseCmd(String[] args) {
        validateArgsLength(args);
        Mode mode = parseMode(args[0]);
        Path filePath = parseFilePath(args[1]);
        Integer key = null;
        Path sampleFilePath = null;
        if (Mode.BRUTE_FORCE.equals(mode)) {
            System.out.println("No encryption key provided.");
            sampleFilePath = parseFilePath(args[2]);
            System.out.println("Using a sample file for text analysis.");
        } else {
            key = Integer.parseInt(args[2]);
        }
        return new Configuration(mode, filePath, key, sampleFilePath);
    }

    private Mode parseMode(String cmdMode) {
        Mode[] values = Mode.values();
        for (Mode mode : values) {
            if (mode.shortName.equalsIgnoreCase(cmdMode) || mode.fullName.equals(cmdMode)) {
                return mode;
            }
        }
        String msg = String.format(
                "Could not parse mode argument!\nProvided: %s;\nExpected: %s",
                cmdMode, Arrays.toString(values));
        throw new IllegalArgumentException(msg);
    }

    private Path parseFilePath(String pathString) {
        Path path = Paths.get(pathString);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            throw new IllegalArgumentException("File with provided path does not exists or is a directory. Path=" + pathString);
        }
        return path;
    }

    private void validateArgsLength(String[] args) {
        if (args.length > 3) {
            System.out.println("Invalid program arguments provided.");
            throw new IllegalArgumentException("Args length should be 3. Provided " + args.length);
        }
    }

}
