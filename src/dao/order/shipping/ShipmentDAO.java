package dao.order.shipping;

import abstracts.Shipping;
import dao.GenericDAO;
import enums.order.shipping.ShipmentType;
import models.order.shipping.Shipment;
import models.order.shipping.ShippingDelivery;
import models.order.shipping.ShippingPickup;

import java.sql.*;

public class ShipmentDAO extends GenericDAO<Shipment> {
    @Override
    protected String getTableName() {
        return "order_shipments";
    }

    @Override
    protected Shipment mapResultSetToEntity(ResultSet rs) throws SQLException {
        Shipment shipment = new Shipment();
        shipment.setId(rs.getInt("id"));
        shipment.setOrderId(rs.getInt("order_id"));
        shipment.setAmount(rs.getFloat("amount"));
        shipment.setShipmentType(ShipmentType.fromCode(rs.getString("shipment_type")));

        String shipmentValues = rs.getString("shipment_values");

        Shipping shipping;
        switch (shipment.getShipmentType()) {
            case ShipmentType.DELIVERY -> shipping = new ShippingDelivery(shipmentValues);
            case ShipmentType.PICKUP -> shipping = new ShippingPickup(shipmentValues);
            default -> throw new IllegalArgumentException("Tipo de shipping desconhecido");
        }

        shipment.setShippingDetails(shipping);

        return shipment;
    }

    @Override
    public void insert(Shipment shipment) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO order_shipments(order_id, amount, shipment_type, shipment_values) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, shipment.getOrderId());
                stmt.setFloat(2, shipment.getAmount());
                stmt.setString(3, shipment.getShipmentType().getCode());
                stmt.setString(4, shipment.getShippingDetails().toJson());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(Shipment shipment) throws SQLException {
        try {
            connect();
            String sql = "UPDATE order_shipments SET amount = ?, shipment_values = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setFloat(1, shipment.getAmount());
                stmt.setString(2, shipment.getShippingDetails().toJson());
                stmt.setInt(3, shipment.getId());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }
}
