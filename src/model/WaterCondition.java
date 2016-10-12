package model;

/**
 * Describes the condition of water reported.
 */
public enum WaterCondition {
    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String name;

    WaterCondition(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
