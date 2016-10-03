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
     * Creates user object
     */
    public User(){
        this("user","pass");
    }

    /**
     * Creates user object with given username and password
     * @param user the username
     * @param password the password
     */
    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * Gets the username of User account
     * @return the username
     */

    public String getUser() {
        return user;
    }

    /**
     * Checks if the login information is correct or not
     * @param user the username that has been input
     * @param password the password that has been input
     * @return if the login is correct or not
     */
    public boolean authenticate(String user, String password) {
        return this.user.equals(user) && this.password.equals(password);
    }

    /**
     * Reports on water availability
     * @param report the report of water availability
     */
    public void reportWaterAvail(String report){

    }

    /**
     * Gets the name of User
     * @return the name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of user
     * @param name the name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email of user
     * @param email the email of user
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the address of user
     * @param address the address of user
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Sets the Bio of user
     * @param bio the Bio of user
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Sets the phone number of user
     * @param phoneNumber the phone number of user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email of user
     * @return the email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the address of User
     * @return the address of User
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the Bio of User
     * @return the Bio of User
     */
    public String getBio() {
        return bio;
    }

    /**
     * Gets the phone number of User
     * @return the phone number of user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the water sources information
     * @return the water sources information
     */
    public String viewWaterSources(){
        return null;
    }

    /**
     * Block the user
     */
    public void block() {
        this.isblock = true;
    }

    /**
     * Unblocks the user
     */
    public void unblock(){
        this.isblock = false;
    }

    /**
     * Checks if the user is blocked
     * @return if the user is blocked
     */
    public boolean ifblock(){
        return this.isblock;
    }
}
