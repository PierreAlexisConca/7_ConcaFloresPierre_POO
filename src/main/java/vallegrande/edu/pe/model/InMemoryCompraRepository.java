// Paquete del repositorio en memoria
package vallegrande.edu.pe.model;

import java.io.BufferedReader;       // para leer archivo
import java.io.BufferedWriter;       // para escribir archivo
import java.io.IOException;          // excepción IO
import java.nio.charset.StandardCharsets; // codificación
import java.nio.file.Files;          // utilidades de ficheros
import java.nio.file.Path;           // representación de rutas
import java.nio.file.Paths;          // creación de rutas
import java.util.ArrayList;          // implementación de lista
import java.util.List;               // interfaz List

// Repositorio que guarda compras en memoria y persiste en CSV sencillo
public class InMemoryCompraRepository {

    // Lista que contiene las compras en memoria
    private final List<Compra> compras = new ArrayList<>();

    // Ruta al archivo donde se guardarán los datos (carpeta "data/compras.csv")
    private final Path filePath = Paths.get("data", "compras.csv");

    // Delimitador simple para el CSV
    private static final String DEL = ";";

    // Constructor: inicializa la lista y carga datos desde disco si existen
    public InMemoryCompraRepository() {
        loadFromFile();
        // Si por alguna razón la lista está vacía, agregamos ejemplos iniciales
        if (compras.isEmpty()) {
            compras.add(new Compra("Juan", "juan@correo.com", "Polo", "Efectivo", "2025-09-20"));
            compras.add(new Compra("Ana", "ana@correo.com", "Casaca", "Tarjeta", "2025-09-19"));
            saveToFile();
        }
    }

    // Retorna una copia de la lista de compras
    public List<Compra> findAll() {
        return new ArrayList<>(compras);
    }

    // Agrega una compra a la lista
    public void add(Compra compra) {
        compras.add(compra);
    }

    // Actualiza una compra en la posición indicada
    public void update(int index, Compra compra) {
        if (index >= 0 && index < compras.size()) {
            compras.set(index, compra);
        }
    }

    // Elimina una compra por índice
    public void remove(int index) {
        if (index >= 0 && index < compras.size()) {
            compras.remove(index);
        }
    }

    // Guarda la lista de compras en un archivo CSV
    public void saveToFile() {
        try {
            // Asegura que la carpeta exista
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            // Escribe el archivo usando un BufferedWriter
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                // Itera cada compra y escribe una línea: nombre;correo;producto;metodoPago;fechaCompra
                for (Compra c : compras) {
                    String line = escape(c.getNombre()) + DEL +
                            escape(c.getCorreo()) + DEL +
                            escape(c.getProducto()) + DEL +
                            escape(c.getMetodoPago()) + DEL +
                            escape(c.getFechaCompra());
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            System.err.println("Error guardando compras: " + ex.getMessage());
        }
    }

    // Carga las compras desde el archivo CSV, si existe
    private void loadFromFile() {
        if (!Files.exists(filePath)) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(DEL, -1);
                if (parts.length >= 5) {
                    String nombre = unescape(parts[0]);
                    String correo = unescape(parts[1]);
                    String producto = unescape(parts[2]);
                    String metodoPago = unescape(parts[3]);
                    String fechaCompra = unescape(parts[4]);
                    compras.add(new Compra(nombre, correo, producto, metodoPago, fechaCompra));
                }
            }
        } catch (IOException ex) {
            System.err.println("Error cargando compras: " + ex.getMessage());
        }
    }

    // Escapa secuencias simples (para exportación CSV)
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\n", " ").replace("\r", " ");
    }

    // Reversa de escape
    private String unescape(String s) {
        return s == null ? "" : s;
    }
}
