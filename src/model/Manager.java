package model;

/**
 * Created by XinZhang on 9/27/16.
 */
public class Manager extends Worker{
    public Manager(){
        this("username", "Password");
    }
    public Manager(String user, String password) {
        super(user, password);
    }

    public String viewReports(){
        return null;
    }
    public String viewTrendOfPurity(){
        return null;
    }
    public void deleteReport(){

    }

}
