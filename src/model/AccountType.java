package model;

public enum AccountType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Administrator");

    private String name;

    AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
