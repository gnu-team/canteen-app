package model.api;

public class ApiUserError extends ApiError {
    private String[] username;
    private String[] first_name;
    private String[] last_name;
    private String[] email;
    private String[] group;
    private String[] password;
    private String[] phone;
    private String[] address;
    private String[] bio;

    @Override
    public String fieldErrors() {
        StringBuilder sb = new StringBuilder();
        listReasons(sb, "User name", username);
        listReasons(sb, "First name", first_name);
        listReasons(sb, "Last name", last_name);
        listReasons(sb, "Email address", email);
        listReasons(sb, "Group name", group);
        listReasons(sb, "Password", password);
        listReasons(sb, "Phone number", phone);
        listReasons(sb, "Address", address);
        listReasons(sb, "Bio", bio);
        return (sb.length() > 0) ? sb.toString() : null;
    }
}
