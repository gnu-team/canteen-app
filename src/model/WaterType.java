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

    /**
     * Creates a new water type with the given name.
     *
     * @param name label for this water type
     */
    WaterType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
