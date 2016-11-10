package org.canteen_water.model.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.SSLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.canteen_water.model.exception.DataException;

import javax.xml.bind.DatatypeConverter;

public class ApiConnection<T extends ApiError> {
    private static final String API_ENDPOINT = "https://canteen-water.org/api";
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
        } catch (SSLException e) {
            // If there might have been a certificate verification
            // failure, encourage users to upgrade Java.
            //
            // (OpenJDK seems to throw SSLProtocolException, while the
            //  Oracle JRE throws SSLHandshakeException, so catch
            //  SSLException, a common superclass.)
            throw new DataException(String.format("SSL error making %s request"
                + " to %s: %s\n\nPlease try upgrading to Java 8u101 or later.",
                method, path, e.getMessage()), e);
        } catch (IOException e) {
            throw new DataException("Could not make " + method + " request to " + path, e);
        }

        if (statusCode != statusWanted) {
            determineError(statusCode);
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

    // Throw a DataException best representing the error encountered
    private void determineError(int statusCode) throws DataException {
        String errDetail = null;
        String rawResponse = null;
        InputStream response = conn.getErrorStream();

        if (response != null) {
            Gson gson = gsonBuilder.create();

            try {
                rawResponse = IOUtils.toString(response, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new DataException("Reading error response", e);
            }

            ApiError err = (ApiError) gson.fromJson(rawResponse, errType);

            errDetail = err.getDetail();
        }

        // Parsing error messages with ApiError often causes issues (for
        // instance, when a new required field is added of which the specific
        // Api*Error is unaware), so go ahead and log the full response body
        // for debugging purposes.
        if (rawResponse != null) {
            // XXX Use logging somehow instead of writing directly to stderr
            System.err.println(String.format("Bad response (%d): %s",
                                             statusCode, rawResponse));
        }

        if (errDetail == null) {
            throw new DataException("Unexpected response code " + statusCode
                                    + " but no error response sent.");
        } else {
            throw new DataException(errDetail);
        }
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
