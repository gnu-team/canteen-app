package org.canteen_water.model;

/**
 * Represents an object capable of being drawn as a pin on the map.
 */
public interface MapPin {
    String COLOR_BLUE = "drop";
    String COLOR_YELLOW = "yellow-drop";

    String getColor();
    String getSummary();
    double getLatitude();
    double getLongitude();
    String getDescription();
}
