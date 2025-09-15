// Paquete de vistas
package vallegrande.edu.pe.view;

import vallegrande.edu.pe.controller.EstudianteController;
import vallegrande.edu.pe.model.Estudiante;

import javax.swing.*;                       // componentes Swing
import javax.swing.event.DocumentEvent;     // eventos al cambiar texto
import javax.swing.event.DocumentListener;  // escucha cambios del campo de búsqueda
import javax.swing.table.DefaultTableModel; // modelo de tabla
import java.awt.*;                          // layouts y colores
import java.util.List;                      // listas

// Vista Swing para gestionar estudiantes (CRUD con búsqueda, edición y persistencia)
public class EstudianteCrudView extends JFrame {

    // Referencia al controlador que maneja la lógica y persistencia
    private final EstudianteController controller;
    // Modelo de la tabla que muestra los estudiantes
    private final DefaultTableModel tableModel;
    // Campo de búsqueda para filtrar la tabla
    private final JTextField txtSearch = new JTextField(20);
    // Tabla que presenta los datos
    private final JTable table;

    // Constructor que recibe el controlador y construye la interfaz
    public EstudianteCrudView(EstudianteController controller) {
        this.controller = controller; // asigna el controlador

        // Configuración básica de la ventana
        setTitle("Gestión de Estudiantes");                   // título de la ventana
        setSize(900, 500);                                    // tamaño inicial
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    // al cerrar solo esta ventana
        setLocationRelativeTo(null);                          // centrar en pantalla
        setLayout(new BorderLayout(8, 8));                    // layout con separación

        // Inicializa el modelo de la tabla con columnas
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Correo", "Curso"}, 0);
        table = new JTable(tableModel);                       // crea la tabla con el modelo
        table.setRowHeight(26);                               // altura de filas
        table.setFont(new Font("SansSerif", Font.PLAIN, 14)); // fuente de contenido

        // Panel superior: contiene búsqueda y botones
        JPanel topPanel = new JPanel(new BorderLayout(8, 8));
        // Subpanel izquierdo: campo de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Buscar:"));               // etiqueta
        searchPanel.add(txtSearch);                           // campo de búsqueda

        // Añadimos un listener al documento del campo de búsqueda para filtrar en tiempo real
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            // Método invocado cuando cambia el documento (insert)
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }
            // Método invocado cuando cambia el documento (remove)
            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }
            // Método invocado cuando cambia el documento (change)
            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }
        });

        // Subpanel derecho: botones de acción (Agregar, Editar, Eliminar)
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("➕ Agregar");         // botón agregar
        JButton editButton = new JButton("✏️ Editar");        // botón editar
        JButton deleteButton = new JButton("🗑 Eliminar");    // botón eliminar

        // Acción del botón Agregar: abre un diálogo para pedir datos y valida
        addButton.addActionListener(e -> {
            // Creamos los campos del formulario
            JTextField nombreField = new JTextField();
            JTextField correoField = new JTextField();
            JTextField cursoField = new JTextField();

            // Creamos un panel con GridLayout para mantener orden
            JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
            form.add(new JLabel("Nombre:")); form.add(nombreField);
            form.add(new JLabel("Correo:")); form.add(correoField);
            form.add(new JLabel("Curso:"));  form.add(cursoField);

            // Mostramos dialogo con el panel
            int option = JOptionPane.showConfirmDialog(this, form, "Nuevo Estudiante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            // Si el usuario presionó OK, validamos y agregamos
            if (option == JOptionPane.OK_OPTION) {
                String nombre = nombreField.getText().trim();
                String correo = correoField.getText().trim();
                String curso = cursoField.getText().trim();

                // Validación básica: no vacíos y formato básico de correo
                if (nombre.isEmpty() || correo.isEmpty() || curso.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!isValidEmail(correo)) {
                    JOptionPane.showMessageDialog(this, "Ingrese un correo válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Verificar si ya existe el correo
                if (controller.existeCorreo(correo)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un estudiante con ese correo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }