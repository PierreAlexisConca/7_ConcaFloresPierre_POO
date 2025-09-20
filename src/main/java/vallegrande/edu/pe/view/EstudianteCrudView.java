// Paquete de vistas
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.CompraController;
import vallegrande.edu.pe.model.Compra;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Vista Swing para gestionar compras (CRUD con búsqueda, edición y persistencia)
class CompraCrudView extends JFrame {

    private final CompraController controller;
    private final DefaultTableModel tableModel;
    private final JTextField txtSearch = new JTextField(20);
    private final JTable table;

    public CompraCrudView(CompraController controller) {
        this.controller = controller;

        setTitle("Gestión de Compras");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // columnas: producto, tipo, fecha
        tableModel = new DefaultTableModel(new Object[]{"Producto", "Tipo de pago", "Fecha de compra"}, 0);
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Buscar:"));
        searchPanel.add(txtSearch);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("➕ Agregar");
        JButton editButton = new JButton("✏️ Editar");
        JButton deleteButton = new JButton("🗑 Eliminar");

        // Agregar compra
        addButton.addActionListener(e -> {
            JTextField productoField = new JTextField();
            JTextField tipoField = new JTextField();
            JTextField fechaField = new JTextField("yyyy-mm-dd");

            JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
            form.add(new JLabel("Producto:")); form.add(productoField);
            form.add(new JLabel("Tipo:")); form.add(tipoField);
            form.add(new JLabel("Fecha de compra:")); form.add(fechaField);

            int option = JOptionPane.showConfirmDialog(this, form, "Nueva Compra", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String producto = productoField.getText().trim();
                String tipo = tipoField.getText().trim();
                String fecha = fechaField.getText().trim();

                if (producto.isEmpty() || tipo.isEmpty() || fecha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                controller.addCompra(producto, tipo, fecha);
                cargarTodos();
            }
        });

        // Editar compra
        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una compra para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String currentProducto = (String) table.getValueAt(row, 0);
            String currentTipo = (String) table.getValueAt(row, 1);
            String currentFecha = (String) table.getValueAt(row, 2);

            JTextField productoField = new JTextField(currentProducto);
            JTextField tipoField = new JTextField(currentTipo);
            JTextField fechaField = new JTextField(currentFecha);

            JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
            form.add(new JLabel("Producto:")); form.add(productoField);
            form.add(new JLabel("Tipo:")); form.add(tipoField);
            form.add(new JLabel("Fecha de compra:")); form.add(fechaField);

            int option = JOptionPane.showConfirmDialog(this, form, "Editar Compra", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String producto = productoField.getText().trim();
                String tipo = tipoField.getText().trim();
                String fecha = fechaField.getText().trim();

                if (producto.isEmpty() || tipo.isEmpty() || fecha.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Compra actualizada = new Compra(producto, tipo, fecha);
                controller.updateCompra(row, actualizada);
                cargarTodos();
            }
        });

        // Eliminar compra
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una compra para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar compra seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deleteCompra(row);
                cargarTodos();
            }
        });

        actionsPanel.add(addButton);
        actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(actionsPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRefresh = new JButton("🔄 Recargar");
        btnRefresh.addActionListener(e -> cargarTodos());
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        cargarTodos();
    }

    private void filtrar() {
        String text = txtSearch.getText().trim().toLowerCase();
        if (text.isEmpty()) {
            cargarTodos();
            return;
        }
        List<Compra> todas = controller.listarCompras();
        tableModel.setRowCount(0);
        for (Compra c : todas) {
            if (c.getProducto().toLowerCase().contains(text) || c.getTipo().toLowerCase().contains(text)) {
                tableModel.addRow(new Object[]{c.getProducto(), c.getTipo(), c.getFechaCompra()});
            }
        }
    }

    private void cargarTodos() {
        tableModel.setRowCount(0);
        for (Compra c : controller.listarCompras()) {
            tableModel.addRow(new Object[]{c.getProducto(), c.getTipo(), c.getFechaCompra()});
        }
    }
}
