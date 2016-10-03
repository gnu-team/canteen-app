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
    USER("User"),
    /**
     * Can report on water purity levels.
     */
    WORKER("Worker"),
    /**
     * Can view historical reports and trends of water purity and delete
     * individual reports that they deem inaccurate.
     */
    MANAGER("Manager"),
    /**
     * Can delete any accounts, ban a user from submitting reports, unblock an
     * account that has been locked for incorrect login attempts, and view the
     * security log.
     */
    ADMIN("Administrator");

    /**
     * Account type chosen by default in registration screen.
     */
    public static AccountType DEFAULT = USER;
    private String name;

    AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
