import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interfaz {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        final String PATH = "1.Peleas/batallas.txt";
        List<Batalla> batallas = new ArrayList<Batalla>();
        List<Enemigo> enemigos = new ArrayList<Enemigo>();
        
        do {
            int opcion = menu(sc);
            if (opcion == 6) {
                System.out.println("Adioooos");
                return;
            }
    
            switch (opcion) {
                case 1 -> {
                    verEnemigos(enemigos);
                }
                case 2 -> {
                    addEnemigo(enemigos, sc);
                }
                case 3 -> {
                    eliminarEnemigo(enemigos, sc);
                }
                case 4 -> {
                    guardarEnemigos(enemigos, PATH);
                }
                case 5 -> {
                    enemigos = cargarEnemigos(PATH);
                }
            }
        } while (true);

    }

    public static int menu(Scanner sc) {
        System.out.println("== MENU PELEAS ==");
        System.out.println("1. Ver enemigos");
        System.out.println("2. Añadir un enemigo");
        System.out.println("3. Eliminar un enemigo");
        System.out.println("4. Guardar Enemigos");
        System.out.println("5. Cargar Enemigos");
        System.out.println("6. Salir");
        return sc.nextInt();
    }

    public static void verEnemigos(List<Enemigo> enemigos) {
        System.out.println("== ENEMIGOS ==");
        for (Enemigo e : enemigos) {
            System.out.println(e);
        }
    }

    public static void addEnemigo(List<Enemigo> enemigos, Scanner sc) {
        System.out.println("Introduce el nombre del enemigo:");
        String nombre = sc.next();
        System.out.println("Introduce el daño del enemigo:");
        int damage = sc.nextInt();
        System.out.println("Introduce la vida del enemigo:");
        int health = sc.nextInt();
        enemigos.add(new Enemigo(nombre, damage, health));
    }

    public static void eliminarEnemigo(List<Enemigo> enemigos, Scanner sc) {
        System.out.println("Introduce el nombre del enemigo a eliminar:");
        String nombre = sc.next();
        for (Enemigo e : enemigos) {
            if (e.getNombre().equals(nombre)) {
                enemigos.remove(e);
                break;
            }
        }
    }

    public static void guardarEnemigos(List<Enemigo> enemigos, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(enemigos);
            System.out.println("Enemigos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error escribiendo en el fichero.");
        }

    }

    public static List<Enemigo> cargarEnemigos(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            List<Enemigo> enemigosRecuperados = (List<Enemigo>) ois.readObject();
            for (Enemigo e : enemigosRecuperados) {
                System.out.println("Enemigo: " + e);
            }
            return enemigosRecuperados;
        } catch (IOException e) {
            System.out.println("Error leyendo fichero.");
        } catch (ClassNotFoundException e) {
            System.out.println("La clase del objeto leído no existe en este programa.");
        }
        return new ArrayList<Enemigo>();
    }

}
