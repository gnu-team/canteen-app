package model.api;

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
import model.AccountType;
import model.DataSource;
import model.PurityReport;
import model.Report;
import model.User;
import model.WaterCondition;
import model.WaterPurityCondition;
import model.WaterType;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;

public class ApiDataSource implements DataSource {
    private GsonBuilder gsonBuilder;
    private String user, password;

    public ApiDataSource() {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
        gsonBuilder.registerTypeAdapter(WaterType.class, new WaterTypeDeserializer());
        gsonBuilder.registerTypeAdapter(WaterCondition.class, new WaterConditionDeserializer());
        gsonBuilder.registerTypeAdapter(WaterPurityCondition.class, new WaterPurityConditionDeserializer());
        gsonBuilder.registerTypeAdapter(AccountType.class, new AccountTypeDeserializer());
    }

    @Override
    public User authenticate(String user, String password) throws DataBackendException, NoSuchUserException {
        ApiConnection conn = new ApiConnection("GET", "/", HttpURLConnection.HTTP_OK, user, password);
        conn.connect();

        // Store credentials only if login succeeded
        this.user = user;
        this.password = password;

        // For now, always return null.
        return null;
    }

    @Override
    public void addUser(User userdata) throws DataBackendException, InvalidUserException {
        // If we've already authenticated, don't use the stored
        // username/password pair when registering
        ApiConnection<ApiUserError> conn = new ApiConnection<>("POST", "/users/", HttpURLConnection.HTTP_CREATED, ApiUserError.class, null, null);
        Writer request = conn.getRequestWriter();

        Gson gson = gsonBuilder.create();
        gson.toJson(userdata, request);

        conn.closeRequest(request);
        conn.connect();

        // Now that we've created the new account, test login
        try {
            authenticate(userdata.getUser(), userdata.getPassword());
        } catch (NoSuchUserException e) {
            throw new InvalidUserException(e.getMessage());
        }
    }

    @Override
    public void addReport(Report report) throws DataBackendException {
        ApiConnection<ApiReportError> conn = new ApiConnection<>("POST", "/reports/", HttpURLConnection.HTTP_CREATED, ApiReportError.class, user, password);
        Writer request = conn.getRequestWriter();

        Gson gson = gsonBuilder.create();
        gson.toJson(report, request);

        conn.closeRequest(request);
        conn.connect();
    }

    @Override
    public void addPurityReport(PurityReport purityReport) throws DataBackendException {
        ApiConnection<ApiPurityReportError> conn = new ApiConnection<>("POST", "/purity_reports/", HttpURLConnection.HTTP_CREATED, ApiPurityReportError.class, user, password);
        Writer request = conn.getRequestWriter();

        Gson gson = gsonBuilder.create();
        gson.toJson(purityReport, request);

        conn.closeRequest(request);
        conn.connect();
    }

    @Override
    public Collection<Report> listReports() throws DataBackendException {
        ApiConnection conn = new ApiConnection("GET", "/reports/", HttpURLConnection.HTTP_OK, user, password);
        Reader response = conn.getResponseReader();

        Gson gson = gsonBuilder.create();
        Report[] reports = gson.fromJson(response, Report[].class);

        return Arrays.asList(reports);
    }

    @Override
    public Collection<PurityReport> listPurityReports() throws DataBackendException {
        ApiConnection conn = new ApiConnection("GET", "/purity_reports/", HttpURLConnection.HTTP_OK, user, password);
        Reader response = conn.getResponseReader();

        Gson gson = gsonBuilder.create();
        PurityReport[] reports = gson.fromJson(response, PurityReport[].class);

        return Arrays.asList(reports);
    }

    private class UserDeserializer implements JsonDeserializer<User> {
        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            User u = new User();
            u.setUser(json.getAsString());
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

    private class WaterPurityConditionDeserializer implements JsonSerializer<WaterPurityCondition>, JsonDeserializer<WaterPurityCondition> {
        @Override
        public JsonElement serialize(WaterPurityCondition src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Arrays.asList(WaterPurityCondition.values()).indexOf(src));
        }

        @Override
        public WaterPurityCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterPurityCondition.values()[json.getAsInt()];
        }
    }

    private class AccountTypeDeserializer implements JsonSerializer<AccountType> {
        @Override
        public JsonElement serialize(AccountType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString() + "s");
        }
    }
}
