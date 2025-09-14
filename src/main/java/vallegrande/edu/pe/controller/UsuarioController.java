// Definición del paquete donde se encuentra esta clase
package vallegrande.edu.pe.controller;

// Importa la clase Usuario del paquete model
import vallegrande.edu.pe.model.Usuario;

// Importa las clases necesarias para usar listas dinámicas
import java.util.ArrayList;
import java.util.List;

// Definición de la clase UsuarioController que maneja la lógica de usuarios
public class UsuarioController {

    // Lista privada que almacena todos los usuarios
    private final List<Usuario> usuarios;

    // Constructor de la clase
    public UsuarioController() {
        // Inicializa la lista de usuarios como un ArrayList vacío
        usuarios = new ArrayList<>();
        // Agrega datos de ejemplo a la lista de usuarios con sus roles correspondientes
        usuarios.add(new Usuario("Valery Chumpitaz", "valery@correo.com", "Administrador"));
        usuarios.add(new Usuario("Juan Pérez", "juan@correo.com", "Docente"));
        usuarios.add(new Usuario("María López", "maria@correo.com", "Estudiante"));
    }

    // Método que retorna la lista completa de usuarios
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Método para agregar un nuevo usuario a la lista
    public void addUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Método para eliminar un usuario de la lista según su índice
    public void deleteUsuario(int index) {
        // Verifica que el índice sea válido antes de eliminar
        if (index >= 0 && index < usuarios.size()) {
            usuarios.remove(index);
        }
    }

    // Método para actualizar un usuario existente en la lista según su índice
    public void updateUsuario(int index, Usuario usuario) {
        // Verifica que el índice sea válido antes de actualizar
        if (index >= 0 && index < usuarios.size()) {
            usuarios.set(index, usuario);
        }
    }
}

