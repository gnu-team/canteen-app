package model.api;

public class ApiReportError extends ApiError {
    private String[] latitude;
    private String[] longitude;
    private String[] type;
    private String[] condition;
    private String[] description;

    @Override
    protected String fieldErrors() {
        StringBuilder sb = new StringBuilder();
        listReasons(sb, "Latitude", latitude);
        listReasons(sb, "Longitude", longitude);
        listReasons(sb, "Water Type", type);
        listReasons(sb, "Water Condition", condition);
        listReasons(sb, "Description", description);
        return (sb.length() > 0) ? sb.toString() : null;
    }
}
