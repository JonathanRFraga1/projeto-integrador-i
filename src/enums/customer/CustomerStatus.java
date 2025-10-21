package enums.customer;

public enum CustomerStatus {
    INACTIVE(0),
    ACTIVE(1),
    BLOCKED(2);

    private final int code;

    CustomerStatus(int code) {
        this.code = code;
    }
}
