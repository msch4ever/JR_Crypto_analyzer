package cz.los;

import cz.los.cmd.CmdParser;
import cz.los.cmd.Configuration;
import cz.los.cmd.Mode;
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
        if (Mode.ENCODE.equals(config.getMode())) {
            new Encoder(config).encode();
        } else {
            new Decoder(config).decode();
        }
    }

}