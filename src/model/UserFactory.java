package model;

import java.util.regex.Pattern;

import exception.InvalidUserException;

public class UserFactory {
    private static final int USER_MIN = 4;
    private static final int USER_MAX = 64;
    private static final int PASS_MIN = 8;
    private static final int PASS_MAX = 2048;
    private static final Pattern USER_PATTERN = Pattern.compile("[a-zA-Z0-9._-]+");

    public static User createUser(String username, String password,
                                  AccountType type) throws
            InvalidUserException {
        username = username.trim();
        password = password.trim();

        validate(username, password);

        User u;
        // TODO: This is horrible. Find an alternative, e.g., somehow
        //       tying each instance of AccountType to the corresponding
        //       class (???)
        switch (type) {
            case USER:
                u = new User(username, password);
                break;
            case WORKER:
                u = new Worker(username, password);
                break;
            case MANAGER:
                u = new Manager(username, password);
                break;
            case ADMIN:
                u = new Administrator(username, password);
                break;
            default:
                throw new UnsupportedOperationException("Creating a " + type
                    + " User is not implemented yet");
        }

        return u;
    }

    private static void inRange(String desc, String victim, int min, int max)
                        throws InvalidUserException {
        if (victim.length() < min) {
            throw new InvalidUserException(desc + " must be at least " + min
                                           + " characters");
        } else if (victim.length() > max) {
            throw new InvalidUserException(desc + " must be less than " + max
                                           + " characters");
        }
    }

    private static void validate(String username, String password)
                        throws InvalidUserException {
        inRange("Username", username, USER_MIN, USER_MAX);
        inRange("Password", password, PASS_MIN, PASS_MAX);

        if (!USER_PATTERN.matcher(username).matches()) {
            throw new InvalidUserException("Username must consist of "
                                           + "alphanumeric characters plus ., "
                                           + "_, and -.");
        }
    }
}
