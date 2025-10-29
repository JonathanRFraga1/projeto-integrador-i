package models.order.shipping;

import abstracts.Shipping;

import java.util.Date;
import org.json.JSONObject;
import org.json.JSONException;

public class ShippingDelivery extends Shipping {
    // private Address address;
    private String carrier;

    public ShippingDelivery() {

    }

    public ShippingDelivery(String jsonData) {
        super();
        try {
            JSONObject obj = new JSONObject(jsonData);
            this.setShippingCoast((float) obj.getDouble("shippingCoast"));
            this.setEstimatedDate(new Date(obj.getLong("estimatedDate")));
            this.carrier = obj.getString("carrier");
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON inválido para ShippingDelivery", e);
        }
    }

    public ShippingDelivery(float shippingCoast, Date estimatedDate, String carrier) {
        super(shippingCoast, estimatedDate);
        this.carrier = carrier;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Override
    public String toJson() throws RuntimeException {
        try {
            JSONObject obj = new JSONObject();
            obj.put("shippingCoast", this.getShippingCoast());
            obj.put("estimatedDate", this.getEstimatedDate().getTime());
            obj.put("carrier", carrier);
            return obj.toString();
        } catch (JSONException e) {
            throw new RuntimeException("Erro ao converter ShippingDelivery para JSON: dados incompletos ou inválidos", e);
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
