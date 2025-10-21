package models.order.shipping;

import abstracts.Shipping;

public class Shipment {
    private int id;
    private int orderId;
    private float amount;
    private Shipping shippingDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Shipping getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(Shipping shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
