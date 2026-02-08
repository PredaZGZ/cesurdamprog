public class Socio {
    private String idSocio;
    private String nombre;
    private int prestamosActivos;
    private String email;
    private static final int MAX_PRESTAMOS = 3; // Límite de préstamos simultáneos, static para acceder sin instanciar
                                                // la clase

    public Socio(String idSocio, String nombre, String email) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (!esEmailValido(email)) {
            throw new IllegalArgumentException("Formato de email inválido.");
        }
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.email = email;
        this.prestamosActivos = 0;
    }

    private boolean esEmailValido(String email) {
        return email != null && email.contains("@");
    }

    public String getIdSocio() {
        return idSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public int getPrestamosActivos() {
        return prestamosActivos;
    }

    public int getMaxPrestamos() {
        return MAX_PRESTAMOS;
    }

    public boolean puedePedirPrestamo() {
        return prestamosActivos < MAX_PRESTAMOS;
    }

    public void incrementarPrestamos() {
        if (!puedePedirPrestamo()) {
            throw new IllegalStateException("El socio ha alcanzado el límite de préstamos.");
        }
        prestamosActivos++;
    }

    public void decrementarPrestamos() {
        if (prestamosActivos > 0) {
            prestamosActivos--;
        }
    }

    @Override
    public String toString() {
        return "Socio{" +
                "ID='" + idSocio + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", Email='" + email + '\'' +
                ", Préstamos Activos=" + prestamosActivos +
                '}';
    }
}
