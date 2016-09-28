package model;

import java.util.regex.Pattern;
import model.User;
import model.Worker;
import model.Manager;
import model.Administrator;
import model.AccountType;

public class UserFactory {
    public static User createUser(String username, String password,
                                  AccountType type) throws
                                  InvalidUserException {
        username = username.trim();
        password = password.trim();

        String reason = validate(username, password);
        if (reason != null) {
            throw new InvalidUserException(reason);
        }

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

    private static String validate(String username, String password) {
        if (username.length() < 4) {
            return "Username must be at least 4 characters";
        } else if (password.length() < 16) {
            return "Password must be at least 16 characters";
        } else if (!Pattern.matches("[a-zA-Z0-9._-]+", username)) {
            return "Username must consist of alphanumeric characters "
                 + "plus ., _, and -.";
        }

        return null;
    }
}
