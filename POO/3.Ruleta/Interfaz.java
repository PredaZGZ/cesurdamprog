import java.util.Scanner;

public class Interfaz {

    private static Scanner scanner = new Scanner(System.in);
    private static Ruleta ruleta;

    public static void main(String[] args) {
        System.out.println("=== BIENVENIDO A LA RULETA ===");

        // Inicializar saldo
        ruleta = null;
        while (ruleta == null) {
            try {
                double saldoInicial = leerDouble("Introduce el saldo inicial: ", 0.1, 1000000);
                ruleta = new Ruleta(saldoInicial);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        boolean salir = false;
        while (!salir && ruleta.getSaldo() > 0) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opción: ", 1, 6);

            switch (opcion) {
                case 1 -> apostarNumero();
                case 2 -> apostarColor();
                case 3 -> apostarParidad();
                case 4 -> apostarDocena();
                case 5 -> System.out.println("\nSaldo actual: " + ruleta.getSaldo());
                case 6 -> salir = true;
            }

            if (!salir && ruleta.getSaldo() <= 0) {
                System.out.println("\n¡Te has quedado sin saldo! Fin del juego.");
            }
        }

        System.out.println("\nGracias por jugar. Saldo final: " + ruleta.getSaldo());
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENU DE APUESTAS ---");
        System.out.println("Saldo actual: " + ruleta.getSaldo());
        System.out.println("1. Apostar a Número (0-36)");
        System.out.println("2. Apostar a Color (ROJO/NEGRO)");
        System.out.println("3. Apostar a Par/Impar");
        System.out.println("4. Apostar a Docena (1-3)");
        System.out.println("5. Ver Saldo");
        System.out.println("6. Salir");
    }

    private static void apostarNumero() {
        int numero = leerEntero("Introduce el número a apostar (0-36): ", 0, 36);
        double cantidad = leerDouble("Introduce la cantidad a apostar: ", 0.1, Double.MAX_VALUE);

        try {
            boolean haGanado = ruleta.apostarNumero(numero, cantidad);
            mostrarResultado(haGanado);
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void apostarColor() {
        String color = leerTexto("Elige color (ROJO/NEGRO): ", new String[] { "ROJO", "NEGRO" });
        double cantidad = leerDouble("Introduce la cantidad a apostar: ", 0.1, Double.MAX_VALUE);

        try {
            boolean haGanado = ruleta.apostarColor(color, cantidad);
            mostrarResultado(haGanado);
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void apostarParidad() {
        String paridad = leerTexto("Elige (PAR/IMPAR): ", new String[] { "PAR", "IMPAR" });
        double cantidad = leerDouble("Introduce la cantidad a apostar: ", 0.1, Double.MAX_VALUE);

        try {
            boolean haGanado = ruleta.apostarParidad(paridad, cantidad);
            mostrarResultado(haGanado);
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void apostarDocena() {
        int docena = leerEntero("Elige docena (1, 2, 3): ", 1, 3);
        double cantidad = leerDouble("Introduce la cantidad a apostar: ", 0.1, Double.MAX_VALUE);

        try {
            boolean haGanado = ruleta.apostarDocena(docena, cantidad);
            mostrarResultado(haGanado);
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void mostrarResultado(boolean haGanado) {
        int numeroSalido = ruleta.getUltimoNumero();
        String colorSalido = ruleta.getColor(numeroSalido);
        System.out.println("\n>>> Ha salido el número: " + numeroSalido + " (" + colorSalido + ")");

        if (haGanado) {
            System.out.println("¡HAS GANADO! :D");
        } else {
            System.out.println("Has perdido :(");
        }
        System.out.println("Nuevo saldo: " + ruleta.getSaldo());
    }

    private static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                String input = scanner.nextLine();
                int valor = Integer.parseInt(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.err.println("Error: El número debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Entrada inválida. Por favor introduce un número entero.");
            }
        }
    }

    private static double leerDouble(String mensaje, double min, double max) {
        while (true) {
            System.out.print(mensaje);
            try {
                String input = scanner.nextLine();
                double valor = Double.parseDouble(input);
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.err.println("Error: La cantidad debe estar entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Entrada inválida. Por favor introduce un número decimal.");
            }
        }
    }

    private static String leerTexto(String mensaje, String[] opcionesValidas) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim().toUpperCase();
            for (String op : opcionesValidas) {
                if (op.equals(input)) {
                    return input;
                }
            }
            System.err.println("Error: Opción no válida. Opciones: " + String.join(", ", opcionesValidas));
        }
    }
}
