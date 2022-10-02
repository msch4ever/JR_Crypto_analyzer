package cz.los.app;

import cz.los.model.BruteForceDecoder;
import cz.los.model.Encoder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.Optional;

public class Ui extends JFrame{

    private JButton encodeButton;
    private JButton decodeButton;
    private JButton bruteForceButton;
    private JLabel header;

    private final JFileChooser fc = new JFileChooser();

    public Ui() {
        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        header = new JLabel("This app allows you to encode/decode your '.txt' file using Cesar's cypher.");
        header.setFont(new Font("BOLD", Font.BOLD, 15));
        header.setHorizontalAlignment(SwingConstants.CENTER);

        encodeButton = new JButton("Encode");
        encodeButton.setToolTipText("Provide a '.txt' file and character shift value to encode it.");

        decodeButton = new JButton("Decode");
        decodeButton.setToolTipText("Provide encoded '.txt' file and character shift value to decode it.");

        bruteForceButton = new JButton("Brute Force");
        bruteForceButton.setToolTipText("Provide encoded file and a sample text from the same author to decode it.");

        createLayout(header, encodeButton, decodeButton, bruteForceButton, panel);
        encodeButton.addActionListener(this::actionPerformed);
        decodeButton.addActionListener(this::actionPerformed);
        bruteForceButton.addActionListener(this::actionPerformed);

        setTitle("Encryption machine");
        setSize(700, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup().addComponent(arg[0], 100, 250, 700))
                .addGroup(gl.createSequentialGroup().addComponent(arg[1], 100, 250, 700))
                .addGroup(gl.createSequentialGroup().addComponent(arg[2], 100, 250, 700))
                .addGroup(gl.createSequentialGroup().addComponent(arg[3], 100, 250, 700))
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGap(10)
                .addComponent(arg[0])
                .addGap(20)
                .addComponent(arg[1])
                .addGap(4)
                .addComponent(arg[2])
                .addGap(4)
                .addComponent(arg[3])
                .addGap(4)
        );
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        Configuration.ConfigBuilder configBuilder = Configuration.builder();
        if (e.getSource() == encodeButton) {
            processFile(configBuilder, Mode.ENCODE);
        } else if (e.getSource() == decodeButton) {
            processFile(configBuilder, Mode.DECODE);
        } else if (e.getSource() == bruteForceButton) {
            decodeBruteForce(configBuilder);
        }
        fc.setDialogTitle("Open");
    }

    private void processFile(Configuration.ConfigBuilder configBuilder, Mode mode) {
        configBuilder.mode(mode);
        JOptionPane.showMessageDialog(this, String.format("You will be prompted to do the following\n    * pick the file you want to %s\n    * choose the offset for encoding", mode.fullName));
        Optional<Path> sourcePath = pickTheFile();
        if (!sourcePath.isPresent()) {
            return;
        }
        configBuilder.sourceFilePath(sourcePath.get());

        configBuilder.key(pickOffset());

        int answer = JOptionPane.showConfirmDialog(this, String.format("Do you want to %s provided file?", mode.fullName));

        if (answer == 0) {
            new Encoder(configBuilder.build()).encode();
            JOptionPane.showMessageDialog(this, String.format("You can find your %sed file in the same directory as the source.", mode.fullName));
        } else {
            System.out.println(String.format("File %s process aborted...", mode.fullName));
        }
    }

    private void decodeBruteForce(Configuration.ConfigBuilder configBuilder) {
        configBuilder.mode(Mode.BRUTE_FORCE);
        JOptionPane.showMessageDialog(this, String.format("You will be prompted to do the following\n*pick the file you want to decode\n*provide a sample file for decoding algorithm"));
        fc.setDialogTitle("PICK FILE TO DECODE");
        Optional<Path> sourcePath = pickTheFile();
        if (!sourcePath.isPresent()) {
            return;
        }
        configBuilder.sourceFilePath(sourcePath.get());
        fc.setDialogTitle("PICK SAMPLE FILE");
        Optional<Path> samplePath = pickTheFile();
        if (!samplePath.isPresent()) {
            return;
        }
        configBuilder.sampleFilePath(samplePath.get());

        int answer = JOptionPane.showConfirmDialog(this, "Do you want to decode provided file?");

        if (answer == 0) {
            new BruteForceDecoder(configBuilder.build()).decodeBruteForce();
            JOptionPane.showMessageDialog(this, "You can find your decoded file in the same directory as the source.");
        } else {
            System.out.println("File decode process aborted...");
        }
    }

    private Optional<Path> pickTheFile() {
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Path sourcePath = fc.getSelectedFile().toPath();
            System.out.println(String.format("Opening: %s", sourcePath));
            return Optional.of(sourcePath);
        } else {
            System.out.println("File was not picked.");
        }
        return Optional.empty();
    }

    private int pickOffset() {
        while(true) {
            String input = JOptionPane.showInputDialog(this, "What offset you want to apply to your file?");
            int offsetValue;
            if (!input.isEmpty()) {
                try {
                    offsetValue = Integer.parseInt(input);
                    return offsetValue;
                } catch (Throwable t) {
                    JOptionPane.showMessageDialog(this, "Could not parse your input. Try again!");
                }
            }
        }
    }
}
