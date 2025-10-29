package dao.customer;

import dao.GenericDAO;
import enums.customer.CustomerPhysicalGender;
import enums.customer.CustomerStatus;
import models.customer.CustomerPhysical;

import java.sql.Date;
import java.sql.*;

public class CustomerPhysicalDAO extends GenericDAO<CustomerPhysical> {

    @Override
    protected String getTableName() {
        return "customers_physical";
    }

    @Override
    protected CustomerPhysical mapResultSetToEntity(ResultSet rs) throws SQLException {
        CustomerPhysical customerPhysical = new CustomerPhysical();
        customerPhysical.setId(rs.getInt("id"));
        customerPhysical.setName(rs.getString("name"));
        customerPhysical.setEmail(rs.getString("email"));
        customerPhysical.setCpf(rs.getString("cpf"));
        customerPhysical.setBirthDate(rs.getDate("birth_date"));
        customerPhysical.setGender(CustomerPhysicalGender.fromCode(rs.getInt("gender")));
        customerPhysical.setStatus(CustomerStatus.fromCode(rs.getInt("status")));
        return customerPhysical;
    }

    @Override
    public int insert(CustomerPhysical customerPhysical) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO customers_physical (name, email, cpf, birth_date, gender, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, customerPhysical.getName());
                stmt.setString(2, customerPhysical.getEmail());
                stmt.setString(3, customerPhysical.getCpf());
                stmt.setDate(4, (Date) customerPhysical.getBirthDate());
                stmt.setInt(5, customerPhysical.getGender().getCode());
                stmt.setInt(6, customerPhysical.getStatus().getCode());
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                    throw new SQLException("Falha ao obter ID gerado");
                }
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(CustomerPhysical customerPhysical) throws SQLException {
        try {
            connect();
            String sql = "UPDATE customers_physical SET name = ?, email = ?, cpf = ?, birth_date = ?, gender = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, customerPhysical.getName());
                stmt.setString(2, customerPhysical.getEmail());
                stmt.setString(3, customerPhysical.getCpf());
                stmt.setDate(4, (Date) customerPhysical.getBirthDate());
                stmt.setInt(5, customerPhysical.getGender().getCode());
                stmt.setInt(6, customerPhysical.getStatus().getCode());
                stmt.setInt(7, customerPhysical.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
