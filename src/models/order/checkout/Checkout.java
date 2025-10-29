package models.order.checkout;

import abstracts.Customer;
import models.order.Payment;

public class Checkout {
    public float amount;
    public int sellerId;
    public Customer customer;
    public Cart cart;
    public Payment payment;

    public Checkout() {

    }

    public Checkout(float amount, int sellerId, Customer customer, Cart cart, Payment payment) {
        this.amount = amount;
        this.sellerId = sellerId;
        this.customer = customer;
        this.cart = cart;
        this.payment = payment;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
