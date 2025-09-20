// Paquete del controlador
package vallegrande.edu.pe.controller;

// Importa el modelo Compra y el repositorio en memoria
import vallegrande.edu.pe.model.Compra;
import vallegrande.edu.pe.model.InMemoryCompraRepository;

import java.util.List;

// Controlador que actúa como capa intermedia entre la vista y el repositorio
public class CompraController {

    // Repositorio interno (en memoria + persistencia simple)
    private final InMemoryCompraRepository repository;

    // Constructor que crea el repositorio
    public CompraController() {
        this.repository = new InMemoryCompraRepository(); // inicializa y carga datos desde archivo
    }

    // Retorna la lista completa de compras
    public List<Compra> listarCompras() {
        return repository.findAll();
    }

    // Agrega una compra (producto, tipo, fecha)
    public void addCompra(String producto, String tipo, String fechaCompra) {
        repository.add(new Compra(producto, tipo, fechaCompra));
        repository.saveToFile(); // persistencia en disco
    }

    // Actualiza una compra existente por índice
    public void updateCompra(int index, Compra compra) {
        repository.update(index, compra);
        repository.saveToFile();
    }

    // Elimina una compra por índice
    public void deleteCompra(int index) {
        repository.remove(index);
        repository.saveToFile();
    }
}
