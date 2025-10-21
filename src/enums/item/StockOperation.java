package enums.item;

public enum StockOperation {
    INCREASE(1),
    DECREASE(2);

    private int code;

    StockOperation(int code) {
        this.code = code;
    }
}