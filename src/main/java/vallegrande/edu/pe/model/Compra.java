// Paquete donde se ubica la clase
package vallegrande.edu.pe.model;

// Clase que representa el modelo Estudiante
public class Compra {

    private String nombre;
    private String correo;
    private String curso;
    private String producto;
    private String tipo;
    private String fechaCompra;

    public Compra(String nombre, String correo, String curso) {
        this.nombre = nombre;
        this.correo = correo;
        this.curso = curso;
    }

    public Compra(String nombre, String correo, String producto, String metodoPago, String fechaCompra) {
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Object fechaCompra) {
        this.fechaCompra = (String) fechaCompra;
    }

    public String getMetodoPago() {
        return "";
    }
}
