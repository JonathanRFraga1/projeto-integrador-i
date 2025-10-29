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

        Customer customer;
        CustomerType customerType = CustomerType.fromCode(rs.getInt("customer_type"));
        switch (customerType) {
            case CustomerType.CUSTOMER_LEGAL -> customer = new CustomerLegal();
            case CustomerType.CUSTOMER_PHYSICAL ->  customer = new CustomerPhysical();
            default -> throw new IllegalArgumentException("Tipo de customer desconhecido");
        }

        customer.setId(rs.getInt("customer_id"));
        cart.setCustomer(customer);

        cart.setCartStatus(CartStatus.fromCode(rs.getInt("cart_status")));

        return cart;
    }

    @Override
    public void insert(Cart cart) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO carts (customer_id, cart_status) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, cart.getCustomer().getId());
                stmt.setInt(2, cart.getCartStatus().getCode());
                stmt.executeUpdate();
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
