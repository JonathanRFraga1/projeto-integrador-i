package enums.order.checkout;

public enum CartStatus  {
    ACTIVE(1),
    ABANDONED(2),
    CLOSED(3);

    private final int code;

    CartStatus(int code) {
        this.code = code;
    }
}