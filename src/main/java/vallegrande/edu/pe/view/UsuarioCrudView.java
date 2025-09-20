// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UsuarioCrudView extends JFrame {

    private final UsuarioController controller;
    private final DefaultTableModel tableModel;

    public UsuarioCrudView(UsuarioController controller) {
        this.controller = controller;

        setTitle("Gestión de Usuarios");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de tabla con columnas: Nombre, Correo, Rol
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Rol"}, 0);

        JTable table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Agregar");
        JButton deleteButton = new JButton("🗑 Eliminar");

        // === Nuevo formulario con JCheckBox para el rol ===
        addButton.addActionListener(e -> {
            JTextField nombreField = new JTextField();
            JTextField correoField = new JTextField();

            JCheckBox adminCheck = new JCheckBox("Administrador");
            JCheckBox docenteCheck = new JCheckBox("Vendedor");
            JCheckBox estudianteCheck = new JCheckBox("Cliente");

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre:"));
            panel.add(nombreField);
            panel.add(new JLabel("Correo:"));
            panel.add(correoField);
            panel.add(new JLabel("Rol:"));
            panel.add(adminCheck);
            panel.add(docenteCheck);
            panel.add(estudianteCheck);

            int result = JOptionPane.showConfirmDialog(this, panel,
                    "Nuevo Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String rol = "";
                if (adminCheck.isSelected()) rol = "Mujer";
                else if (docenteCheck.isSelected()) rol = "Hombre";
                else if (estudianteCheck.isSelected()) rol = "Niño/a";

                controller.addUsuario(new Usuario(nombreField.getText(), correoField.getText(), rol));
                cargarUsuarios();
            }
        });

        // Acción del botón Eliminar
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                controller.deleteUsuario(row);
                cargarUsuarios();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        tableModel.setRowCount(0);
        for (Usuario u : controller.getUsuarios()) {
            tableModel.addRow(new Object[]{u.getNombre(), u.getCorreo(), u.getRol()});
        }
    }
}
