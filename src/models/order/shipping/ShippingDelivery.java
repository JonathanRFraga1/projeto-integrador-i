package models.order.shipping;

import abstracts.Shipping;

public class ShippingDelivery extends Shipping {
    // private Address address;
    private String carrier;

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public float calculateCoast(float[] items) {
        float total = 0;

        for (float item : items) {
            total += item;
        }

        return total;
    }
}
