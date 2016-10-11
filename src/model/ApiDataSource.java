package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import exception.InvalidUserException;
import exception.NoSuchUserException;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Austin Adams on 10/10/16.
 */
public class ApiDataSource implements DataSource {
    private GsonBuilder gsonBuilder;
    private String user, password;
    private static final String API_ENDPOINT = "http://localhost:8000";

    public ApiDataSource() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
        gsonBuilder.registerTypeAdapter(WaterType.class, new WaterTypeDeserializer());
        gsonBuilder.registerTypeAdapter(WaterCondition.class, new WaterConditionDeserializer());
    }

    @Override
    public User authenticate(String user, String password) throws NoSuchUserException {
        this.user = user;
        this.password = password;

        int statusCode;
        try {
            statusCode = request("GET", "/").getResponseCode();
        // TODO: Handle this better
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (statusCode == HttpURLConnection.HTTP_FORBIDDEN) {
            throw new NoSuchUserException("Authentication failed");
        } else if (statusCode != HttpURLConnection.HTTP_OK) {
            // TODO: Include detail from response body
            throw new NoSuchUserException("Bad status code " + statusCode);
        }

        // For now, always return null.
        return null;
    }

    @Override
    public void addUser(User userdata) throws InvalidUserException {
        throw new UnsupportedOperationException("addUser() is not implemented yet");
    }

    @Override
    public void addReport(Report report) {
        throw new UnsupportedOperationException("addReport() is not implemented yet");
    }

    @Override
    public Collection<Report> listReports() {
        HttpURLConnection conn = request("GET", "/reports/");

        InputStream response;
        try {
            response = conn.getInputStream();
            // TODO: Handle this better
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = gsonBuilder.create();
        Report[] reports = gson.fromJson(new InputStreamReader(response), Report[].class);

        return Arrays.asList(reports);
    }

    private HttpURLConnection request(String method, String path) {
        URL url;

        try {
            url = new URL(API_ENDPOINT + path);
        // TODO: Handle this better
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
        // TODO: Handle this better
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            conn.setRequestMethod(method);
        // TODO: Handle this better
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        }

        // Set the Authorization header
        // http://stackoverflow.com/a/12603622/321301
        String userpass = user + ":" + password;
        String authblob = DatatypeConverter.printBase64Binary(userpass.getBytes());
        conn.setRequestProperty("Authorization", "Basic " + authblob);

        // To get a JSON response, set the Accept header
        conn.setRequestProperty("Accept", "application/json");

        return conn;
    }

    private class UserDeserializer implements JsonDeserializer<User> {
        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            User u = new User();
            u.setName(json.getAsString());
            return u;
        }
    }

    private class WaterTypeDeserializer implements JsonDeserializer<WaterType> {
        @Override
        public WaterType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterType.values()[json.getAsInt()];
        }
    }

    private class WaterConditionDeserializer implements JsonDeserializer<WaterCondition> {
        @Override
        public WaterCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterCondition.values()[json.getAsInt()];
        }
    }
}
