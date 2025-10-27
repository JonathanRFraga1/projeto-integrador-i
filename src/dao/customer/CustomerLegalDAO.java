package dao.customer;

import dao.GenericDAO;
import enums.customer.CustomerStatus;
import models.customer.CustomerLegal;

import java.sql.*;

public class CustomerLegalDAO extends GenericDAO<CustomerLegal> {

    @Override
    protected String getTableName() {
        return "customers_legal";
    }

    @Override
    protected CustomerLegal mapResultSetToEntity(ResultSet rs) throws SQLException {
        CustomerLegal customerLegal = new CustomerLegal();
        customerLegal.setId(rs.getInt("id"));
        customerLegal.setName(rs.getString("name"));
        customerLegal.setEmail(rs.getString("email"));
        customerLegal.setCnpj(rs.getString("cnpj"));
        customerLegal.setResponsibleName(rs.getString("responsible_name"));
        customerLegal.setStatus(CustomerStatus.fromCode(rs.getInt("status")));
        return customerLegal;
    }

    @Override
    public void insert(CustomerLegal customerLegal) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO customers_legal (name, email, cnpj, responsible_name, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, customerLegal.getName());
                stmt.setString(2, customerLegal.getEmail());
                stmt.setString(3, customerLegal.getCnpj());
                stmt.setString(4, customerLegal.getResponsibleName());
                stmt.setInt(5, customerLegal.getStatus().getCode());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(CustomerLegal customerLegal) throws SQLException {
        try {
            connect();
            String sql = "UPDATE customers_legal SET name = ?, email = ?, cnpj = ?, responsible_name = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, customerLegal.getName());
                stmt.setString(2, customerLegal.getEmail());
                stmt.setString(3, customerLegal.getCnpj());
                stmt.setString(4, customerLegal.getResponsibleName());
                stmt.setInt(5, customerLegal.getStatus().getCode());
                stmt.setInt(6, customerLegal.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
