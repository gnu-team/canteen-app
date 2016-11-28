package org.canteen_water.model;

import com.google.gson.annotations.SerializedName;

/**
 * Defines a user account, which can view water sources and leave reports.
 */
public class User {
    private String username;
    private String password;
    private AccountType group;
    // Profile attributes
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    private String address;
    private String bio;
    @SerializedName("phone")
    private String phoneNumber;
    private boolean isblock = false;

    /**
     * Creates user object with null fields.
     * (Called by Gson, which uses reflection to populate fields)
     */
    public User(){
    }

    /**
     * Creates user object with given username and password
     * @param user the username
     * @param password the password
     * @param type account type
     */
    public User(String user, String password, AccountType type) {
        this.username = user;
        this.password = password;
        this.group = type;
    }

    /**
     * Gets the username of User account
     * @return the username
     */
    public String getUser() {
        return username;
    }

    /**
     * Sets the username of this account.
     * @param username new username
     */
    public void setUser(String username) {
        this.username = username;
    }

    /**
     * Gets the password of this account
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of this account
     * @return the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the login information is correct or not
     * @param user the username that has been input
     * @param password the password that has been input
     * @return if the login is correct or not
     */
    public boolean authenticate(String user, String password) {
        return this.username.equals(user) && this.password.equals(password);
    }

    /**
     * Reports on water availability
     * @param report the report of water availability
     */
    public void reportWaterAvail(String report){

    }

    /**
     * Gets the firstName of User
     * @return the firstName of user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the firstName of user
     * @param firstName the firstName of user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of this user
     * @return the last name of this user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this user
     * @param lastName new last name of this user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Determines whether user can create/list purity reports.
     * @return true if they can, false if they cannot.
     */
    public boolean canUsePurityReports() {
        return group != null && group.canUsePurityReports();
    }

    /**
     * Determines whether user can view the history report for a water source.
     * @return true if they can, false if they cannot.
     */
    public boolean canViewHistoryReports() {
        return group != null && group.canViewHistoryReports();
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
