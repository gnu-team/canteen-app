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
import model.exception.DataException;
import model.DataErrorReceiver;
import model.DataSuccessReceiver;
import model.DataReceiver;
import model.AccountType;
import model.DataSource;
import model.PurityReport;
import model.Report;
import model.User;
import model.WaterCondition;
import model.WaterPurityCondition;
import model.WaterType;
import model.Year;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApiDataSource implements DataSource {
    private Executor executor;
    private GsonBuilder gsonBuilder;
    private String user, password;

    public ApiDataSource() {
        executor = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r);
            // Don't let hung threads prevent the application from shutting down
            t.setDaemon(true);
            return t;
        });

        // Register (de)serializers
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(User.class, new UserDeserializer());
        gsonBuilder.registerTypeAdapter(WaterType.class, new WaterTypeDeserializer());
        gsonBuilder.registerTypeAdapter(WaterCondition.class, new WaterConditionDeserializer());
        gsonBuilder.registerTypeAdapter(WaterPurityCondition.class, new WaterPurityConditionDeserializer());
        gsonBuilder.registerTypeAdapter(AccountType.class, new AccountTypeDeserializer());
    }

    @Override
    public void authenticate(String user, String password, DataReceiver<User> onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiTask<User>(onSuccess, onFail, () -> {
            ApiConnection conn = new ApiConnection("GET", "/users/me/", HttpURLConnection.HTTP_OK, user, password);
            Reader response = conn.getResponseReader();

            Gson gson = gsonBuilder.create();
            User userData = gson.fromJson(response, User.class);

            // TODO: Fix race condition
            // Store credentials only if login succeeded
            this.user = user;
            this.password = password;

            // For now, always return null.
            return userData;
        }));
    }

    @Override
    public void addUser(User userdata, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiVoidTask(onSuccess, onFail, () -> {
            // If we've already authenticated, don't use the stored
            // username/password pair when registering
            ApiConnection<ApiUserError> conn = new ApiConnection<>("POST", "/users/", HttpURLConnection.HTTP_CREATED, ApiUserError.class, null, null);
            Writer request = conn.getRequestWriter();

            Gson gson = gsonBuilder.create();
            gson.toJson(userdata, request);

            conn.closeRequest(request);
            conn.connect();

            // XXX Fix race condition
            // XXX Test new account instead of blindly trusting it worked
            this.user = userdata.getUser();
            this.password = userdata.getPassword();

            // Clear out password. Now that we've used it to register
            // successfully, we've stored it here in ApiDataSource, and (1) we
            // don't want it hanging around, and (2) we don't want to change the
            // password every time we call updateUser(userdata) in the future
            userdata.setPassword(null);
        }));
    }

    @Override
    public void updateUser(User userdata, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiVoidTask(onSuccess, onFail, () -> {
            // Use PATCH rather than PUT so we can exclude unchanged
            // fields. (Currently, this is just password because we don't
            // offer a way to change it in the client yet.)
            ApiConnection<ApiUserError> conn = new ApiConnection<>("PATCH", "/users/me/", HttpURLConnection.HTTP_OK, ApiUserError.class, user, password);
            Writer request = conn.getRequestWriter();

            Gson gson = gsonBuilder.create();
            gson.toJson(userdata, request);

            conn.closeRequest(request);
            conn.connect();
        }));
    }

    @Override
    public void addReport(Report report, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiVoidTask(onSuccess, onFail, () -> {
            ApiConnection<ApiReportError> conn = new ApiConnection<>("POST", "/reports/", HttpURLConnection.HTTP_CREATED, ApiReportError.class, user, password);
            Writer request = conn.getRequestWriter();

            Gson gson = gsonBuilder.create();
            gson.toJson(report, request);

            conn.closeRequest(request);
            conn.connect();
        }));
    }

    @Override
    public void addPurityReport(PurityReport purityReport, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiVoidTask(onSuccess, onFail, () -> {
            ApiConnection<ApiPurityReportError> conn = new ApiConnection<>("POST", "/purity_reports/", HttpURLConnection.HTTP_CREATED, ApiPurityReportError.class, user, password);
            Writer request = conn.getRequestWriter();

            Gson gson = gsonBuilder.create();
            gson.toJson(purityReport, request);

            conn.closeRequest(request);
            conn.connect();
        }));
    }

    @Override
    public void listReports(DataReceiver<Collection<Report>> onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiTask<Collection<Report>>(onSuccess, onFail, () -> {
            ApiConnection conn = new ApiConnection("GET", "/reports/", HttpURLConnection.HTTP_OK, user, password);
            Reader response = conn.getResponseReader();

            Gson gson = gsonBuilder.create();
            Report[] reports = gson.fromJson(response, Report[].class);

            return Arrays.asList(reports);
        }));
    }

    @Override
    public void listPurityReports(DataReceiver<Collection<PurityReport>> onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiTask<Collection<PurityReport>>(onSuccess, onFail, () -> {
            ApiConnection conn = new ApiConnection("GET", "/purity_reports/", HttpURLConnection.HTTP_OK, user, password);
            Reader response = conn.getResponseReader();

            Gson gson = gsonBuilder.create();
            PurityReport[] reports = gson.fromJson(response, PurityReport[].class);

            return Arrays.asList(reports);
        }));
    }

    @Override
    public void listNearbyPurityReports(Year year, PurityReport report, DataReceiver<Collection<PurityReport>> onSuccess, DataErrorReceiver onFail) {
        executor.execute(new ApiTask<Collection<PurityReport>>(onSuccess, onFail, () -> {
            String path = String.format("/purity_reports/near/%f,%f/?startDate=%s-01-01&endDate=%s-12-31",
                                        report.getLatitude(), report.getLongitude(), year, year);
            ApiConnection conn = new ApiConnection("GET", path, HttpURLConnection.HTTP_OK, user, password);
            Reader response = conn.getResponseReader();

            Gson gson = gsonBuilder.create();
            PurityReport[] reports = gson.fromJson(response, PurityReport[].class);

            return Arrays.asList(reports);
        }));
    }

    private static class FullUser extends User {}
    private static class UserDeserializer implements JsonDeserializer<User> {
        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            // Full user object (e.g., {"username": "austin", "first_name": "Austin", ... })
            if (json.isJsonObject()) {
                return context.deserialize(json, FullUser.class);
            // Username string (e.g., "austin") to wrap in a User object
            } else {
                User u = new User();
                u.setUser(json.getAsString());
                return u;
            }
        }
    }

    private static class WaterTypeDeserializer implements JsonSerializer<WaterType>, JsonDeserializer<WaterType> {
        @Override
        public JsonElement serialize(WaterType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Arrays.asList(WaterType.values()).indexOf(src));
        }

        @Override
        public WaterType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterType.values()[json.getAsInt()];
        }
    }

    private static class WaterConditionDeserializer implements JsonSerializer<WaterCondition>, JsonDeserializer<WaterCondition> {
        @Override
        public JsonElement serialize(WaterCondition src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Arrays.asList(WaterCondition.values()).indexOf(src));
        }

        @Override
        public WaterCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterCondition.values()[json.getAsInt()];
        }
    }

    private static class WaterPurityConditionDeserializer implements JsonSerializer<WaterPurityCondition>, JsonDeserializer<WaterPurityCondition> {
        @Override
        public JsonElement serialize(WaterPurityCondition src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Arrays.asList(WaterPurityCondition.values()).indexOf(src));
        }

        @Override
        public WaterPurityCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return WaterPurityCondition.values()[json.getAsInt()];
        }
    }

    private static class AccountTypeDeserializer implements JsonSerializer<AccountType>, JsonDeserializer<AccountType> {
        @Override
        public JsonElement serialize(AccountType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString() + "s");
        }

        @Override
        public AccountType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String pluralGroupName = json.getAsString();
            String singularGroupName = pluralGroupName.substring(0, pluralGroupName.length() - 1);
            AccountType at = AccountType.getByName(singularGroupName);

            if (at == null) {
                throw new JsonParseException("Unknown group name " + singularGroupName);
            } else {
                return at;
            }
        }
    }
}
