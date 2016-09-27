package model;

/**
 * Created by XinZhang on 9/27/16.
 */
public class Administrator {
    private String username;
    private String password;
    public Administrator() {
        this("user","password");
    }
    public Administrator(String user, String password) {
        this.username = user;
        this.password = password;
    }
    public boolean authenticate(String user, String password) {
        return this.username.equals(user) && this.password.equals(password);
    }
    public void delete(User u) {

    }
    public void banUser(User u) {
        u.block();
    }
    public void unblock(User u) {
        u.unblock();
    }
}
