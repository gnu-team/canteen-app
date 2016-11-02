package model;

/**
 * Represents an object capable of being drawn as a pin on the map.
 */
public interface MapPin {
    String getSummary();
    double getLatitude();
    double getLongitude();
    String getDescription();
}
