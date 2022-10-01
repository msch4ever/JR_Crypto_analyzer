package cz.los;

import cz.los.cmd.CmdParser;
import cz.los.cmd.Configuration;

public class App {

    private final Configuration config;

    public App(Configuration config) {
        this.config = config;
    }

    public static void main(String[] args) {
        App cryptoMachine = new App(new CmdParser().parseCmd(args));
        System.out.println(cryptoMachine.config);
    }

}