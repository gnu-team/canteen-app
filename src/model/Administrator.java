package model;

/**
 * System administrator account
 */
public class Administrator extends Manager {
    private String username;
    private String password;

    /**
     * Creates an Administrator class
     */
    public Administrator() {
        this("user","password");
    }

    /**
     * Creates an Administrator class
     * @param user the username of the administrator
     * @param password the password of the administrator
     */
    public Administrator(String user, String password) {
        this.username = user;
        this.password = password;
    }

    /**
     * Checks the information for login as administrator
     * @param user the username that has been input
     * @param password the password that need to be checked
     * @return true if user and password match, else false
     */
    public boolean authenticate(String user, String password) {
        return this.username.equals(user) && this.password.equals(password);
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
