package model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private int id;
    private String reportNumber;

    public Report(User creator, String location, WaterType type, WaterCondition condition) {
        this.date = new Date();
        this.creator = creator;
        this.location = location;
        this.type = type;
        this.condition = condition;
    }

    public String getDateFormat() {
        String dateStr = String.format("%tD", date); // mm/dd/yy
        String timeStr = String.format(" %tl:%<tM%<tp", date); // 5:19pm
        return dateStr + timeStr;
    }

    public Date getDate() {
        return this.date;
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

    /**
     * Sets the id
     * @param id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Make a foramt for a report number
     * ex) S-0001
     * S means Source Report
     * @return report number which changed to the format
     */
    public String getReportNumber() {

        NumberFormat nf = new DecimalFormat("0000"); // ex) Making S-0001
        //S means Source, P will mean purity , and H will mean Historical
        String reportNumber = "S-" + nf.format(id);
        return reportNumber;
    }
}
