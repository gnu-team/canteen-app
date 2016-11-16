package org.canteen_water.model.api;

public class ApiReportError extends ApiError {
    private String[] latitude;
    private String[] longitude;
    private String[] type;
    private String[] condition;
    private String[] description;

    @Override
    protected void fieldErrors(StringBuilder sb) {
        listReasons(sb, "Latitude", latitude);
        listReasons(sb, "Longitude", longitude);
        listReasons(sb, "Water Type", type);
        listReasons(sb, "Water Condition", condition);
        listReasons(sb, "Description", description);
    }
}
