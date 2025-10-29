package enums.customer;

public enum CustomerStatus {
    INACTIVE(0),
    ACTIVE(1),
    BLOCKED(2);

    private final int code;

    CustomerStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CustomerStatus fromCode(int code) {
        for (CustomerStatus status : CustomerStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para CustomerStatus: " + code);
    }
}
