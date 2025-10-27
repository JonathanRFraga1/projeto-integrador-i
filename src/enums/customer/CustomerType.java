package enums.customer;

public enum CustomerType {
    CUSTOMER_PHYSICAL(1),
    CUSTOMER_LEGAL(2);

    private int code;

    CustomerType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CustomerType fromCode(int code) {
        for (CustomerType status : CustomerType.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para CustomerType: " + code);
    }
}