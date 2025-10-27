package dao.order;

import dao.GenericDAO;
import models.order.OrderItem;

import java.sql.*;

public class OrderItemDAO extends GenericDAO<OrderItem> {
    @Override
    protected String getTableName() {
        return "order_items";
    }

    @Override
    protected OrderItem mapResultSetToEntity(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt("id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setItemId(rs.getInt("item_id"));
        orderItem.setItemName(rs.getString("item_name"));
        orderItem.setItemPrice(rs.getInt("item_price"));
        orderItem.setQuantity(rs.getInt("quantity"));
        return orderItem;
    }

    @Override
    public void insert(OrderItem orderItem) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO order_items (order_id, item_id, item_name, item_price, quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, orderItem.getOrderId());
                stmt.setInt(2, orderItem.getItemId());
                stmt.setString(3, orderItem.getItemName());
                stmt.setFloat(4, orderItem.getItemPrice());
                stmt.setFloat(5, orderItem.getQuantity());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(OrderItem orderItem) throws SQLException {
        try {
            connect();
            String sql = "UPDATE order_items SET item_price = ?, quantity = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setFloat(1, orderItem.getItemPrice());
                stmt.setFloat(2, orderItem.getQuantity());
                stmt.setInt(3, orderItem.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
