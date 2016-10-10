package model;

/**
 * Holds the required types of water reported.
 */
public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String name;

    WaterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
