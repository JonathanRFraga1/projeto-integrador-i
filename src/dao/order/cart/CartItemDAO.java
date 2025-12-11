package dao.order.cart;

import dao.GenericDAO;
import models.order.checkout.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO extends GenericDAO<CartItem> {
    @Override
    protected String getTableName() {
        return "cart_items";
    }

    @Override
    protected CartItem mapResultSetToEntity(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getInt("id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setItemId(rs.getInt("item_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setSubtotal(rs.getFloat("subtotal"));
        return cartItem;
    }

    @Override
    public int insert(CartItem cartItem) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO cart_items (cart_id, item_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, cartItem.getCartId());
                stmt.setInt(2, cartItem.getItemId());
                stmt.setInt(3, cartItem.getQuantity());
                stmt.setFloat(4, cartItem.getSubtotal());
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
    public void update(CartItem cartItem) throws SQLException {
        try {
            connect();
            String sql = "UPDATE cart_items SET quantity = ?, subtotal = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, cartItem.getQuantity());
                stmt.setFloat(2, cartItem.getSubtotal());
                stmt.setInt(3, cartItem.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
    
    public ArrayList<CartItem> getByCartId(int cartId) throws SQLException {
        ArrayList<CartItem> items = new ArrayList<>();
        
        try {
            connect();
            String sql = "SELECT * FROM cart_items WHERE cart_id = ?";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, cartId);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        items.add(mapResultSetToEntity(rs));
                    }
                }
            }
        } finally {
            disconnect();
        }
        
        return items;
    }
}
