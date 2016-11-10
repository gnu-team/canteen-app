package org.canteen_water.model;

import java.util.Collection;

import org.canteen_water.model.exception.DataException;

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
    void authenticate(String user, String password, DataReceiver<User> onSuccess, DataErrorReceiver onFail);

    /**
     * Adds new account
     * @param userdata User Object (information with new ID and Password)
     */
    void addUser(User userdata, DataSuccessReceiver onSuccess, DataErrorReceiver onFail);

    /**
     * Change attributes (e.g., profile) of current user account
     * @param userdata User Object (information with changed
     *                 ID/Password/Profile)
     */
    void updateUser(User userdata, DataSuccessReceiver onSuccess, DataErrorReceiver onFail);

    /**
     * Adds new report
     * @param report New report to add
     */
    void addReport(Report report, DataSuccessReceiver onSuccess, DataErrorReceiver onFail);

    /**
     * Adds new purity report
     * @param purityReport New purity report to add
     * @throws DataException for unexpected backend failures
     */
    void addPurityReport(PurityReport purityReport, DataSuccessReceiver onSuccess, DataErrorReceiver onFail);

    /**
     * Returns all reports in the system
     * @return a Collection containing all known Reports
     * @throws DataException for unexpected backend failures
     */
    void listReports(DataReceiver<Collection<Report>> onSuccess, DataErrorReceiver onFail);

    /**
     * Returns all purity reports in the system
     * @return a Collection containing all known PurityReports
     * @throws DataException for unexpected backend failures
     */
    void listPurityReports(DataReceiver<Collection<PurityReport>> onSuccess, DataErrorReceiver onFail);

    /**
     * Finds all purity reports near the given latitude and longitude.
     */
    void listNearbyPurityReports(Year year, PurityReport report, DataReceiver<Collection<PurityReport>> onSuccess, DataErrorReceiver onFail);
}

