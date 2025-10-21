package enums.item;

public enum ItemStatus {
    INACTIVE(0),
    ACTIVE(1);

    private int code;

    ItemStatus(int code) {
        this.code = code;
    }
}