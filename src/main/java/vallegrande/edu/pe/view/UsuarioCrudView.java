// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;


// Importa el controlador de usuarios y la clase Usuario
import vallegrande.edu.pe.controller.UsuarioController;
import vallegrande.edu.pe.model.Usuario;


// Importa clases de Swing y AWT necesarias para la interfaz gráfica y tablas
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


// Clase que representa la ventana CRUD de usuarios
public class UsuarioCrudView extends JFrame {


    // Controlador de usuarios para manejar la lógica
    private final UsuarioController controller;


    // Modelo de tabla para mostrar los datos de los usuarios
    private final DefaultTableModel tableModel;


    // Constructor que recibe el controlador
    public UsuarioCrudView(UsuarioController controller) {
        this.controller = controller;  // Asigna el controlador recibido


        // Configuración básica de la ventana
        setTitle("Gestión de Usuarios");                  // Título de la ventana
        setSize(700, 400);                                // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Al cerrar, solo se cierra esta ventana
        setLocationRelativeTo(null);                       // Centra la ventana en la pantalla


        // Modelo de tabla con columnas: Nombre, Correo, Rol
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Rol"}, 0);


        // Tabla que mostrará los usuarios
        JTable table = new JTable(tableModel);
        table.setRowHeight(25);                             // Altura de cada fila
        table.setFont(new Font("SansSerif", Font.PLAIN, 14)); // Fuente de las celdas


        // Scroll para la tabla
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);              // Añade la tabla con scroll al centro


        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("➕ Agregar");      // Botón para agregar usuario
        JButton deleteButton = new JButton("🗑 Eliminar");  // Botón para eliminar usuario


        // Acción del botón Agregar
        addButton.addActionListener(e -> {
            // Solicita los datos del nuevo usuario mediante diálogos
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            String correo = JOptionPane.showInputDialog(this, "Correo:");
            String rol = JOptionPane.showInputDialog(this, "Rol (Administrador/Docente/Estudiante):");
            // Si los datos no son nulos, agrega el usuario y recarga la tabla
            if (nombre != null && correo != null && rol != null) {
                controller.addUsuario(new Usuario(nombre, correo, rol));
                cargarUsuarios();
            }
        });


        // Acción del botón Eliminar
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();                 // Obtiene la fila seleccionada
            if (row >= 0) {                                   // Verifica que haya una fila seleccionada
                controller.deleteUsuario(row);               // Elimina el usuario correspondiente
                cargarUsuarios();                             // Recarga la tabla
            }
        });


        // Añade los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);               // Coloca el panel de botones al sur


        // Carga inicialmente los usuarios en la tabla
        cargarUsuarios();
    }


    // Método que carga todos los usuarios del controlador en la tabla
    private void cargarUsuarios() {
        tableModel.setRowCount(0);                          // Limpia la tabla
        for (Usuario u : controller.getUsuarios()) {        // Recorre cada usuario
            tableModel.addRow(new Object[]{u.getNombre(), u.getCorreo(), u.getRol()}); // Agrega fila
        }
    }
}





