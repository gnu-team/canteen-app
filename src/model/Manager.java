package model;

/**
 * Manager account: can view, delete reports
 */
public class Manager extends Worker {
    /**
     * create manager object with default username and password
     */
    public Manager(){
        this("username", "Password");
    }

    /**
     * create manager object with username and password
     * @param user the username to be created
     * @param password the password for new account
     */

    public Manager(String user, String password) {
        super(user, password);
    }

    /**
     * view reports
     * @return the report to be viewed
     */
    public String viewReports(){
        return null;
    }

    /**
     * To view the trends of water Purity
     * @return the trend of water purity
     */
    public String viewTrendOfPurity(){
        return null;
    }

    /**
     * Delete individual report
     */
    public void deleteReport(){

    }
}
