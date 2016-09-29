package model;

/**
 * Created by Austin Adams on 9/19/16.
 */
public class User {
    private String user;
    private String password;
    private String email;
    private String address;
    private String bio;
    private int phoneNumber;
    private boolean isblock = false;
    private static final User defaultUser = new User("user", "pass");

    public static User getDefaultUser() {
        return defaultUser;
    }
    public User(){
        this("user","pass");
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public boolean authenticate(String user, String password) {
        return this.user.equals(user) && this.password.equals(password);
    }
    public void reportWaterAvail(String report){

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBio() {
        return bio;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String viewWaterSources(){
        return null;
    }
    public void block() {
        this.isblock = true;
    }
    public void unblock(){
        this.isblock = false;
    }
    public boolean ifblock(){
        return this.isblock;
    }

}
