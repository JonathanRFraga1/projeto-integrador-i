/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package views.orders;

import controllers.orders.CreateBudgetController;
import java.util.ArrayList;
import views.customers.SelectCustomerPhysical;
import models.Item;
import models.order.checkout.CartItem;

/**
 *
 * @author jonat
 */
public class CreateBudgetView extends javax.swing.JInternalFrame {

    private SelectCustomerPhysical frame;

    private CreateBudgetController controller;

    private String[] customerData = null;

    public CreateBudgetView() {
        initComponents();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Novo Orçamento");

        controller = new CreateBudgetController(this);
        controller.getItemsToTable();

        // Listener para duplo clique na tabela de seleção
        itemsToSelectTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedLine = itemsToSelectTable.getSelectedRow();
                if (selectedLine != -1 && evt.getClickCount() == 2) {
                    String id = itemsToSelectTable.getValueAt(selectedLine, 0).toString();
                    Item item = controller.getItem(id);
                    controller.fillFields(item);
                }
            }
        });

        // NOVO: Listener para recalcular subtotal quando quantidade mudar
        tableBudgetItems.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    // Coluna 3 é Quantidade
                    if (column == 3 && row >= 0) {
                        recalcularSubtotal(row);
                    }
                }
            }
        });

        // NOVO: Configurar o editor da coluna Quantidade para validação
        configurarCellEditorQuantidade();

        // NOVO: Configurar botão remover
        configurarBotaoRemover();
    }
    
    public void setCustomerData(String[] customerData) {
        this.customerData = customerData;
    }

    // NOVO MÉTODO: Recalcula o subtotal de uma linha
    private void recalcularSubtotal(int row) {
        javax.swing.table.DefaultTableModel model
                = (javax.swing.table.DefaultTableModel) tableBudgetItems.getModel();

        try {
            // Coluna 2: Preço de Venda
            Object precoObj = model.getValueAt(row, 2);
            // Coluna 3: Quantidade
            Object quantidadeObj = model.getValueAt(row, 3);

            if (precoObj != null && quantidadeObj != null) {
                double preco = Double.parseDouble(precoObj.toString().replace(",", "."));
                int quantidade = Integer.parseInt(quantidadeObj.toString());

                // Validação: quantidade deve ser positiva
                if (quantidade <= 0) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "A quantidade deve ser maior que zero!",
                            "Quantidade Inválida",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    model.setValueAt(1, row, 3); // Volta para 1
                    quantidade = 1;
                }

                // Calcula o subtotal
                double subtotal = preco * quantidade;

                // Atualiza a coluna Subtotal (índice 4)
                model.setValueAt(subtotal, row, 4);

                // Atualiza o total geral
                controller.updateTotalBudget();
            }
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Valor inválido para quantidade!",
                    "Erro",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            model.setValueAt(1, row, 3); // Volta para 1
            model.setValueAt(model.getValueAt(row, 2), row, 4); // Subtotal = Preço
        }
    }

    // NOVO MÉTODO: Configura validação na célula de quantidade
    private void configurarCellEditorQuantidade() {
        javax.swing.JTextField textField = new javax.swing.JTextField();

        javax.swing.DefaultCellEditor editor = new javax.swing.DefaultCellEditor(textField) {
            @Override
            public boolean stopCellEditing() {
                String value = textField.getText();

                try {
                    int quantidade = Integer.parseInt(value);
                    if (quantidade <= 0) {
                        javax.swing.JOptionPane.showMessageDialog(
                                tableBudgetItems,
                                "A quantidade deve ser maior que zero!",
                                "Quantidade Inválida",
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );
                        return false; // Não permite sair da célula
                    }
                } catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(
                            tableBudgetItems,
                            "Digite apenas números!",
                            "Valor Inválido",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                    return false; // Não permite sair da célula
                }

                return super.stopCellEditing();
            }
        };

        // Aplica o editor na coluna Quantidade (índice 3)
        tableBudgetItems.getColumnModel().getColumn(3).setCellEditor(editor);
    }

    // NOVO MÉTODO: Configura o botão remover
    private void configurarBotaoRemover() {
        // Renderizador do botão
        tableBudgetItems.getColumnModel().getColumn(5).setCellRenderer(
                new javax.swing.table.DefaultTableCellRenderer() {
            private final javax.swing.JButton button = new javax.swing.JButton("Remover");

            {
                button.setForeground(java.awt.Color.RED);
                button.setFont(button.getFont().deriveFont(java.awt.Font.BOLD));
            }

            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column
            ) {
                return button;
            }
        }
        );

        // Editor do botão
        tableBudgetItems.getColumnModel().getColumn(5).setCellEditor(
                new javax.swing.DefaultCellEditor(new javax.swing.JCheckBox()) {
            private final javax.swing.JButton button = new javax.swing.JButton("Remover");

            @Override
            public java.awt.Component getTableCellEditorComponent(
                    javax.swing.JTable table, Object value, boolean isSelected,
                    int row, int column
            ) {
                button.setForeground(java.awt.Color.RED);
                button.setFont(button.getFont().deriveFont(java.awt.Font.BOLD));

                // Remove todos os listeners anteriores
                for (java.awt.event.ActionListener al : button.getActionListeners()) {
                    button.removeActionListener(al);
                }

                // Adiciona o listener para remover
                button.addActionListener(e -> {
                    int confirm = javax.swing.JOptionPane.showConfirmDialog(
                            table,
                            "Deseja realmente remover este item?",
                            "Confirmar Remoção",
                            javax.swing.JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                        controller.removerItem(row);
                        stopCellEditing();
                    }
                });

                return button;
            }
        }
        );

        // Adiciona MouseListener para detectar cliques
        tableBudgetItems.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = tableBudgetItems.columnAtPoint(evt.getPoint());
                int row = tableBudgetItems.rowAtPoint(evt.getPoint());

                // Se clicou na coluna Ação (5)
                if (column == 5 && row >= 0) {
                    int confirm = javax.swing.JOptionPane.showConfirmDialog(
                            tableBudgetItems,
                            "Deseja realmente remover este item?",
                            "Confirmar Remoção",
                            javax.swing.JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                        controller.removerItem(row);
                    }
                }
            }
        });
    }

    public void getFrameData() {
        customerData = frame.getCustomerData();
        frame.dispose();
        labelCustomerName.setText("Cliente: " + customerData[1]);
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelCustomerName = new javax.swing.JLabel();
        buttonSelectCustomerPhysical = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemsToSelectTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableBudgetItems = new javax.swing.JTable();
        labelTotalGeral = new javax.swing.JLabel();
        saveBudget = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleção de produtos"));

        labelCustomerName.setText("Cliente: Não Selecionado");

        buttonSelectCustomerPhysical.setText("Selecionar Cliente");
        buttonSelectCustomerPhysical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSelectCustomerPhysicalActionPerformed(evt);
            }
        });

        itemsToSelectTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Preco", "Preço Promocional", "Quantidade", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(itemsToSelectTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelCustomerName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonSelectCustomerPhysical)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCustomerName)
                    .addComponent(buttonSelectCustomerPhysical))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Produtos Selecionados"));

        tableBudgetItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Preço de Venda", "Quantidade", "Subtotal", "Ação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableBudgetItems);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelTotalGeral.setText("Total: R$ 0,00");

        saveBudget.setText("Salvar Orçamento");
        saveBudget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBudgetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTotalGeral)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBudget))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBudget)
                    .addComponent(labelTotalGeral))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSelectCustomerPhysicalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSelectCustomerPhysicalActionPerformed
        frame = new SelectCustomerPhysical(this);
        frame.setVisible(true);

    }//GEN-LAST:event_buttonSelectCustomerPhysicalActionPerformed

    private void saveBudgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBudgetActionPerformed
        if (customerData == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecione um cliente para vincular ao orçamento!",
                    "Nenhum cliente selecionado",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        ArrayList<CartItem> items = controller.getItems();

        if (items.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto para vincular ao orçamento!",
                    "Nenhum produto selecionado",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean response = controller.saveBudget(customerData);

        if (!response) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar o orçamento!",
                    "Erro",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Orçamento salvo com sucesso!",
                "Sucesso",
                javax.swing.JOptionPane.DEFAULT_OPTION
        );
        controller.cleanFields();
    }//GEN-LAST:event_saveBudgetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonSelectCustomerPhysical;
    public javax.swing.JTable itemsToSelectTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel labelCustomerName;
    public javax.swing.JLabel labelTotalGeral;
    private javax.swing.JButton saveBudget;
    public javax.swing.JTable tableBudgetItems;
    // End of variables declaration//GEN-END:variables
}
