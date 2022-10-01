package cz.los.cmd;

public enum Mode {

    ENCODE("e", "encode"),
    DECODE("d", "decode"),
    BRUTE_FORCE("bf", "brute_force");

    public final String shortName;
    public final String fullName;

    Mode(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return this.name() + "(" + shortName + ", " + fullName + ")";
    }
}
