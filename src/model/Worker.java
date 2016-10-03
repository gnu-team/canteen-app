package model;

/**
 * Defines a worker account, which can report on purity levels.
 */
public class Worker extends User {
    /**
     * To create Worker Object with Default information
     */
    public Worker(){
        this("user","password");
    }

    /**
     * To Create Worker Object with username and password
     * @param user The username of User
     * @param password The password of Account
     */

    public Worker(String user, String password) {
        super(user,password);
    }

    /**
     * To report on water purity
     * @param report the report for water purity
     */

    public void reportOnPurity(String report){

    }
}
