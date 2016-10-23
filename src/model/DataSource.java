package model;

import java.util.Collection;

import exception.DataBackendException;
import exception.InvalidUserException;
import exception.NoSuchUserException;

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
    User authenticate(String user, String password) throws DataBackendException, NoSuchUserException;

    /**
     * Adds new account
     * @param userdata User Object (information with new ID and Password)
     */
    void addUser(User userdata) throws DataBackendException, InvalidUserException;

    /**
     * Change attributes (e.g., profile) of current user account
     * @param userdata User Object (information with changed
     *                 ID/Password/Profile)
     */
    void updateUser(User userdata) throws DataBackendException;

    /**
     * Adds new report
     * @param report New report to add
     */
    void addReport(Report report) throws DataBackendException;

    /**
     * Adds new purity report
     * @param purityReport New purity report to add
     * @throws DataBackendException for unexpected backend failures
     */
    void addPurityReport(PurityReport purityReport) throws DataBackendException;

    /**
     * Returns all reports in the system
     * @return a Collection containing all known Reports
     * @throws DataBackendException for unexpected backend failures
     */
    Collection<Report> listReports() throws DataBackendException;

    /**
     * Returns all purity reports in the system
     * @return a Collection containing all known PurityReports
     * @throws DataBackendException for unexpected backend failures
     */
    Collection<PurityReport> listPurityReports() throws DataBackendException;
}

