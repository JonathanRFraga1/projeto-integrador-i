package models.order.checkout;

import abstracts.Customer;
import enums.order.checkout.CartStatus;

import java.util.ArrayList;

public class Cart {
    private int id;
    private Customer customer;
    private ArrayList<CartItem> cartItems;
    private CartStatus cartStatus;

    public Cart() {

    }

    public Cart(int id, Customer customer, ArrayList<CartItem> cartItems, CartStatus cartStatus) {
        this.id = id;
        this.customer = customer;
        this.cartItems = cartItems;
        this.cartStatus = cartStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }
}
