package model;


import java.util.HashSet;
import java.util.Set;


/**
 * Stores an account and check if there is an account or not
 * Created by NikosD on 9/28/16.
 */
public class DataSource {
    private static DataSource instance;
    private Set<User> users;


    private DataSource() {
        instance = new DataSource();
        users = new HashSet<>();
    }

    /**
     * Gets the instance for this class
     * @return instance for this class
     */
    public static DataSource getInstance() {
        return instance;
    }

    /**
     * Checks if there is the account or not in the list which we already have
     * @param user to check user ID which entered by an user
     * @param password to check user Password which entered by an user
     * @return if there is the account, return the User Object included user and password
     */
    public User authenticate(String user, String password) {
        for (User s: users) {
            if (s.authenticate(user, password)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Adds new account
     * @param userdata User Object (information with new ID and Password)
     */
    public void add(User userdata) {
        users.add(userdata);
    }
}

