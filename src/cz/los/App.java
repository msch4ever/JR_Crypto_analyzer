package cz.los;

import cz.los.cmd.CmdParser;
import cz.los.cmd.Configuration;
import cz.los.cmd.Mode;
import cz.los.model.BruteForceDecoder;
import cz.los.model.Decoder;
import cz.los.model.Encoder;

public class App {

    private final Configuration config;

    public App(Configuration config) {
        this.config = config;
    }

    public static void main(String[] args) {
        App cryptoMachine = new App(new CmdParser().parseCmd(args));
        cryptoMachine.run();
    }

    private void run() {
        Mode mode = config.getMode();
        switch (mode) {
            case ENCODE:
                new Encoder(config).encode();
                break;
            case DECODE:
                new Decoder(config).decode();
                break;
            case BRUTE_FORCE:
                new BruteForceDecoder(config).decodeBruteForce();
        }
    }

}