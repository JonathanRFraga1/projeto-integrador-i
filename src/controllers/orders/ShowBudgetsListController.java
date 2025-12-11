package controllers.orders;

import controllers.customers.*;
import dao.customer.CustomerPhysicalDAO;
import dao.order.cart.CartDAO;
import dao.order.cart.CartItemDAO;
import enums.customer.CustomerPhysicalGender;
import enums.customer.CustomerStatus;
import enums.order.checkout.CartStatus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.customer.CustomerPhysical;
import models.order.checkout.Cart;
import models.order.checkout.CartItem;
import views.orders.ShowBudgetsListView;

public class ShowBudgetsListController {
    ShowBudgetsListView view;
    
    public ShowBudgetsListController(ShowBudgetsListView showBudgetsListView) {
        this.view = showBudgetsListView;
    }


    public void getBudgets() {
        try {
            CartDAO dao = new CartDAO();
            CustomerPhysicalDAO customerDao = new CustomerPhysicalDAO();
            CartItemDAO cartItemDAO = new CartItemDAO();

            List<Cart> carts = dao.getAll();

            System.out.println(carts.size());

            DefaultTableModel model = (DefaultTableModel) view.listBudgetsTable.getModel();
            model.setRowCount(0);

            for (Cart cart : carts) {
                System.out.println(cart.getId());
                CartStatus status = cart.getCartStatus();
               
                String statusString = switch (status) {
                    case CartStatus.ACTIVE ->
                        "Aberto";
                    case CartStatus.ABANDONED ->
                        "Abandonado";
                    case CartStatus.CLOSED ->
                        "Fechado";
                };
                
                CustomerPhysical customer = customerDao.getById(cart.getCustomer().getId());
                
                ArrayList<CartItem> items = cartItemDAO.getByCartId(cart.getId());
                
                float subtotal = 0;
                
                for (CartItem item: items) {
                    subtotal+= item.getSubtotal();
                }

             
                model.addRow(new Object[]{
                    cart.getId(),
                    customer.getName(),
                    subtotal,
                    statusString
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCustomerById(String id) {
        try {
            CustomerPhysicalDAO dao = new CustomerPhysicalDAO();

            CustomerPhysical customer = dao.getById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
