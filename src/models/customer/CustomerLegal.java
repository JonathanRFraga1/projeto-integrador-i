package models.customer;

import abstracts.Customer;
import enums.customer.CustomerStatus;

public class CustomerLegal extends Customer {
    private String cnpj;
    private String responsibleName;

    public CustomerLegal() {
        super();
    }

    public CustomerLegal(String cnpj, String responsibleName) {
        this.cnpj = cnpj;
        this.responsibleName = responsibleName;
    }

    public CustomerLegal(int id, String name, int addressId, String email, CustomerStatus status, String cnpj, String responsibleName) {
        super(id, name, addressId, email, status);
        this.cnpj = cnpj;
        this.responsibleName = responsibleName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }
}