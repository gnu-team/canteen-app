package org.canteen_water.model.api;

import com.google.gson.annotations.SerializedName;

public class ApiUserError extends ApiError {
    private String[] username;
    @SerializedName("first_name")
    private String[] firstName;
    @SerializedName("last_name")
    private String[] lastName;
    private String[] email;
    private String[] group;
    private String[] password;
    private String[] phone;
    private String[] address;
    private String[] bio;

    @Override
    public void fieldErrors(StringBuilder sb) {
        listReasons(sb, "User name", username);
        listReasons(sb, "First name", firstName);
        listReasons(sb, "Last name", lastName);
        listReasons(sb, "Email address", email);
        listReasons(sb, "Group name", group);
        listReasons(sb, "Password", password);
        listReasons(sb, "Phone number", phone);
        listReasons(sb, "Address", address);
        listReasons(sb, "Bio", bio);
    }
}
