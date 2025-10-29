package models.order.checkout;

public class CartItem {
    private int id;
    private int itemId;
    private int quantity;
    private float subtotal;

    public CartItem() {

    }

    public CartItem(int id, int itemId, int quantity, float subtotal) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public void calculateSubtotal() {

    }
}
