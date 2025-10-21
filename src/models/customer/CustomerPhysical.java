package models.customer;

import abstracts.Customer;
import enums.customer.CustomerPhysicalGender;
import java.util.Date;

public class CustomerPhysical extends Customer {
    private String cpf;
    private Date birthDate;
    private CustomerPhysicalGender gender;

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
