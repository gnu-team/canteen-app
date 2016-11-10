package org.canteen_water.model;

/**
 * System administrator account
 */
public class Administrator extends Manager {
    /**
     * Creates an Administrator class
     */
    public Administrator() {
        this("user", "password", AccountType.ADMIN);
    }

    /**
     * Creates an Administrator class
     * @param user the username of the administrator
     * @param password the password of the administrator
     */
    public Administrator(String user, String password, AccountType type) {
        super(user, password, type);
    }

    /**
     * Deletes the other user account
     * @param u the user to be deleted
     */
    public void delete(User u) {

    }

    /**
     * Bans other user from submitting reports
     * @param u the user to be baned
     */
    public void banUser(User u)
    {
        u.block();
    }

    /**
     * Unblocks blocked account
     * @param u user that need to unblock account
     */
    public void unblock(User u) {
        u.unblock();
    }
}
