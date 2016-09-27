package model;

/**
 * Created by XinZhang on 9/27/16.
 */
public class Worker extends User{
    public Worker(){
        this("user","password");
    }

    public Worker(String user, String password) {
        super(user,password);
    }

    public void reportOnPurity(String report){

    }
}
