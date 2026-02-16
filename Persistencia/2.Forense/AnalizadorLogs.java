import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnalizadorLogs {

    private static final String FICHERO_ENTRADA = "access.log";
    private static final String FICHERO_SALIDA = "resumen_errores.txt";

    public static void main(String[] args) {        

        // Contadores globales
        int total4xx = 0;
        int total5xx = 0;

        // Mapa: ruta -> cantidad de errores
        HashMap<String, Integer> erroresPorRuta = new HashMap<>();

        // Mapa: IP -> cantidad de errores
        HashMap<String, Integer> erroresPorIP = new HashMap<>();

        // Mapa: ruta -> primera línea de ejemplo con error
        HashMap<String, String> ejemploPorRuta = new HashMap<>();

        // 1. Lectura y parsing del fichero
        try (BufferedReader br = new BufferedReader(new FileReader(FICHERO_ENTRADA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("::");

                // Validar que la línea tenga las 5 partes esperadas
                if (partes.length < 5) {
                    continue;
                }

                String ip = partes[0];
                String ruta = partes[3];
                String codigoStr = partes[4];

                int codigo;
                try {
                    codigo = Integer.parseInt(codigoStr);
                } catch (NumberFormatException e) {
                    continue;
                }

                // Solo nos interesan los errores (4xx y 5xx)
                boolean esError = false;
                if (codigo >= 400 && codigo < 500) {
                    total4xx++;
                    esError = true;
                } else if (codigo >= 500 && codigo < 600) {
                    total5xx++;
                    esError = true;
                }

                if (esError) {
                    // Agrupación por ruta
                    erroresPorRuta.put(ruta, erroresPorRuta.getOrDefault(ruta, 0) + 1);

                    // Agrupación por IP
                    erroresPorIP.put(ip, erroresPorIP.getOrDefault(ip, 0) + 1);

                    // Captura de ejemplo (solo la primera vez por ruta)
                    if (!ejemploPorRuta.containsKey(ruta)) {
                        ejemploPorRuta.put(ruta, linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el fichero de entrada: " + e.getMessage());
            return;
        }

        // 2. Detectar la IP atacante principal (la que más errores genera)
        String ipAtacante = "";
        int maxErroresIP = 0;
        for (Map.Entry<String, Integer> entry : erroresPorIP.entrySet()) {
            if (entry.getValue() > maxErroresIP) {
                maxErroresIP = entry.getValue();
                ipAtacante = entry.getKey();
            }
        }

        // 3. Generar el informe
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO_SALIDA))) {
            bw.write("=== AUDITORÍA DE SERVIDOR ===");
            bw.newLine();
            bw.newLine();

            // Sección 1: Métricas globales
            bw.write("[MÉTRICAS GLOBALES]");
            bw.newLine();
            bw.write("- Total Errores Cliente (4xx): " + total4xx);
            bw.newLine();
            bw.write("- Total Errores Servidor (5xx): " + total5xx);
            bw.newLine();
            bw.newLine();

            // Sección 2: Atacante principal
            bw.write("[ATACANTE PRINCIPAL]");
            bw.newLine();
            bw.write("- IP: " + ipAtacante);
            bw.newLine();
            bw.write("- Total Errores Generados: " + maxErroresIP);
            bw.newLine();
            bw.newLine();

            // Sección 3: Rutas críticas (> 100 errores)
            bw.write("[RUTAS CRÍTICAS (> 100 errores)]");
            bw.newLine();
            bw.write("Detectadas rutas bajo ataque intensivo:");
            bw.newLine();
            bw.newLine();

            int contador = 1;
            for (Map.Entry<String, Integer> entry : erroresPorRuta.entrySet()) {
                if (entry.getValue() > 100) {
                    String ruta = entry.getKey();
                    int cantidad = entry.getValue();
                    String ejemplo = ejemploPorRuta.get(ruta);

                    bw.write(contador + ". RUTA: " + ruta);
                    bw.newLine();
                    bw.write("   -> Cantidad: " + cantidad + " errores");
                    bw.newLine();
                    bw.write("   -> Ejemplo: " + ejemplo);
                    bw.newLine();
                    bw.newLine();

                    contador++;
                }
            }

            bw.write("=== FIN DEL INFORME ===");
            bw.newLine();

        } catch (IOException e) {
            System.err.println("Error al escribir el fichero de salida: " + e.getMessage());
            return;
        }

        System.out.println("Informe generado correctamente en: " + FICHERO_SALIDA);
    }
}