package controllers.orders;

import abstracts.Customer;
import dao.customer.CustomerPhysicalDAO;
import dao.item.ItemDAO;
import dao.order.OrderDAO;
import dao.order.OrderItemDAO;
import dao.order.cart.CartDAO;
import dao.order.cart.CartItemDAO;
import enums.item.ItemStatus;
import enums.order.OrderStatus;
import enums.order.checkout.CartStatus;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Item;
import models.Seller;
import models.customer.CustomerPhysical;
import models.order.checkout.Cart;
import models.order.checkout.CartItem;
import models.order.Order;
import models.order.OrderItem;
import views.orders.DisplayBudgetView;

public class DisplayBudgetController {

    DisplayBudgetView view;

    private ArrayList<CartItem> items = new ArrayList<CartItem>();

    public DisplayBudgetController(DisplayBudgetView createBudgetView) {
        this.view = createBudgetView;
    }

    public void getBudget(int budgetId) {
        try {
            CartDAO dao = new CartDAO();
            CustomerPhysicalDAO customerDao = new CustomerPhysicalDAO();
            CartItemDAO cartItemDAO = new CartItemDAO();
            ItemDAO itemDAO = new ItemDAO();

            Cart cart = dao.getById(budgetId);

            DefaultTableModel model = (DefaultTableModel) view.tableBudgetItems.getModel();
            model.setRowCount(0);

            ArrayList<CartItem> items = cartItemDAO.getByCartId(cart.getId());

            CustomerPhysical customer = customerDao.getById(cart.getCustomer().getId());

            view.labelCustomerName.setText("Cliente: " + customer.getName());

            float subtotal = 0;

            for (CartItem cartItem : items) {
                subtotal += cartItem.getSubtotal();
                int itemId = cartItem.getItemId();
                Item item = itemDAO.getById(itemId);

                model.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getFinalPrice(),
                    cartItem.getQuantity(),
                    cartItem.getSubtotal(),});
            }

            view.labelTotalGeral.setText("Total: R$ " + subtotal);

            if (cart.getCartStatus() == CartStatus.CLOSED) {
                view.createOrderButtom.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createOrder(int cartId) {
        try {
            CartDAO dao = new CartDAO();

            CustomerPhysicalDAO customerDao = new CustomerPhysicalDAO();

            CartItemDAO cartItemDAO = new CartItemDAO();
            ItemDAO itemDAO = new ItemDAO();

            OrderDAO orderDAO = new OrderDAO();
            OrderItemDAO orderItemDAO = new OrderItemDAO();

            Cart cart = dao.getById(cartId);

            Order order = new Order();
            order.setCartId(cartId);
            order.setCustomer(cart.getCustomer());
            Seller seller = new Seller();
            seller.setId(1);
            order.setSeller(seller);

            ArrayList<CartItem> cartItems = cartItemDAO.getByCartId(cartId);
            ArrayList<OrderItem> orderItems = new ArrayList<>();

            float subtotal = 0;

            for (CartItem cartItem : cartItems) {
                subtotal += cartItem.getSubtotal();
                OrderItem orderItem = new OrderItem();
                
                Item item = itemDAO.getById(cartItem.getItemId());
                
                orderItem.setItemId(cartItem.getItemId());
                orderItem.setItemName(item.getName());
                orderItem.setItemPrice(item.getFinalPrice());
                orderItem.setQuantity(cartItem.getQuantity());
                
                orderItems.add(orderItem);
            }

            order.setTotalAmount(subtotal);

            order.setStatus(OrderStatus.PAID);

            int orderId = orderDAO.insert(order);
            
            for (OrderItem orderItem: orderItems) {
                orderItem.setOrderId(orderId);
                orderItemDAO.insert(orderItem);
            }
            
            cart.setCartStatus(CartStatus.CLOSED);
            
            dao.update(cart);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }
}
