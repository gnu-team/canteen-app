package model;

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Represents a water purity report.
 */
public class PurityReport implements MapPin {
    private int id;
    private Date date;
    @SerializedName("creator_name")
    private User creator;
    private double latitude;
    private double longitude;
    private double virusPPM;
    private double contaminantPPM;
    private WaterPurityCondition condition;
    private String description;

    public PurityReport(User creator, double latitude, double longitude, double virusPPM, double contaminantPPM, WaterPurityCondition condition, String description) {
        this.date = new Date();
        this.creator = creator;
        this.latitude = latitude;
        this.longitude = longitude;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
        this.condition = condition;
        this.description = description;
    }

    public String getDateFormat() {
        String dateStr = String.format("%tD", date); // mm/dd/yy
        String timeStr = String.format(" %tl:%<tM%<tp", date); // 5:19pm
        return dateStr + timeStr;
    }

    public Date getDate() {
        return new Date(date);
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

    public double getVirusPPM() {
        return virusPPM;
    }

    public void setVirusPPM(double virusPPM) {
        this.virusPPM = virusPPM;
    }

    public double getContaminantPPM() {
        return contaminantPPM;
    }

    public void setContaminantPPM(double contaminantPPM) {
        this.contaminantPPM = contaminantPPM;
    }

    public WaterPurityCondition getCondition() {
        return condition;
    }

    public void setCondition(WaterPurityCondition condition) {
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
        return "P-" + nf.format(id);
    }

    @Override
    public String getColor() {
        return MapPin.COLOR_YELLOW;
    }

    @Override
    public String getSummary() {
        return String.format("%s: %s", getReportNumber(), condition);
    }
}
