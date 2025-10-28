package dao.order;

import dao.GenericDAO;
import enums.order.PaymentMethod;
import models.order.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO extends GenericDAO<Payment> {
    @Override
    protected String getTableName() {
        return "order_payments";
    }

    @Override
    protected Payment mapResultSetToEntity(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(rs.getInt("id"));
        payment.setOrderId(rs.getInt("order_id"));
        payment.setPaymentMethod(PaymentMethod.fromCode(rs.getString("payment_method")));
        payment.setAdditions(rs.getFloat("additions"));
        payment.setDiscounts(rs.getFloat("discounts"));
        payment.setInstallments(rs.getInt("installments"));
        payment.setShippingPrice(rs.getFloat("shipping_price"));
        payment.setAmount(rs.getFloat("amount"));
        return payment;
    }

    @Override
    public void insert(Payment payment) throws SQLException {
        try {
            connect();
            String sql = "INSERT INTO order_payments (order_id, payment_method, additions, discounts, installments, shipping_price, amount) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, payment.getOrderId());
                stmt.setString(2, payment.getPaymentMethod().getCode());
                stmt.setFloat(3, payment.getAdditions());
                stmt.setFloat(4, payment.getDiscounts());
                stmt.setInt(5, payment.getInstallments());
                stmt.setFloat(6, payment.getShippingPrice());
                stmt.setFloat(7, payment.getAmount());
                stmt.executeUpdate();
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void update(Payment payment) throws SQLException {
        try {
            connect();
            String sql = "UPDATE order_payments SET payment_method = ?, additions = ?,  discounts = ?, installments = ?, shipping_price = ?, amount = ? WHERE id = ?";
            try  (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, payment.getPaymentMethod().getCode());
                stmt.setFloat(2, payment.getAdditions());
                stmt.setFloat(3, payment.getDiscounts());
                stmt.setInt(4, payment.getInstallments());
                stmt.setFloat(5, payment.getShippingPrice());
                stmt.setFloat(6, payment.getAmount());
                stmt.setInt(7, payment.getPaymentId());
                stmt.executeUpdate();
            }
        }  finally {
            disconnect();
        }
    }
}
