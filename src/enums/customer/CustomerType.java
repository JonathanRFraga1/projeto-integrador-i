package enums.customer;

public enum CustomerType {
    CUSTOMER_PHYSICAL(1),
    CUSTOMER_LEGAL(2);

    private int code;

    CustomerType(int code) {
        this.code = code;
    }
}