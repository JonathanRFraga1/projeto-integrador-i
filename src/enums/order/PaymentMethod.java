package enums.order;

public enum PaymentMethod {
    CASH("cash"),
    CREDIT_CARD("credit_card"),
    DEBIT_CARD("debit_card"),
    PIX("pix"),
    BANK_SLIP("bank_slip");

    private final String code;

    PaymentMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PaymentMethod fromCode(String code) {
        for (PaymentMethod status : PaymentMethod.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para PaymentMethod: " + code);
    }
}