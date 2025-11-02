package enums.order;

public enum OrderStatus {
    PENDING(1),
    PAID(2),
    CANCELED(3),
    READY_TO_DELIVERING(4),
    SHIPPED(5),
    DELIVERED(6),
    CLOSED(7);
    
    private final int code;

    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para OrderStatus: " + code);
    }
}
