package org.canteen_water.model;

/**
 * Describes the condition of water purity reported.
 */
public enum WaterPurityCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private String name;

    /**
     * Creates a new water condition with the given name.
     *
     * @param name label for this water condition
     */
    WaterPurityCondition(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
