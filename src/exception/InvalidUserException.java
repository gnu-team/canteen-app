package exception;

/**
 * Represents a registration error.
 */
public class InvalidUserException extends Exception {
    /**
     * Creates a registration error.
     *
     * @param message The error message shown to the user.
     */
    public InvalidUserException(String message) {
        super(message);
    }
}
