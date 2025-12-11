package models;

import enums.item.ItemStatus;
import enums.item.StockOperation;

public class Item {

    private int id;
    private String name;
    private float price;
    private float promotionalPrice;
    private float quantity;
    private String photo;
    private ItemStatus status;

    public Item() {
    }

    public Item(int id, String name, float price, float promotionalPrice, float quantity, String photo, ItemStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.promotionalPrice = promotionalPrice;
        this.quantity = quantity;
        this.photo = photo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPromotionalPrice() {
        return promotionalPrice;
    }

    public void setPromotionalPrice(float promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public void updateStock(StockOperation operation, float stockValue) {
        switch (operation) {
            case StockOperation.DECREASE ->
                this.quantity -= stockValue;
            case StockOperation.INCREASE ->
                this.quantity += stockValue;
        }
    }

    public float getFinalPrice() {
        float finalPrice = this.price;

        if (this.promotionalPrice > 0 && this.price > this.promotionalPrice) {
            finalPrice = this.promotionalPrice;
        }

        return finalPrice;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", promotionalPrice=" + promotionalPrice
                + ", quantity=" + quantity
                + ", photo='" + photo + '\''
                + ", status=" + status
                + '}';
    }
}
