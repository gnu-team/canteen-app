package org.canteen_water.model.exception;

/**
 * Represents an unexpected error encountered by a class implementing
 * DataSource.
 */
public class DataException extends Exception {
    /**
     * Creates a DataSource backend exception.
     *
     * @param message The error message shown to the user.
     */
    public DataException(String message) {
        super(message);
    }

    /**
     * Creates a DataSource backend exception with an inner exception.
     *
     * @param message The error message shown to the user
     * @param cause The inner exception
     */
    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
