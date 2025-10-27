package abstracts;

import enums.customer.CustomerStatus;

public abstract class Customer {
    private int id;
    private String name;
    private int addressId;
    private String email;
    private CustomerStatus status;

    protected Customer() {}

    protected Customer(int id, String name, int addressId, String email, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.email = email;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}