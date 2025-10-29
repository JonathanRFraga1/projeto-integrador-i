package models.order.shipping;

import abstracts.Shipping;
import enums.order.shipping.ShipmentType;

public class Shipment {
    private int id;
    private int orderId;
    private float amount;
    private ShipmentType shipmentType;
    private Shipping shippingDetails;

    public Shipment() {
    }

    public Shipment(int id, int orderId, float amount, ShipmentType shipmentType, Shipping shippingDetails) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.shipmentType = shipmentType;
        this.shippingDetails = shippingDetails;
    }

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

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Shipping getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(Shipping shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
