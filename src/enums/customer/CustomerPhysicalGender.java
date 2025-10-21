package enums.customer;

public enum CustomerPhysicalGender {
    MASCULINE(1),
    FEMININE(2),
    OTHER(3),
    NOT_SPECIFIED(4);

    private final int code;

    CustomerPhysicalGender(int code) {
        this.code = code;
    }
}
