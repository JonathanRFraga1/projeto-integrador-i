package enums.item;

public enum StockOperation {
    INCREASE(1),
    DECREASE(2);

    private int code;

    StockOperation(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StockOperation fromCode(int code) {
        for (StockOperation status : StockOperation.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para StockOperation: " + code);
    }
}