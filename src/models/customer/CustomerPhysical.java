package models.customer;

import abstracts.Customer;
import enums.customer.CustomerPhysicalGender;
import enums.customer.CustomerStatus;

import java.util.Date;

public class CustomerPhysical extends Customer {
    private String cpf;
    private Date birthDate;
    private CustomerPhysicalGender gender;

    public CustomerPhysical() {
        super();
    }

    public CustomerPhysical(String cpf, Date birthDate, CustomerPhysicalGender gender) {
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public CustomerPhysical(int id, String name, int addressId, String email, CustomerStatus status, String cpf, Date birthDate, CustomerPhysicalGender gender) {
        super(id, name, addressId, email, status);
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public CustomerPhysicalGender getGender() {
        return gender;
    }

    public void setGender(CustomerPhysicalGender gender) {
        this.gender = gender;
    }
}
