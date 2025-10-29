package dao.order;

import abstracts.Customer;
import dao.GenericDAO;
import enums.customer.CustomerType;
import enums.order.OrderStatus;
import models.Seller;
import models.customer.CustomerLegal;
import models.customer.CustomerPhysical;
import models.order.Order;

import java.sql.*;

public class OrderDAO extends GenericDAO<Order> {
    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected Order mapResultSetToEntity(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setOrderDate(rs.getDate("created_at"));
        order.setCartId(rs.getInt("cart_id"));
        order.setTotalAmount(rs.getFloat("total_amount"));
        order.setStatus(OrderStatus.fromCode(rs.getInt("status")));

        Customer customer =
                CustomerType.fromCode(rs.getInt("customer_type")) == CustomerType.CUSTOMER_LEGAL ?
                new CustomerLegal() : new CustomerPhysical();

        customer.setId(rs.getInt("customer_id"));

        order.setCustomer(customer);

        Seller seller = new Seller();
        seller.setId(rs.getInt("seller_id"));

        order.setSeller(seller);

        return order;
    }

    @Override
    public int insert(Order order) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO orders (customer_id, customer_type, seller_id, total_amount, status) VALUES (?, ?, ?, ?, ?)";

            Customer customer = order.getCustomer();

            int customerType;

            if (customer instanceof CustomerLegal) {
                customerType = CustomerType.CUSTOMER_LEGAL.getCode();
            } else if (customer instanceof CustomerPhysical) {
                customerType = CustomerType.CUSTOMER_PHYSICAL.getCode();
            } else {
                throw new IllegalArgumentException("Tipo de cliente desconhecido: " + customer.getClass().getName());
            }

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, customer.getId());
                stmt.setInt(2, customerType);
                stmt.setInt(3, order.getSeller().getId());
                stmt.setFloat(4, order.getTotalAmount());
                stmt.setInt(5, order.getStatus().getCode());
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
    public void update(Order order) throws SQLException {
        try {
            connect();
            String sql = "UPDATE orders SET total_amount = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setFloat(1, order.getTotalAmount());
                stmt.setInt(2, order.getStatus().getCode());
                stmt.setInt(3, order.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
