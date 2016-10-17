package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import exception.DataBackendException;
import exception.InvalidUserException;
import exception.NoSuchUserException;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by Austin Adams on 10/10/16.
 */
public class ApiDataSource implements DataSource {
    private GsonBuilder gsonBuilder;
    private String user, password;
    private static final String API_ENDPOINT = "https://canteen.austinjadams.com";

    public ApiDataSource() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
        gsonBuilder.registerTypeAdapter(WaterType.class, new WaterTypeDeserializer());
        gsonBuilder.registerTypeAdapter(WaterCondition.class, new WaterConditionDeserializer());
        gsonBuilder.registerTypeAdapter(AccountType.class, new AccountTypeDeserializer());
    }

    @Override
    public User authenticate(String user, String password) throws DataBackendException, NoSuchUserException {
        HttpURLConnection conn = request("GET", "/", user, password);
        int statusCode;
        try {
            statusCode = conn.getResponseCode();
        } catch (IOException e) {
            throw new DataBackendException("Could not make login request successfully", e);
        }

        if (statusCode != HttpURLConnection.HTTP_OK) {
            ApiError err = parseError(conn);

            if (err == null) {
                throw new DataBackendException("Bad status code " + statusCode);
            } else {
                throw new DataBackendException(err.getDetail());
            }
        }

        // Set credentials only if login succeeded
        this.user = user;
        this.password = password;

        // For now, always return null.
        return null;
    }

    @Override
    public void addUser(User userdata) throws DataBackendException, InvalidUserException {
        HttpURLConnection conn = request("POST", "/users/");

        OutputStream request;
        try {
            request = conn.getOutputStream();
        } catch (IOException e) {
            throw new DataBackendException("Could not make request registering new user", e);
        }

        OutputStreamWriter writer = new OutputStreamWriter(request);
        Gson gson = gsonBuilder.create();
        gson.toJson(userdata, writer);
        try {
            writer.close();
        } catch (IOException e) {
            throw new DataBackendException("Flushing request body", e);
        }

        int statusCode;
        try {
            statusCode = conn.getResponseCode();
        } catch (IOException e) {
            throw new DataBackendException("Retrieving response code", e);
        }

        if (statusCode != HttpURLConnection.HTTP_CREATED) {
            ApiError err = parseError(conn);

            if (err == null) {
                throw new DataBackendException("Bad status code " + statusCode);
            } else {
                throw new DataBackendException(err.getDetail());
            }
        }

        // Now that we've created the new account, test login
        try {
            authenticate(userdata.getUser(), userdata.getPassword());
        } catch (NoSuchUserException e) {
            throw new InvalidUserException(e.getMessage());
        }
    }

    @Override
    public void addReport(Report report) throws DataBackendException {
        HttpURLConnection conn = request("POST", "/reports/");

        OutputStream request;
        try {
            request = conn.getOutputStream();
        } catch (IOException e) {
            throw new DataBackendException("Could not make request submitting new report", e);
        }

        OutputStreamWriter writer = new OutputStreamWriter(request);
        Gson gson = gsonBuilder.create();
        gson.toJson(report, writer);
        try {
            writer.close();
        } catch (IOException e) {
            throw new DataBackendException("Flushing request body", e);
        }

        int statusCode;
        try {
            statusCode = conn.getResponseCode();
        } catch (IOException e) {
            throw new DataBackendException("Retrieving response code", e);
        }

        if (statusCode != HttpURLConnection.HTTP_CREATED) {
            ApiError err = parseError(conn);

            if (err == null) {
                throw new DataBackendException("Bad status code " + statusCode);
            } else {
                throw new DataBackendException(err.getDetail());
            }
        }
    }

    @Override
    public Collection<Report> listReports() throws DataBackendException {
        HttpURLConnection conn = request("GET", "/reports/");

        int statusCode;
        try {
            statusCode = conn.getResponseCode();
        } catch (IOException e) {
            throw new DataBackendException("Retrieving response code", e);
        }

        if (statusCode != HttpURLConnection.HTTP_OK) {
            ApiError err = parseError(conn);

            if (err == null) {
                throw new DataBackendException("Bad status code " + statusCode);
            } else {
                throw new DataBackendException(err.getDetail());
            }
        }

        InputStream response;

        try {
            response = conn.getInputStream();
        } catch (IOException e) {
            throw new DataBackendException("Could not request report list", e);
        }

        Gson gson = gsonBuilder.create();
        Report[] reports = gson.fromJson(new InputStreamReader(response), Report[].class);

        return Arrays.asList(reports);
    }

    private HttpURLConnection request(String method, String path) throws DataBackendException {
        return request(method, path, user, password);
    }

    private HttpURLConnection request(String method, String path, String user, String password) throws DataBackendException {
        URL url;

        try {
            url = new URL(API_ENDPOINT + path);
        } catch (MalformedURLException e) {
            throw new DataBackendException("Malformed URL", e);
        }

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new DataBackendException("Opening request connection", e);
        }

        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new DataBackendException("Bad protocol", e);
        }

        // To get a JSON response, set the Accept header
        conn.setRequestProperty("Accept", "application/json");

        // If it's a POST, we need to send a request body
        if (method.equals("POST")) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        }

        if (user != null && password != null) {
            // Set the Authorization header
            // http://stackoverflow.com/a/12603622/321301
            String userpass = user + ":" + password;
            String authblob = DatatypeConverter.printBase64Binary(userpass.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + authblob);
        }

        return conn;
    }

    private ApiError parseError(HttpURLConnection conn) {
        ApiError err = null;
        InputStream response = conn.getErrorStream();

        if (response != null) {
            Gson gson = gsonBuilder.create();
            err = gson.fromJson(new InputStreamReader(response), ApiError.class);
        }

        return err;
    }

    private class UserDeserializer implements JsonDeserializer<User> {
        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            User u = new User();
            u.setName(json.getAsString());
            return u;
        }
    }

    private class WaterTypeDeserializer implements JsonSerializer<WaterType>, JsonDeserializer<WaterType> {
        @Override
        public JsonElement serialize(WaterType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Arrays.asList(WaterType.values()).indexOf(src));
        }

        @Override
        public WaterType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterType.values()[json.getAsInt()];
        }
    }

    private class WaterConditionDeserializer implements JsonSerializer<WaterCondition>, JsonDeserializer<WaterCondition> {
        @Override
        public JsonElement serialize(WaterCondition src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Arrays.asList(WaterCondition.values()).indexOf(src));
        }

        @Override
        public WaterCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterCondition.values()[json.getAsInt()];
        }
    }

    private class AccountTypeDeserializer implements JsonSerializer<AccountType> {
        @Override
        public JsonElement serialize(AccountType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString() + "s");
        }
    }
}
