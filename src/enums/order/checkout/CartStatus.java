package enums.order.checkout;

public enum CartStatus  {
    ACTIVE(1),
    ABANDONED(2),
    CLOSED(3);

    private final int code;

    CartStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CartStatus fromCode(int code) {
        for (CartStatus status : CartStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para CartStatus: " + code);
    }
}