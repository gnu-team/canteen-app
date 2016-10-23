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
    public User authenticate(String user, String password) throws DataException {
        for (User s: users) {
            if (s.authenticate(user, password)) {
                return s;
            }
        }
        throw new DataException("No matched ID or Password");
    }

    @Override
    public void addUser(User userdata) throws DataException {
        for (User userOb : users) {
            if (userdata.getUser().equals(userOb.getUser())) {
                throw new DataException("Invalid ID");
            }
        }
        users.add(userdata);
    }

    @Override
    public void updateUser(User userdata) throws DataException {
        throw new DataException("Not implemented");
    }

    @Override
    public void addReport(Report report) {
        report.setID(reports.size());
        reports.add(report);
    }

    @Override
    public void addPurityReport(PurityReport purityReport) throws DataException {
        throw new DataException("Not implemented");
    }

    @Override
    public Collection<Report> listReports() {
        return reports;
    }

    @Override
    public Collection<PurityReport> listPurityReports() throws DataException {
        throw new DataException("Not implemented");
    }
}

