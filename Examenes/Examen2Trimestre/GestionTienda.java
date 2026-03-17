import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionTienda {

    private static final String ARCHIVO_CSV = "productos.csv";
    private static List<Producto> inventario = new ArrayList<>();

    public static void main(String[] args) {
        cargarProductos();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- GESTIÓN DE TIENDA ---");
            System.out.println("1. Listar Productos");
            System.out.println("2. Añadir Producto");
            System.out.println("3. Actualizar Stock");
            System.out.println("4. Guardar y Salir");
            System.out.print("Elige una opción: ");

            String opcionStr = scanner.nextLine();
            int opcion = -1;

            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, introduce un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> listarProductos();
                case 2 -> anadirProducto(scanner);
                case 3 -> actualizarStock(scanner);
                case 4 -> {
                    guardarProductos();
                    salir = true;
                }
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
        scanner.close();
    }

    private static void cargarProductos() {
        File archivo = new File(ARCHIVO_CSV);
        if (!archivo.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty())
                    continue;
                String[] datos = linea.split(";");
                if (datos.length == 4) {
                    try {
                        int id = Integer.parseInt(datos[0].trim());
                        String nombre = datos[1].trim();
                        double precio = Double.parseDouble(datos[2].trim());
                        int stock = Integer.parseInt(datos[3].trim());
                        inventario.add(new Producto(id, nombre, precio, stock));
                    } catch (NumberFormatException e) {
                        System.out.println("Error de formato numérico en el producto: " + linea);
                    }
                } else {
                    System.out.println("Formato incorrecto en línea: " + linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo CSV no encontrado. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }

    private static void listarProductos() {
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Producto p : inventario) {
                System.out.println(p.toString());
            }
        }
    }

    private static void anadirProducto(Scanner scanner) {
        try {
            System.out.print("Introduce el ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            for (Producto p : inventario) {
                if (p.getId() == id) {
                    System.out.println("Error: Ya existe un producto con el ID " + id + ".");
                    return;
                }
            }

            System.out.print("Introduce el nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Introduce el precio: ");
            double precio = Double.parseDouble(scanner.nextLine());

            System.out.print("Introduce el stock inicial: ");
            int stock = Integer.parseInt(scanner.nextLine());

            inventario.add(new Producto(id, nombre, precio, stock));
            System.out.println("Producto añadido correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Error: Debes introducir un formato numérico válido.");
        }
    }

    private static void actualizarStock(Scanner scanner) {
        try {
            System.out.print("Introduce el ID del producto: ");
            int id = Integer.parseInt(scanner.nextLine());

            Producto productoEncontrado = null;
            for (Producto p : inventario) {
                if (p.getId() == id) {
                    productoEncontrado = p;
                    break;
                }
            }

            if (productoEncontrado == null) {
                System.out.println("Error: Producto no encontrado con ID " + id + ".");
                return;
            }

            System.out.print("Introduce la cantidad a actualizar (positivo para añadir, negativo para descontar): ");
            int cantidad = Integer.parseInt(scanner.nextLine());

            int nuevoStock = productoEncontrado.getStock() + cantidad;
            if (nuevoStock < 0) {
                System.out.println("Error: El stock resultante no puede ser negativo.");
            } else {
                productoEncontrado.setStock(nuevoStock);
                System.out.println("Stock actualizado correctamente. Nuevo stock: " + nuevoStock);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Debes introducir un número válido.");
        }
    }

    private static void guardarProductos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CSV))) {
            for (Producto p : inventario) {
                bw.write(p.getId() + ";" + p.getNombre() + ";" + p.getPrecio() + ";" + p.getStock());
                bw.newLine();
            }
            System.out.println("Inventario guardado perfectamente en " + ARCHIVO_CSV);
        } catch (IOException e) {
            System.out.println("Error al guardar archivo CSV: " + e.getMessage());
        }
    }
}
