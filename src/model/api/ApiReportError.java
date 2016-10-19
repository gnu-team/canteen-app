package model.api;

public class ApiReportError extends ApiError {
    private String[] latitude;
    private String[] longitude;
    private String[] type;
    private String[] condition;

    public String getDetail() {
        StringBuilder sb = new StringBuilder();
        listReasons(sb, "Latitude", latitude);
        listReasons(sb, "Longitude", longitude);
        listReasons(sb, "Water Type", type);
        listReasons(sb, "Water Condition", condition);

        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }
}
