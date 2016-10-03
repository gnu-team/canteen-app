package model;

/**
 * Defines a worker account, which can report on purity levels.
 */
public class Worker extends User {
    public Worker(){
        this("user","password");
    }

    public Worker(String user, String password) {
        super(user,password);
    }

    public void reportOnPurity(String report){

    }
}
