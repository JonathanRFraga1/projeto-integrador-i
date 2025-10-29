package models.order.shipping;

import abstracts.Shipping;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ShippingPickup extends Shipping {
    private String pickupLocation;
    private String pickupCode;

    public ShippingPickup() {

    }

    public ShippingPickup(String jsonData) {
        super();
        try {
            JSONObject obj = new JSONObject(jsonData);
            this.setShippingCoast((float) obj.getDouble("shippingCoast"));
            this.setEstimatedDate(new Date(obj.getLong("estimatedDate")));
            this.pickupCode = obj.getString("pickupCode");
            this.pickupLocation = obj.getString("pickupLocation");
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON inválido para ShippingPickup", e);
        }
    }

    public ShippingPickup(float shippingCoast, Date estimatedDate, String pickupLocation, String pickupCode) {
        super(shippingCoast, estimatedDate);
        this.pickupLocation = pickupLocation;
        this.pickupCode = pickupCode;
    }

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

    @Override
    public String toJson() throws RuntimeException {
        try {
            JSONObject obj = new JSONObject();
            obj.put("shippingCoast", this.getShippingCoast());
            obj.put("estimatedDate", this.getEstimatedDate().getTime());
            obj.put("pickupLocation", pickupLocation);
            obj.put("pickupCode", pickupCode);
            return obj.toString();
        } catch (JSONException e) {
            throw new RuntimeException("Erro ao converter ShippingPickup para JSON: dados incompletos ou inválidos", e);
        }
    }

    public float calculateCost(float[] items) {
        float total = 0;

        for (float item : items) {
            total += item;
        }

        return total;
    }
}
