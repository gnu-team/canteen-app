package model.api;

public class ApiUserError extends ApiError {
    private String[] username;
    private String[] group;
    private String[] password;

    @Override
    public String fieldErrors() {
        StringBuilder sb = new StringBuilder();
        listReasons(sb, "User name", username);
        listReasons(sb, "Group name", group);
        listReasons(sb, "Password", password);
        return (sb.length() > 0) ? sb.toString() : null;
    }
}
