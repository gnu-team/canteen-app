package model;

/**
 * Created by Austin Adams on 9/19/16.
 */
public class User {
    private String user;
    private String password;

    private String name;
    private String email;
    private String address;
    private String bio;
    private String phoneNumber;
    private boolean isblock = false;

    public User(){
        this("user","pass");
    }

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public boolean authenticate(String user, String password) {
        return this.user.equals(user) && this.password.equals(password);
    }
    public void reportWaterAvail(String report){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPhoneNumber(String phoneNumber) {
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

    public String getPhoneNumber() {
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
