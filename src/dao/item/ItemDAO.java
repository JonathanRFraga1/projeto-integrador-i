package dao.item;

import dao.GenericDAO;
import enums.item.ItemStatus;
import models.Item;

import java.sql.*;

public class ItemDAO extends GenericDAO<Item> {

    @Override
    protected String getTableName() {
        return "items";
    }

    @Override
    protected Item mapResultSetToEntity(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setPrice(rs.getFloat("price"));
        item.setPromotionalPrice(rs.getFloat("promotional_price"));
        item.setQuantity(rs.getFloat("quantity"));
        item.setPhoto(rs.getString("photo"));
        item.setStatus(ItemStatus.fromCode(rs.getInt("status")));
        return item;
    }

    @Override
    public void insert(Item item) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO items (name, price, promotional_price, quantity, photo, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, item.getName());
                stmt.setFloat(2, item.getPrice());
                stmt.setFloat(3, item.getPromotionalPrice());
                stmt.setFloat(4, item.getQuantity());
                stmt.setString(5, item.getPhoto());
                stmt.setInt(6, item.getStatus().getCode());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(Item item) throws SQLException {
        try {
            connect();
            String sql = "UPDATE items SET name = ?, price = ?, promotional_price = ?, quantity = ?, photo = ?, status = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, item.getName());
                stmt.setFloat(2, item.getPrice());
                stmt.setFloat(3, item.getPromotionalPrice());
                stmt.setFloat(4, item.getQuantity());
                stmt.setString(5, item.getPhoto());
                stmt.setInt(6, item.getStatus().getCode());
                stmt.setInt(7, item.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
