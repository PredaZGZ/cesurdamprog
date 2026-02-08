import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Libro> libros;
    private List<Socio> socios;
    private List<Prestamo> prestamos;
    private int contadorPrestamos;

    public Biblioteca() {
        this.libros = new ArrayList<>();
        this.socios = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.contadorPrestamos = 1;
    }

    // --- GESTIÓN DE LIBROS ---

    public void altaLibro(String isbn, String titulo, String autor, int year) {
        if (buscarLibro(isbn) != null) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + isbn);
        }
        libros.add(new Libro(isbn, titulo, autor, year));
    }

    public Libro buscarLibro(String isbn) {
        for (Libro l : libros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                return l;
            }
        }
        return null;
    }

    public List<Libro> getLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        for (Libro l : libros) {
            if (!l.isPrestado()) {
                disponibles.add(l);
            }
        }
        return disponibles;
    }

    // --- GESTIÓN DE SOCIOS ---

    public void altaSocio(String idSocio, String nombre, String email) {
        if (buscarSocio(idSocio) != null) {
            throw new IllegalArgumentException("Ya existe un socio con el ID: " + idSocio);
        }
        socios.add(new Socio(idSocio, nombre, email));
    }

    public Socio buscarSocio(String idSocio) {
        for (Socio s : socios) {
            if (s.getIdSocio().equalsIgnoreCase(idSocio)) {
                return s;
            }
        }
        return null;
    }

    // --- GESTIÓN DE PRÉSTAMOS ---

    public void realizarPrestamo(String idSocio, String isbn) {
        Socio socio = buscarSocio(idSocio);
        if (socio == null) {
            throw new IllegalArgumentException("Socio no encontrado.");
        }

        Libro libro = buscarLibro(isbn);
        if (libro == null) {
            throw new IllegalArgumentException("Libro no encontrado.");
        }

        if (libro.isPrestado()) {
            throw new IllegalStateException("El libro ya está prestado.");
        }

        if (!socio.puedePedirPrestamo()) {
            throw new IllegalStateException("El socio ha alcanzado su límite de préstamos.");
        }

        // Crear préstamo
        String idPrestamo = "P" + contadorPrestamos++;
        Prestamo prestamo = new Prestamo(idPrestamo, socio, libro);
        prestamos.add(prestamo);

        // Actualizar estados
        libro.prestar();
        socio.incrementarPrestamos();
    }

    public void devolverLibro(String isbn, String idSocio) {
        // Buscar el préstamo activo correspondiente
        Prestamo prestamoAEncontrar = null;
        for (Prestamo p : prestamos) {
            if (p.estaActivo() &&
                    p.getLibro().getIsbn().equalsIgnoreCase(isbn) &&
                    p.getSocio().getIdSocio().equalsIgnoreCase(idSocio)) {
                prestamoAEncontrar = p;
                break;
            }
        }

        if (prestamoAEncontrar == null) {
            throw new IllegalArgumentException("No se encontró un préstamo activo para este libro y socio.");
        }

        // Cerrar préstamo y actualizar estados
        prestamoAEncontrar.cerrarPrestamo();
        prestamoAEncontrar.getLibro().devolver();
        prestamoAEncontrar.getSocio().decrementarPrestamos();
    }

    public List<Prestamo> getPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.estaActivo()) {
                activos.add(p);
            }
        }
        return activos;
    }

    public List<Prestamo> getPrestamosPorSocio(String idSocio) {
        List<Prestamo> historial = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getSocio().getIdSocio().equalsIgnoreCase(idSocio)) {
                historial.add(p);
            }
        }
        return historial;
    }
}
