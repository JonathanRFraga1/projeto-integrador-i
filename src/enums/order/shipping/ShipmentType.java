package enums.order.shipping;

public enum ShipmentType {
    DELIVERY("delivery"),
    PICKUP("pickup");

    private final String code;

    ShipmentType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ShipmentType fromCode(String code) {
        for (ShipmentType status : ShipmentType.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para ShipmentType: " + code);
    }
}
