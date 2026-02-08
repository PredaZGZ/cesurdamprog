
import java.util.Scanner;
import java.util.List;

public class Main {
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        precargarDatos();
        mostrarMenu();
    }

    private static void precargarDatos() {
        System.out.println("Cargando datos de prueba...");

        // 6 Libros
        biblioteca.altaLibro("978-1", "El Quijote", "Cervantes", 1605);
        biblioteca.altaLibro("978-2", "1984", "Orwell", 1949);
        biblioteca.altaLibro("978-3", "Cien Años de Soledad", "García Márquez", 1967);
        biblioteca.altaLibro("978-4", "Dune", "Frank Herbert", 1965);
        biblioteca.altaLibro("978-5", "El Hobbit", "Tolkien", 1937);
        biblioteca.altaLibro("978-6", "Fundación", "Asimov", 1951);

        // 3 Socios
        biblioteca.altaSocio("S001", "Ana García", "ana@example.com");
        biblioteca.altaSocio("S002", "Luis Pérez", "luis@example.com");
        biblioteca.altaSocio("S003", "Maria López", "maria@example.com");

        // 2 Préstamos activos
        try {
            biblioteca.realizarPrestamo("S001", "978-1"); // Ana se lleva El Quijote
            biblioteca.realizarPrestamo("S002", "978-4"); // Luis se lleva Dune
        } catch (Exception e) {
            System.out.println("Error en precarga: " + e.getMessage());
        }
        System.out.println("Datos cargados correctamente.\n");
    }

    private static void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("=== GESTIÓN DE BIBLIOTECA ===");
            System.out.println("1. Alta de Libro");
            System.out.println("2. Alta de Socio");
            System.out.println("3. Prestar Libro");
            System.out.println("4. Devolver Libro");
            System.out.println("5. Listar Libros Disponibles");
            System.out.println("6. Listar Préstamos Activos");
            System.out.println("7. Listar Préstamos de un Socio");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine(); // Consumir salto de línea
            } catch (Exception e) {
                opcion = -1;
                sc.nextLine(); // Limpiar buffer
            }

            switch (opcion) {
                case 1 -> altaLibro();
                case 2 -> altaSocio();
                case 3 -> prestarLibro();
                case 4 -> devolverLibro();
                case 5 -> listarLibros();
                case 6 -> listarPrestamosActivos();
                case 7 -> listarPrestamosSocio();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    private static void altaLibro() {
        try {
            System.out.print("ISBN: ");
            String isbn = sc.nextLine();

            System.out.print("Título: ");
            String titulo = sc.nextLine();

            System.out.print("Autor: ");
            String autor = sc.nextLine();

            System.out.print("Año: ");
            int year = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            biblioteca.altaLibro(isbn, titulo, autor, year);
            System.out.println("Libro dado de alta correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            sc.nextLine(); // Limpiar buffer en caso de error de entrada
        }
    }

    private static void altaSocio() {
        try {
            System.out.print("ID Socio: ");
            String id = sc.nextLine();

            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            biblioteca.altaSocio(id, nombre, email);
            System.out.println("Socio dado de alta correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void prestarLibro() {
        try {
            System.out.print("ID Socio: ");
            String idSocio = sc.nextLine();

            System.out.print("ISBN Libro: ");
            String isbn = sc.nextLine();

            biblioteca.realizarPrestamo(idSocio, isbn);
            System.out.println("Préstamo realizado con éxito.");
        } catch (Exception e) {
            System.out.println("Error al realizar préstamo: " + e.getMessage());
        }
    }

    private static void devolverLibro() {
        try {
            System.out.print("ID Socio: ");
            String idSocio = sc.nextLine();

            System.out.print("ISBN Libro: ");
            String isbn = sc.nextLine();

            biblioteca.devolverLibro(isbn, idSocio);
            System.out.println("Devolución realizada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al devolver libro: " + e.getMessage());
        }
    }

    private static void listarLibros() {
        List<Libro> libros = biblioteca.getLibrosDisponibles();
        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        } else {
            System.out.println("--- Libros Disponibles ---");
            for (Libro l : libros) {
                System.out.println(l);
            }
        }
    }

    private static void listarPrestamosActivos() {
        List<Prestamo> prestamos = biblioteca.getPrestamosActivos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos activos.");
        } else {
            System.out.println("--- Préstamos Activos ---");
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }

    private static void listarPrestamosSocio() {
        System.out.print("ID Socio: ");
        String idSocio = sc.nextLine();
        List<Prestamo> prestamos = biblioteca.getPrestamosPorSocio(idSocio);

        if (prestamos.isEmpty()) {
            System.out.println("Este socio no tiene préstamos registrados.");
        } else {
            System.out.println("--- Historial de Préstamos del Socio " + idSocio + " ---");
            for (Prestamo p : prestamos) {
                System.out.println(p);
            }
        }
    }
}
