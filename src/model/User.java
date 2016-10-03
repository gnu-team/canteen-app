package model;

/**
 * Defines a user account, which can view water sources and leave reports.
 */
public class User {
    private String user;
    private String password;
    // Profile attributes
    private String name;
    private String email;
    private String address;
    private String bio;
    private String phoneNumber;
    private boolean isblock = false;

    /**
     * Create user object
     */

    public User(){
        this("user","pass");
    }

    /**
     * Create user object with given username and password
     * @param user the username
     * @param password the password
     */
    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * To get the username of User account
     * @return the username
     */

    public String getUser() {
        return user;
    }

    /**
     * The check if the login information is correct or not
     * @param user the username that has been input
     * @param password the password that has been input
     * @return if the login is correct or not
     */
    public boolean authenticate(String user, String password) {
        return this.user.equals(user) && this.password.equals(password);
    }

    /**
     * To report on water availability
     * @param report the report of water availability
     */
    public void reportWaterAvail(String report){

    }

    /**
     * To get the name of User
     * @return the name of user
     */

    public String getName() {
        return name;
    }

    /**
     * To set the name of user
     * @param name the name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To set the email of user
     * @param email the email of user
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * To set the address of user
     * @param address the address of user
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * the set the Bio of user
     * @param bio the Bio of user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * To set the phone number of user
     * @param phoneNumber the phone number of user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * To get the email of user
     * @return the email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * To get the address of User
     * @return the address of User
     */
    public String getAddress() {
        return address;
    }

    /**
     * To get the Bio of User
     * @return the Bio of User
     */
    public String getBio() {
        return bio;
    }

    /**
     * To get the phone number of User
     * @return the phone number of user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * to get the water sources information
     * @return the water sources information
     */
    public String viewWaterSources(){
        return null;
    }

    /**
     * To block the user
     */

    public void block() {
        this.isblock = true;
    }

    /**
     * To unblock the user
     */
    public void unblock(){
        this.isblock = false;
    }

    /**
     * To check if the user is blocked
     * @return if the user is blocked
     */
    public boolean ifblock(){
        return this.isblock;
    }
}
