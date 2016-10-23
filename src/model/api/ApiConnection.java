package model.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.exception.DataException;

import javax.xml.bind.DatatypeConverter;

public class ApiConnection<T extends ApiError> {
    private static final String API_ENDPOINT = "https://canteen.austinjadams.com";
    private final GsonBuilder gsonBuilder;
    private final int statusWanted;
    private final Class<T> errType;
    private final String method, path;
    private final HttpURLConnection conn;

    // TODO: Redesign this class so we don't need this
    @SuppressWarnings("unchecked")
    public ApiConnection(String method, String path, int statusWanted, String user, String password) throws DataException {
        this(method, path, statusWanted, (Class<T>) ApiError.class, user, password);
    }

    public ApiConnection(String method, String path, int statusWanted, Class<T> errType, String user, String password) throws DataException {
        this.statusWanted = statusWanted;
        this.errType = errType;
        this.method = method;
        this.path = path;
        this.conn = makeConn(method, path, user, password);
        this.gsonBuilder = new GsonBuilder();
    }

    /**
     * Initiates connection and checks status code
     */
    public void connect() throws DataException {
        int statusCode;
        try {
            statusCode = conn.getResponseCode();
        } catch (IOException e) {
            throw new DataException("Could not make " + method + " request to " + path, e);
        }

        if (statusCode != statusWanted) {
            String err = parseError();

            if (err == null) {
                throw new DataException("Unexpected response code "
                    + statusCode + " but no error sent.");
            } else {
                throw new DataException(err);
            }
        }
    }

    private HttpURLConnection makeConn(String method, String path, String user, String password) throws DataException {
        URL url;

        try {
            url = new URL(API_ENDPOINT + path);
        } catch (MalformedURLException e) {
            throw new DataException("Malformed URL", e);
        }

        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new DataException("Opening request connection", e);
        }

        // If it's a non-GET (e.g., POST), we need to send a request body and
        // possibly work around an Oracleism in the design of HttpURLConnection
        if (!method.equals("GET")) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Work around https://bugs.openjdk.java.net/browse/JDK-7016595
            // (Java's puzzling, intentional inability to make PATCH requests)
            // by setting the X-HTTP-Method-Override header to the desired
            // method but sending a POST.
            if (!method.equals("POST")) {
                conn.setRequestProperty("X-HTTP-Method-Override", method);
                method = "POST";
            }
        }

        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new DataException("Bad protocol", e);
        }

        // To get a JSON response, set the Accept header
        conn.setRequestProperty("Accept", "application/json");

        if (user != null && password != null) {
            // Set the Authorization header
            // http://stackoverflow.com/a/12603622/321301
            String userpass = user + ":" + password;
            String authblob = DatatypeConverter.printBase64Binary(userpass.getBytes());
            conn.setRequestProperty("Authorization", "Basic " + authblob);
        }

        return conn;
    }

    private String parseError() {
        String result = null;
        InputStream response = conn.getErrorStream();

        if (response != null) {
            Gson gson = gsonBuilder.create();
            ApiError err = (ApiError) gson.fromJson(new InputStreamReader(response), errType);

            result = err.getDetail();
        }

        return result;
    }

    public void closeRequest(Writer writer) throws DataException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new DataException("Flushing request body", e);
        }
    }

    public Reader getResponseReader() throws DataException {
        // Check the response code before we try to read
        connect();

        InputStream request;
        try {
            request = conn.getInputStream();
        } catch (IOException e) {
            throw new DataException("Could not getInputStream()", e);
        }

        return new InputStreamReader(request);
    }

    public Writer getRequestWriter() throws DataException {
        OutputStream request;
        try {
            request = conn.getOutputStream();
        } catch (IOException e) {
            throw new DataException("Could not getOutputStream()", e);
        }

        return new OutputStreamWriter(request);
    }
}
