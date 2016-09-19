package model;

/**
 * Created by Austin Adams on 9/19/16.
 */
public class User {
    private String user;
    private String password;

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public boolean authenticate(String user, String password) {
        return this.user.equals(user) && this.password.equals(password);
    }
}
