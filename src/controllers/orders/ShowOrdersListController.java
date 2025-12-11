package controllers.orders;

import dao.customer.CustomerPhysicalDAO;
import dao.order.OrderDAO;
import enums.order.OrderStatus;
import enums.order.checkout.CartStatus;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.customer.CustomerPhysical;
import models.order.Order;
import views.orders.ShowOrdersListView;

public class ShowOrdersListController {

    ShowOrdersListView view;

    public ShowOrdersListController(ShowOrdersListView showBudgetsListView) {
        this.view = showBudgetsListView;
    }

    public void getOrders() {
        try {
            DefaultTableModel model = (DefaultTableModel) view.listBudgetsTable.getModel();
            model.setRowCount(0);

            OrderDAO orderDAO = new OrderDAO();
            CustomerPhysicalDAO customerDao = new CustomerPhysicalDAO();
            List<Order> orders = orderDAO.getAll();

            for (Order order : orders) {
                OrderStatus status = order.getStatus();

                String statusString = switch (status) {
                    case PAID ->
                        "Pagamento Realizado";
                    case PENDING ->
                        "Pagamento Pendente";
                    case CANCELED ->
                        "Cancelado";
                    case READY_TO_DELIVERING ->
                        "Pronto para Envio";
                    case SHIPPED ->
                        "Enviado";
                    case DELIVERED ->
                        "Entregue";
                    case CLOSED ->
                        "Fechado";
                    default ->
                        "Status Desconhecido: " + status.name();
                };
                
                CustomerPhysical customer = customerDao.getById(order.getCustomer().getId());

                model.addRow(new Object[]{
                    order.getId(),
                    customer.getName(),
                    order.getTotalAmount(),
                    statusString
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
