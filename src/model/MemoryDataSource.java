package model;

import model.exception.DataException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Stores an account and check if there is an account or not
 */
public class MemoryDataSource implements DataSource {
    private static MemoryDataSource instance = new MemoryDataSource();
    private Set<User> users;
    private Set<Report> reports;

    private MemoryDataSource() {
        users = new HashSet<>();
        reports = new HashSet<>();
    }

    /**
     * Gets the instance for this class
     * @return instance for this class
     */
    public static MemoryDataSource getInstance() {
        return instance;
    }

    @Override
    public void authenticate(String user, String password, DataReceiver<User> onSuccess, DataErrorReceiver onFail) {
        for (User s: users) {
            if (s.authenticate(user, password)) {
                onSuccess.onSuccess(s);
                return;
            }
        }
        onFail.onFail(new DataException("No matched ID or Password"));
    }

    @Override
    public void addUser(User userdata, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        for (User userOb : users) {
            if (userdata.getUser().equals(userOb.getUser())) {
                onFail.onFail(new DataException("Invalid ID"));
                return;
            }
        }
        users.add(userdata);
        onSuccess.onSuccess();
    }

    @Override
    public void updateUser(User userdata, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        onFail.onFail(new DataException("Not implemented"));
    }

    @Override
    public void addReport(Report report, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        report.setID(reports.size());
        reports.add(report);
        onSuccess.onSuccess();
    }

    @Override
    public void addPurityReport(PurityReport purityReport, DataSuccessReceiver onSuccess, DataErrorReceiver onFail) {
        onFail.onFail(new DataException("Not implemented"));
    }

    @Override
    public void listReports(DataReceiver<Collection<Report>> onSuccess, DataErrorReceiver onFail) {
        onSuccess.onSuccess(reports);
    }

    @Override
    public void listPurityReports(DataReceiver<Collection<PurityReport>> onSuccess, DataErrorReceiver onFail) {
        onFail.onFail(new DataException("Not implemented"));
    }
}

