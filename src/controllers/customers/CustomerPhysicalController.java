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
import views.customers.CustomerPhysicalView;

public class CustomerPhysicalController {

    CustomerPhysicalView view;

    public CustomerPhysicalController(CustomerPhysicalView customerPhysicalView) {
        this.view = customerPhysicalView;
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

            fillFields(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createCustomer() {
        try {
            String name = view.name.getText();
            String email = view.email.getText();
            String cpf = view.cpf.getText();
            String birthDate = view.birthDate.getText();
            int gender = view.gender.getSelectedIndex() + 1;
            int status = view.status.getSelectedIndex();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(birthDate, formatter);
            Date utilDate = Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());

            CustomerPhysical customer = new CustomerPhysical();
            customer.setName(name);
            customer.setEmail(email);
            customer.setCpf(cpf);
            customer.setBirthDate(utilDate);
            customer.setGender(CustomerPhysicalGender.fromCode(gender));
            customer.setStatus(CustomerStatus.fromCode(status));

            CustomerPhysicalDAO dao = new CustomerPhysicalDAO();

            int id = 0;

            try {
                id = dao.insert(customer);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Erro ao inserir Cliente Pessoa Física", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(view, "Cliente inserido com sucesso", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);

            customer.setId(id);

            view.id.setText(String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillFields(CustomerPhysical customer) {
        String cpf = customer.getCpf();
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
        
        System.out.println(customer.getStatus().getCode());

        view.id.setText(String.valueOf(customer.getId()));
        view.name.setText(customer.getName());
        view.email.setText(customer.getEmail());
        view.cpf.setText(cpfFormatted);
        view.birthDate.setText(birthDateFormatted);
        view.gender.setSelectedIndex(customer.getGender().getCode() - 1);
        view.status.setSelectedIndex(customer.getStatus().getCode());

    }

    public void cleanFields() {

    }
}
