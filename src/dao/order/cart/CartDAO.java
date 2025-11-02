package dao.order.cart;

import abstracts.Customer;
import dao.GenericDAO;
import enums.customer.CustomerType;
import enums.order.checkout.CartStatus;
import models.customer.CustomerLegal;
import models.customer.CustomerPhysical;
import models.order.checkout.Cart;

import java.sql.*;

public class CartDAO extends GenericDAO<Cart> {

    @Override
    protected String getTableName() {
        return "carts";
    }

    @Override
    protected Cart mapResultSetToEntity(ResultSet rs) throws SQLException {
        Cart cart = new Cart();

        CustomerType customerType = CustomerType.fromCode(rs.getInt("customer_type"));
        Customer customer = switch (customerType) {
            case CustomerType.CUSTOMER_LEGAL -> new CustomerLegal();
            case CustomerType.CUSTOMER_PHYSICAL -> new CustomerPhysical();
            default -> throw new IllegalArgumentException("Tipo de customer desconhecido");
        };

        customer.setId(rs.getInt("customer_id"));
        cart.setCustomer(customer);

        cart.setCartStatus(CartStatus.fromCode(rs.getInt("cart_status")));

        return cart;
    }

    @Override
    public int insert(Cart cart) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO carts (customer_id, customer_type, cart_status) VALUES (?, ?, ?)";

            Customer customer = cart.getCustomer();
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
                stmt.setInt(3, cart.getCartStatus().getCode());
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
    public void update(Cart cart) throws SQLException {
        try {
            connect();
            String sql = "UPDATE carts SET cart_status = ? WHERE customer_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, cart.getCartStatus().getCode());
                stmt.setInt(2, cart.getCustomer().getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
