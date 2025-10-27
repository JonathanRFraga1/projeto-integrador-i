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

    public int getCode() {
        return code;
    }

    public static CustomerPhysicalGender fromCode(int code) {
        for (CustomerPhysicalGender status : CustomerPhysicalGender.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para CustomerPhysicalGender: " + code);
    }
}
