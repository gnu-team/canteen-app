package model;

/**
 * Enumeration storing different types of users.
 *
 * Higher privilege levels inherit privileges of lower privilege levels. For
 * instance, a manager can submit reports on water availability.
 */
public enum AccountType {
    /**
     * Can submit a report on water availability and view available water
     * sources.
     */
    USER("User", false, false),
    /**
     * Can report on water purity levels.
     */
    WORKER("Worker", true, false),
    /**
     * Can view historical reports and trends of water purity and delete
     * individual reports that they deem inaccurate.
     */
    MANAGER("Manager", true, true),
    /**
     * Can delete any accounts, ban a user from submitting reports, unblock an
     * account that has been locked for incorrect login attempts, and view the
     * security log.
     */
    ADMIN("Administrator", true, true);

    /**
     * Account type chosen by default in registration screen.
     */
    public static final AccountType DEFAULT = USER;
    private String name;
    // Permissions
    private boolean permUsePurityReports;
    private boolean permViewHistoryReports;

    /**
     * Creates an account with the given name.
     *
     * @param name label of this account
     */
    AccountType(String name, boolean permUsePurityReports, boolean permViewHistoryReports) {
        this.name = name;
        this.permUsePurityReports = permUsePurityReports;
        this.permViewHistoryReports = permViewHistoryReports;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean canUsePurityReports() {
        return permUsePurityReports;
    }

    public boolean canViewHistoryReports() {
        return permViewHistoryReports;
    }

    public static AccountType getByName(String name) {
        for (AccountType at : AccountType.values()) {
            if (at.toString().equals(name)) {
                return at;
            }
        }

        // TODO: Throw an exception instead
        return null;
    }
}
