package abstracts;

import java.util.Date;


public abstract class Shipping {
    private float shippingCoast = 0;
    private Date estimatedDate;

    public Shipping() {
    }

    public Shipping(float shippingCoast, Date estimatedDate) {
        this.shippingCoast = shippingCoast;
        this.estimatedDate = estimatedDate;
    }

    public float getShippingCoast() {
        return shippingCoast;
    }

    public void setShippingCoast(float shippingCoast) {
        this.shippingCoast = shippingCoast;
    }

    public Date getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(Date estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String toJson() {
        return "{}";
    }

    public float calculateCost() {
        return 0;
    }
}
