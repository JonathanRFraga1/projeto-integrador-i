package models.order.shipping;

import abstracts.Shipping;

public class ShippingPickup extends Shipping {
    private String pickupLocation;
    private String pickupCode;

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getPickupCode() {
        return pickupCode;
    }

    public void setPickupCode(String pickupCode) {
        this.pickupCode = pickupCode;
    }

    public float calculateCoast(float[] items) {
        float total = 0;

        for (float item : items) {
            total += item;
        }

        return total;
    }
}
