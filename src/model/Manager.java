package model;

/**
 * Manager account: can view, delete reports
 */
public class Manager extends Worker {
    /**
     * Creates manager object with default username and password
     */
    public Manager(){
        this("username", "Password", AccountType.MANAGER);
    }

    /**
     * Creates manager object with username and password
     * @param user the username to be created
     * @param password the password for new account
     * @param type account type
     */

    public Manager(String user, String password, AccountType type) {
        super(user, password, type);
    }

    /**
     * Views reports
     * @return the report to be viewed
     */
    public String viewReports(){
        return null;
    }

    /**
     * Views the trends of water Purity
     * @return the trend of water purity
     */
    public String viewTrendOfPurity(){
        return null;
    }

    /**
     * Deletes individual report
     */
    public void deleteReport(){

    }
}
