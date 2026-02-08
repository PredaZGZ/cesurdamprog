
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int year;
    private boolean prestado;

    public Libro(String isbn, String titulo, String autor, int year) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.year = year;
        this.prestado = false; // Por defecto disponible
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getYear() {
        return year;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void prestar() {
        if (this.prestado) {
            throw new IllegalStateException("El libro ya está prestado.");
        }
        this.prestado = true;
    }

    public void devolver() {
        this.prestado = false;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "ISBN='" + isbn + "'" +
                ", Título='" + titulo + "'" +
                ", Autor='" + autor + "'" +
                ", Año=" + year +
                ", Prestado=" + prestado +
                '}';
    }
}
