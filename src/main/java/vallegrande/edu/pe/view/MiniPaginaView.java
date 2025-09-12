// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.view;

// Importa el controlador de usuarios para manejar la lógica
import vallegrande.edu.pe.controller.UsuarioController;

// Importa clases de Swing y AWT necesarias para la interfaz gráfica
import javax.swing.*;
import java.awt.*;

// Clase que representa la ventana principal tipo "mini página web" del instituto
public class MiniPaginaView extends JFrame {

    // Controlador de usuarios para gestionar acciones relacionadas con usuarios
    private final UsuarioController controller;

    // Constructor que recibe el controlador de usuarios
    public MiniPaginaView(UsuarioController controller) {
        this.controller = controller;   // Asigna el controlador recibido
        initUI();                        // Inicializa la interfaz gráfica
    }

    // Método que construye toda la interfaz de usuario
    private void initUI() {
        // Configuración básica de la ventana
        setTitle("Instituto Valle Grande - Portal Principal"); // Título de la ventana
        setSize(950, 550);                                     // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Cierra la aplicación al cerrar la ventana
        setLocationRelativeTo(null);                          // Centra la ventana en la pantalla
        setLayout(new BorderLayout());                        // Layout principal con zonas norte, sur, este, oeste y centro

        // ===============================
        // Encabezado tipo banner
        // ===============================
        JPanel header = new JPanel();                          // Panel superior
        header.setBackground(new Color(0, 70, 140));          // Color de fondo azul corporativo
        header.setPreferredSize(new Dimension(getWidth(), 90));// Alto del banner
        JLabel title = new JLabel("Bienvenido al Portal del Instituto Valle Grande"); // Texto del encabezado
        title.setFont(new Font("SansSerif", Font.BOLD, 26));  // Fuente y tamaño
        title.setForeground(Color.WHITE);                     // Color del texto
        header.add(title);                                    // Añade el título al panel header
        add(header, BorderLayout.NORTH);                      // Añade el header al norte de la ventana

        // ===============================
        // Panel central
        // ===============================
        JPanel centerPanel = new JPanel(new GridLayout(2, 1)); // Panel central con dos filas
        centerPanel.setBackground(Color.WHITE);               // Color de fondo blanco

        // -------------------------------
        // Texto informativo
        // -------------------------------
        JPanel infoPanel = new JPanel();                      // Panel para información
        infoPanel.setBackground(Color.WHITE);                // Fondo blanco
        JLabel infoLabel = new JLabel("<html><center><h2>Gestión Académica y Administrativa</h2>" +
                "Administre usuarios, docentes y estudiantes desde un mismo lugar<br>" +
                "Sistema institucional moderno y seguro</center></html>"); // Texto con HTML para formato
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16)); // Fuente
        infoPanel.add(infoLabel);                              // Añade el texto al panel infoPanel

        // -------------------------------
        // Panel de botones tipo menú
        // -------------------------------
        JPanel menuPanel = new JPanel();                       // Panel para los botones de acción
        menuPanel.setBackground(new Color(245, 245, 245));    // Fondo gris claro

        // Botón Administrar Usuarios
        JButton btnUsuarios = crearBotonMenu("👤 Administrar Usuarios", new Color(0, 120, 215));
        btnUsuarios.addActionListener(e -> new UsuarioCrudView(controller).setVisible(true)); // Abre el CRUD al hacer clic

        // Botón Administrar Docentes
        JButton btnDocentes = crearBotonMenu("📚 Administrar Docentes", new Color(34, 167, 240));

        // Botón Administrar Estudiantes
        JButton btnEstudiantes = crearBotonMenu("🎓 Administrar Estudiantes", new Color(34, 153, 84));

        // Añade los botones al panel de menú
        menuPanel.add(btnUsuarios);
        menuPanel.add(btnDocentes);
        menuPanel.add(btnEstudiantes);

        // Añade los subpaneles al panel central
        centerPanel.add(infoPanel);                            // Primera fila: info
        centerPanel.add(menuPanel);                            // Segunda fila: botones
        add(centerPanel, BorderLayout.CENTER);                // Añade el panel central a la ventana

        // ===============================
        // Footer
        // ===============================
        JPanel footer = new JPanel();                          // Panel inferior
        footer.setBackground(new Color(230, 230, 230));       // Fondo gris claro
        JLabel lblFooter = new JLabel("© 2025 Instituto Valle Grande - Todos los derechos reservados"); // Texto
        lblFooter.setFont(new Font("SansSerif", Font.ITALIC, 12)); // Fuente en cursiva
        footer.add(lblFooter);                                 // Añade el texto al footer
        add(footer, BorderLayout.SOUTH);                      // Añade el footer al sur de la ventana
    }

    // Método auxiliar para crear botones tipo menú con estilo
    private JButton crearBotonMenu(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);                     // Crea un nuevo botón
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));    // Fuente del texto
        btn.setForeground(Color.WHITE);                       // Color del texto blanco
        btn.setBackground(colorFondo);                        // Color de fondo del botón
        btn.setFocusPainted(false);                            // Quita el contorno al seleccionar
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));        // Cambia el cursor al pasar sobre el botón
        btn.setPreferredSize(new Dimension(250, 60));         // Tamaño fijo del botón
        return btn;                                           // Devuelve el botón
    }
}


