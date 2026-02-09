import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GeneradorLogsRealista {

    // Configuración
    private static final String ARCHIVO_SALIDA = "access.log";
    private static final int TOTAL_LINEAS = 2500;
    private static final String IP_ATACANTE = "45.33.22.11"; // El "malo"

    // Recursos del sistema
    private static final String[] PAGINAS = { "/index.html", "/contacto.html", "/productos.html", "/login",
            "/dashboard" };
    private static final String[] ASSETS = { "/css/style.css", "/js/app.js", "/img/logo.png",
            "/css/bootstrap.min.css" };
    private static final String[] RUTAS_ATAQUE = { "/admin", "/wp-login.php", "/.env", "/config.php", "/api/v1/dump" };

    // Usuarios "reales" (Pool de IPs recurrentes)
    private static final String[] IPS_USUARIOS = {
            "192.168.10.5", "192.168.10.12", "10.0.0.45", "88.12.33.21",
            "172.16.5.99", "24.55.12.8", "212.10.5.55", "192.168.10.5"
    };

    // Estado global de tiempo (Empieza hoy a las 08:00:00)
    private static LocalDateTime tiempoActual = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);
    private static Random random = new Random();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("Generando logs realistas con secuencias temporales...");

        try (FileWriter writer = new FileWriter(ARCHIVO_SALIDA)) {
            int contadorLineas = 0;

            while (contadorLineas < TOTAL_LINEAS) {
                // 1. Avanzar el tiempo ligeramente (entre 100ms y 3 segundos)
                avanzarTiempo();

                // 2. Determinar el escenario según el progreso
                String lineaLog;

                if (contadorLineas > 1800) {
                    // FASE 3: EL SERVIDOR COLAPSA (Líneas 1800-2500)
                    // Mucho error 500/503 y timeouts
                    lineaLog = generarTraficoCaida();
                } else if (contadorLineas > 1200) {
                    // FASE 2: EL ATAQUE COMIENZA (Líneas 1200-1800)
                    // Mezcla de tráfico normal y ataques rápidos de la IP sospechosa
                    if (random.nextInt(100) < 60) { // 60% probabilidad de ataque
                        lineaLog = generarAtaque();
                    } else {
                        lineaLog = generarTraficoUsuarioNormal();
                    }
                } else {
                    // FASE 1: TRÁFICO NORMAL (Líneas 0-1200)
                    lineaLog = generarTraficoUsuarioNormal();
                }

                writer.write(lineaLog + "\n");
                contadorLineas++;

                // Simulación de "burst" (Carga de assets):
                // Si un usuario pide HTML, el navegador pide CSS/JS inmediatamente después
                if (lineaLog.contains(".html") && !lineaLog.contains(IP_ATACANTE) && contadorLineas < TOTAL_LINEAS) {
                    String[] partes = lineaLog.split("::");
                    String ipOriginal = partes[0];
                    // Generar 1 o 2 assets para esa misma IP
                    int numAssets = random.nextInt(3);
                    for (int j = 0; j < numAssets; j++) {
                        String asset = ASSETS[random.nextInt(ASSETS.length)];
                        // Tiempo avanza muy poco (milisegundos) para assets
                        tiempoActual = tiempoActual.plusNanos(random.nextInt(500_000_000));
                        String logAsset = String.format("%s::%s::GET::%s::200",
                                ipOriginal, tiempoActual.format(formatter), asset);
                        writer.write(logAsset + "\n");
                        contadorLineas++;
                    }
                }
            }

            System.out.println("Generado 'access.log' con " + contadorLineas + " líneas.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private static void avanzarTiempo() {
        // Incremento aleatorio entre 0.1s y 4s
        long milis = 100 + random.nextInt(4000);
        tiempoActual = tiempoActual.plusNanos(milis * 1_000_000);
    }

    private static String generarTraficoUsuarioNormal() {
        String ip = IPS_USUARIOS[random.nextInt(IPS_USUARIOS.length)];
        String metodo = random.nextInt(10) < 8 ? "GET" : "POST"; // 80% GET
        String recurso = PAGINAS[random.nextInt(PAGINAS.length)];

        String codigo = "200";
        if (metodo.equals("POST") && recurso.equals("/login")) {
            // A veces fallan al loguearse
            codigo = random.nextBoolean() ? "302" : "401";
        } else if (random.nextInt(100) < 5) {
            codigo = "404"; // Pequeña probabilidad de error humano (typo)
        }

        return formatLog(ip, metodo, recurso, codigo);
    }

    private static String generarAtaque() {
        // El atacante va muy rápido, el tiempo avanza menos
        tiempoActual = tiempoActual.plusNanos(50_000_000); // 50ms

        String ip = IP_ATACANTE;
        String metodo = random.nextBoolean() ? "GET" : "POST";
        String recurso = RUTAS_ATAQUE[random.nextInt(RUTAS_ATAQUE.length)];
        String codigo = random.nextInt(100) < 90 ? "403" : "404"; // Casi todo prohibido

        return formatLog(ip, metodo, recurso, codigo);
    }

    private static String generarTraficoCaida() {
        // Durante la caída, cualquier IP recibe errores
        String ip = random.nextBoolean() ? IP_ATACANTE : IPS_USUARIOS[random.nextInt(IPS_USUARIOS.length)];
        String metodo = "GET";
        String recurso = PAGINAS[random.nextInt(PAGINAS.length)];
        String codigo = random.nextInt(100) < 80 ? "500" : "503"; // Error de servidor masivo

        return formatLog(ip, metodo, recurso, codigo);
    }

    private static String formatLog(String ip, String metodo, String recurso, String codigo) {
        return String.format("%s::%s::%s::%s::%s",
                ip, tiempoActual.format(formatter), metodo, recurso, codigo);
    }
}