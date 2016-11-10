package org.canteen_water.model;

/**
 * Defines a worker account, which can report on purity levels.
 */
public class Worker extends User {
    /**
     * Creates worker object with default information
     */
    public Worker(){
        this("user", "password", AccountType.WORKER);
    }

    /**
     * To Create Worker Object with username and password
     * @param user The username of User
     * @param password The password of Account
     * @param type account type
     */
    public Worker(String user, String password, AccountType type) {
        super(user, password, type);
    }

    /**
     * Reports on water purity
     * @param report the report for water purity
     */
    public void reportOnPurity(String report){

    }
}
