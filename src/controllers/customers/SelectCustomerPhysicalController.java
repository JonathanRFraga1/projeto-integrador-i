package controllers.customers;

import dao.customer.CustomerPhysicalDAO;
import enums.customer.CustomerPhysicalGender;
import enums.customer.CustomerStatus;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.customer.CustomerPhysical;
import views.customers.SelectCustomerPhysical;

public class SelectCustomerPhysicalController {
    SelectCustomerPhysical view;
    
    public SelectCustomerPhysicalController(SelectCustomerPhysical selectCustomerPhysical) {
        this.view = selectCustomerPhysical;
    }


    public void getCustomers() {
        try {
            CustomerPhysicalDAO dao = new CustomerPhysicalDAO();

            List<CustomerPhysical> customers = dao.getAll();

            System.out.println(customers.size());

            DefaultTableModel model = (DefaultTableModel) view.customersTable.getModel();
            model.setRowCount(0);

            for (CustomerPhysical customer : customers) {
                CustomerStatus status = customer.getStatus();
                CustomerPhysicalGender gender = customer.getGender();
                String cpf = customer.getCpf();

                String statusString = switch (status) {
                    case CustomerStatus.ACTIVE ->
                        "Ativo";
                    case CustomerStatus.BLOCKED ->
                        "Bloqueado";
                    case CustomerStatus.INACTIVE ->
                        "Inativo";
                };

                String genderString = switch (gender) {
                    case CustomerPhysicalGender.FEMININE ->
                        "Feminino";
                    case CustomerPhysicalGender.MASCULINE ->
                        "Masculino";
                    case CustomerPhysicalGender.OTHER ->
                        "Outros";
                    case CustomerPhysicalGender.NOT_SPECIFIED ->
                        "Não Especificado";
                };

                String cpfFormatted = String.format("%s.%s.%s-%s",
                        cpf.substring(0, 3),
                        cpf.substring(3, 6),
                        cpf.substring(6, 9),
                        cpf.substring(9)
                );

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String birthDateFormatted = "";
                if (customer.getBirthDate() != null) {
                    birthDateFormatted = sdf.format(customer.getBirthDate());
                }

                model.addRow(new Object[]{
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    cpfFormatted,
                    birthDateFormatted,
                    genderString,
                    statusString
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCustomerById(String id) {
        try {
            CustomerPhysicalDAO dao = new CustomerPhysicalDAO();

            CustomerPhysical customer = dao.getById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
