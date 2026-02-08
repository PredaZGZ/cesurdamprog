public class Prestamo {
    private String idPrestamo;
    private Socio socio;
    private Libro libro;
    private String fechaInicio;
    private String fechaDevolucion;

    public Prestamo(String idPrestamo, Socio socio, Libro libro) {
        this.idPrestamo = idPrestamo;
        this.socio = socio;
        this.libro = libro;
        this.fechaInicio = "Hoy"; // Simplificado
        this.fechaDevolucion = null; // null indica que el préstamo está activo
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public Socio getSocio() {
        return socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public void cerrarPrestamo() {
        this.fechaDevolucion = "Hoy"; // Simplificado
    }

    @Override
    public String toString() {
        return "Préstamo{" +
                "ID='" + idPrestamo + "'" +
                ", Socio=" + socio.getNombre() +
                ", Libro=" + libro.getTitulo() +
                ", Inicio='" + fechaInicio + "'" +
                ", Fin='" + (fechaDevolucion != null ? fechaDevolucion : "Activo") + "'" +
                '}';
    }
}
