package exception;

/**
 * Represents an unexpected error encountered by a class implementing
 * DataSource.
 */
public class DataBackendException extends Exception {
    /**
     * Creates a DataSource backend exception.
     *
     * @param message The error message shown to the user.
     */
    public DataBackendException(String message) {
        super(message);
    }

    /**
     * Creates a DataSource backend exception with an inner exception.
     *
     * @param message The error message shown to the user
     * @param cause The inner exception
     */
    public DataBackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
