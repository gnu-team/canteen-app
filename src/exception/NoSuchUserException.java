package exception;

/**
 * Represents a login error.
 */
public class NoSuchUserException extends Exception {
    /**
     * Creates a login error.
     *
     * @param message The error message shown to the user.
     */
    public NoSuchUserException(String message) {
        super(message);
    }
}
