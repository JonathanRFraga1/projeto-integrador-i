package enums.order;

public enum OrderStatus {
    PENDING(1),
    PAID(2),
    CANCELED(3),
    READY_TO_DELIVERING(4),
    SHIPPED(5),
    DELIVERED(6),
    CLOSED(8);
    
    private final int code;

    private OrderStatus(int code) {
        this.code = code;
    }
}
