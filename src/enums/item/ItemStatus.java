package enums.item;

public enum ItemStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int code;

    ItemStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ItemStatus fromCode(int code) {
        for (ItemStatus status : ItemStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para ItemStatus: " + code);
    }
}