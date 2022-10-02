package cz.los.app;

import cz.los.model.BruteForceDecoder;
import cz.los.model.Decoder;
import cz.los.model.Encoder;

import javax.swing.*;
import java.awt.*;

public class App {

    private final Configuration config;

    public App(Configuration config) {
        this.config = config;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            runUiApp();
        } else {
            runCmdApp(args);
        }
    }

    private static void runUiApp() {
        EventQueue.invokeLater(() -> {
            Ui runner = new Ui();
            runner.setVisible(true);

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void runCmdApp(String[] args) {
        Configuration config = new CmdParser().parseCmd(args);
        App cryptoMachine = new App(config);
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