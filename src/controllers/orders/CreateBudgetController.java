package controllers.orders;

import dao.customer.CustomerPhysicalDAO;
import dao.item.ItemDAO;
import dao.order.cart.CartDAO;
import dao.order.cart.CartItemDAO;
import enums.item.ItemStatus;
import enums.order.checkout.CartStatus;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Item;
import models.customer.CustomerPhysical;
import models.order.checkout.Cart;
import models.order.checkout.CartItem;
import views.orders.CreateBudgetView;

public class CreateBudgetController {

    CreateBudgetView view;
    DefaultTableModel modelItemsBudget;
    DefaultTableModel modelListItems;

    private ArrayList<CartItem> items = new ArrayList<CartItem>();

    public CreateBudgetController(CreateBudgetView createBudgetView) {
        this.view = createBudgetView;
    }

    public void getItemsToTable() {
        try {
            ItemDAO dao = new ItemDAO();

            List<Item> items = dao.getAll();

            System.out.println(items.size());

            modelListItems = (DefaultTableModel) view.itemsToSelectTable.getModel();
            modelListItems.setRowCount(0);

            modelItemsBudget = (DefaultTableModel) view.tableBudgetItems.getModel();
            modelItemsBudget.setRowCount(0);

            for (Item item : items) {
                ItemStatus status = item.getStatus();

                String statusString = switch (status) {
                    case ItemStatus.ACTIVE ->
                        "Ativo";
                    case ItemStatus.INACTIVE ->
                        "Inativo";
                };

                modelListItems.addRow(new Object[]{
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getPromotionalPrice(),
                    item.getQuantity(),
                    statusString
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item getItem(String id) {
        Item item = null;
        try {
            ItemDAO dao = new ItemDAO();

            item = dao.getById(Integer.parseInt(id));

            if (inBudget(item.getId())) {
                javax.swing.JOptionPane.showMessageDialog(
                        view,
                        "Este item já foi adicionado ao orçamento!",
                        "Item Duplicado",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    private boolean inBudget(int itemId) {
        for (int i = 0; i < modelItemsBudget.getRowCount(); i++) {
            int id = Integer.parseInt(modelItemsBudget.getValueAt(i, 0).toString());
            if (id == itemId) {
                return true;
            }
        }
        return false;
    }

    public void fillFields(Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setItemId(item.getId());
        cartItem.setQuantity(1);
        cartItem.setSubtotal(item.getFinalPrice());
        
        items.add(cartItem);
        modelItemsBudget.addRow(new Object[]{
            item.getId(),
            item.getName(),
            item.getFinalPrice(),
            "1",
            item.getFinalPrice(),
            "Remover"
        });
        updateTotalBudget();

    }

    public void removerItem(int row) {
        if (row >= 0 && row < modelItemsBudget.getRowCount()) {
            if (row < items.size()) {
                items.remove(row);
            }

            modelItemsBudget.removeRow(row);

            updateTotalBudget();
        }
    }

    public void updateTotalBudget() {
        double totalGeral = 0.0;

        for (int i = 0; i < modelItemsBudget.getRowCount(); i++) {
            Object subtotalObj = modelItemsBudget.getValueAt(i, 4);
            if (subtotalObj != null) {
                try {
                    double subtotal = Double.parseDouble(subtotalObj.toString().replace(",", "."));
                    totalGeral += subtotal;

                    updateCartItem(i);

                } catch (NumberFormatException ex) {
                    // Ignora valores inválidos
                }
            }
        }

        view.labelTotalGeral.setText(String.format("Total: R$ %.2f", totalGeral));
    }

    public double getTotalOrcamento() {
        double total = 0.0;

        for (int i = 0; i < modelItemsBudget.getRowCount(); i++) {
            Object subtotalObj = modelItemsBudget.getValueAt(i, 4);
            if (subtotalObj != null) {
                try {
                    total += Double.parseDouble(subtotalObj.toString().replace(",", "."));
                } catch (NumberFormatException ex) {
                    // Ignora valores inválidos
                }
            }
        }

        return total;
    }

    public void cleanFields() {
        modelItemsBudget.setRowCount(0);
        items.clear();
        updateTotalBudget();
        view.labelCustomerName.setText("Cliente: Não Selecionado");
        view.labelTotalGeral.setText("Total: R$ 0,00");
        view.setCustomerData(null);
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void updateCartItem(int row) {
        if (row >= 0 && row < items.size() && row < modelItemsBudget.getRowCount()) {
            try {
                // Pega a quantidade da tabela (coluna 3)
                int quantity = Integer.parseInt(modelItemsBudget.getValueAt(row, 3).toString());

                // Pega o preço unitário da tabela (coluna 2)
                double finalPrice = Float.parseFloat(
                        modelItemsBudget.getValueAt(row, 2).toString().replace(",", ".")
                );

                // Atualiza o CartItem correspondente
                CartItem cartItem = items.get(row);
                cartItem.setQuantity(quantity);
                cartItem.setSubtotal((float) (quantity * finalPrice));

            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                System.err.println("Erro ao atualizar CartItem: " + ex.getMessage());
            }
        }
    }

    public boolean saveBudget(String[] customerData) {
        try {
            Cart cart = new Cart();

            CustomerPhysicalDAO customerDAO = new CustomerPhysicalDAO();
            int customerId = Integer.parseInt(customerData[0]);
            CustomerPhysical customer = customerDAO.getById(customerId);

            cart.setCustomer(customer);
            cart.setCartStatus(CartStatus.ACTIVE);

            CartDAO cartDAO = new CartDAO();
            int cartId = cartDAO.insert(cart);
            cart.setId(cartId);

            cart.setCartItems(items);

            CartItemDAO cartItemDAO = new CartItemDAO();

            for (CartItem cartItem : items) {
                cartItem.setCartId(cartId);
                System.out.println(cartItem);
                cartItemDAO.insert(cartItem);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }
}
