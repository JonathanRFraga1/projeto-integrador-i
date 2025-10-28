package models.order;

import enums.order.PaymentMethod;

public class Payment {
    private int paymentId;
    private int orderId;
    private float amount;
    private PaymentMethod paymentMethod;
    private float additions;
    private float discounts;
    private float shippingPrice;
    private int installments = 1;

    public Payment() {

    }

    public Payment(int paymentId, int orderId, float amount, PaymentMethod paymentMethod, float additions, float discounts, float shippingPrice, int installments) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.additions = additions;
        this.discounts = discounts;
        this.shippingPrice = shippingPrice;
        this.installments = installments;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public float getAdditions() {
        return additions;
    }

    public void setAdditions(float additions) {
        this.additions = additions;
    }

    public float getDiscounts() {
        return discounts;
    }

    public void setDiscounts(float discounts) {
        this.discounts = discounts;
    }

    public float getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(float shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }
}
