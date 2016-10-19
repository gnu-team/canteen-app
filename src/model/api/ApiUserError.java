package model.api;

public class ApiUserError extends ApiError {
    private String[] username;
    private String[] group;
    private String[] password;

    public String getDetail() {
        StringBuilder sb = new StringBuilder();
        listReasons(sb, "User name", username);
        listReasons(sb, "Group name", group);
        listReasons(sb, "Password", password);

        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }
}
