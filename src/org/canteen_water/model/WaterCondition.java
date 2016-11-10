package org.canteen_water.model;

/**
 * Describes the condition of water reported.
 */
public enum WaterCondition {
    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private String name;

    /**
     * Creates a new water condition with the given name.
     *
     * @param name label for this water condition
     */
    WaterCondition(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
