package dao.order;

import dao.GenericDAO;
import enums.order.OrderStatus;
import models.order.Order;
import models.order.OrderHistory;

import java.sql.*;

public class OrderHistoryDAO extends GenericDAO<OrderHistory> {
    @Override
    protected String getTableName() {
        return "orders_history";
    }

    @Override
    protected OrderHistory mapResultSetToEntity(ResultSet rs) throws SQLException {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(rs.getInt("id"));
        orderHistory.setStatus(OrderStatus.fromCode(rs.getInt("status")));
        orderHistory.setComment(rs.getString("comment"));
        orderHistory.setTimestamp(rs.getDate("timestamp"));

        Order order = new Order();
        order.setId(rs.getInt("order_id"));
        orderHistory.setOrder(order);

        return orderHistory;
    }

    @Override
    public void insert(OrderHistory orderHistory) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO orders_history (order_id, comment, status) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, orderHistory.getOrder().getId());
                stmt.setString(2, orderHistory.getComment());
                stmt.setInt(3, orderHistory.getStatus().getCode());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(OrderHistory orderHistory) throws SQLException {
        throw new UnsupportedOperationException("O histórico de pedidos (OrderHistory) é imutável e não pode ser atualizado.");
    }
}
