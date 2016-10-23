package model;

import java.util.Collection;

import model.exception.DataException;

/**
 * Maintains system state, either by storing state in memory or by
 * querying a service.
 */
public interface DataSource {
    /**
     * Checks if there is the account or not in the list which we already have
     * @param user to check user ID which entered by an user
     * @param password to check user Password which entered by an user
     * @return if there is the account, return the User Object included user and password
     */
    User authenticate(String user, String password) throws DataException;

    /**
     * Adds new account
     * @param userdata User Object (information with new ID and Password)
     */
    void addUser(User userdata) throws DataException;

    /**
     * Change attributes (e.g., profile) of current user account
     * @param userdata User Object (information with changed
     *                 ID/Password/Profile)
     */
    void updateUser(User userdata) throws DataException;

    /**
     * Adds new report
     * @param report New report to add
     */
    void addReport(Report report) throws DataException;

    /**
     * Adds new purity report
     * @param purityReport New purity report to add
     * @throws DataException for unexpected backend failures
     */
    void addPurityReport(PurityReport purityReport) throws DataException;

    /**
     * Returns all reports in the system
     * @return a Collection containing all known Reports
     * @throws DataException for unexpected backend failures
     */
    Collection<Report> listReports() throws DataException;

    /**
     * Returns all purity reports in the system
     * @return a Collection containing all known PurityReports
     * @throws DataException for unexpected backend failures
     */
    Collection<PurityReport> listPurityReports() throws DataException;
}

