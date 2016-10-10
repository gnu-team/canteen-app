package model;

import java.util.Date;

/**
 * Represents a water source report.
 */
public class Report {
    private Date date;
    private User creator;
    // TODO: Use geocoordinates instead of a String
    private String location;
    private WaterType type;
    private WaterCondition condition;

    public Report(Date date, User creator, String location, WaterType type, WaterCondition condition) {
        this.date = date;
        this.creator = creator;
        this.location = location;
        this.type = type;
        this.condition = condition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public WaterType getType() {
        return type;
    }

    public void setType(WaterType type) {
        this.type = type;
    }

    public WaterCondition getCondition() {
        return condition;
    }

    public void setCondition(WaterCondition condition) {
        this.condition = condition;
    }
}
