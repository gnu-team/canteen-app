package org.canteen_water.model;


/**
 * Generates a User instance from a username, password, and AccountType.
 *
 * This hack exists because AccountType is not a field of User; instead,
 * Manager etc. are subclasses of User.
 */
public final class UserFactory {

    private UserFactory() {
        //default constructor
    }

    /**
     * Checks username and password for correctness and returns a User of the
     * given account type.
     *
     * @param username The username of new account.
     * @param password The password of new account.
     * @param type Account type to create.
     * @return A new User instance of the correct subclass of User.
     */
    public static User createUser(String username, String password,
                                  AccountType type) {
        username = username.trim();

        User u;
        // TODO: This is horrible. Find an alternative, e.g., somehow
        //       tying each instance of AccountType to the corresponding
        //       class (???)
        switch (type) {
            case USER:
                u = new User(username, password, type);
                break;
            case WORKER:
                u = new Worker(username, password, type);
                break;
            case MANAGER:
                u = new Manager(username, password, type);
                break;
            case ADMIN:
                u = new Administrator(username, password, type);
                break;
            default:
                throw new UnsupportedOperationException("Creating a " + type
                    + " User is not implemented yet");
        }

        return u;
    }
}
