package model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import com.google.gson.annotations.SerializedName;

/**
 * Represents a water source report.
 */
public class Report implements MapPin {
    private int id;
    private Date date;
    @SerializedName("creator_name")
    private User creator;
    private double latitude;
    private double longitude;
    private WaterType type;
    private WaterCondition condition;
    private String description;

    public Report(User creator, double latitude, double longitude, WaterType type, WaterCondition condition, String description) {
        this.date = new Date();
        this.creator = creator;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.condition = condition;
        this.description = description;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the id
     * @param id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Make a format for a report number
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

    public String getSummary() {
        return String.format("%s: %s %s", getReportNumber(), condition, type);
    }
}
