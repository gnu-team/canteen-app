package model.api;

public class ApiPurityReportError extends ApiError {
    private String[] latitude;
    private String[] longitude;
    private String[] virusPPM;
    private String[] contaminantPPM;
    private String[] condition;
    private String[] description;

    @Override
    protected String fieldErrors() {
        StringBuilder sb = new StringBuilder();
        listReasons(sb, "Latitude", latitude);
        listReasons(sb, "Longitude", longitude);
        listReasons(sb, "Virus PPM", virusPPM);
        listReasons(sb, "Contaminant PPM", contaminantPPM);
        listReasons(sb, "Condition", condition);
        listReasons(sb, "Description", description);
        return (sb.length() > 0) ? sb.toString() : null;
    }
}
