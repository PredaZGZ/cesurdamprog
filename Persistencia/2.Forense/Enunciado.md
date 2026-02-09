# Auditoría de Errores Críticos en Servidor

## 1. Contexto

Tras el ataque sufrido ayer, el fichero de logs (`access.log`) ha crecido demasiado. Ya no nos interesa ver cada error individual (serían miles de líneas), sino obtener una **visión estadística** del problema.

La dirección técnica te ha pedido un **Informe de Agregación** que responda a dos preguntas:

1. ¿Cuántos errores de cliente (4xx) y de servidor (5xx) ocurrieron en total?
2. ¿Qué partes específicas de la web (rutas) están bajo ataque masivo?
3. ¿Cuál es la **IP origen** que más errores ha generado (el "atacante principal")?

## 2. Requerimientos Técnicos

Tu programa debe leer el fichero `access.log` y procesarlo cumpliendo las siguientes reglas:

1. **Lectura y Parsing:**

   * Leer línea a línea.
   * Identificar el **Código de Estado** (posición 4) y el **Recurso/Ruta** solicitado (posición 3).
2. **Lógica de Procesamiento (Estructuras de Datos):**

   * **Contadores Globales:** Debes contar cuántos errores son de tipo `4xx` y cuántos de tipo `5xx`.
   * **Agrupación por Ruta (Mapas):** Necesitas saber cuántos errores han ocurrido **por cada ruta distinta**.
     * Usa un `HashMap<>`.
   * **Detección de Atacante (IPs):** Debes contabilizar cuántos errores ha generado **cada IP origen**.
     * Usa otro `HashMap<>` para guardar (IP -> n_errores).
     * Al finalizar la lectura, recorre este mapa para encontrar cuál es la IP con el valor más alto.
   * **Captura de Ejemplos:** Para poder depurar, necesitamos guardar **un ejemplo** (la línea completa del log) de la primera vez que detectes un error en una ruta. Puedes usar un tercer Mapa auxiliar `HashMap<>`.
3. **Generación del Informe (`resumen_errores.txt`):**

   * **Sección 1:** Imprime los totales de errores 4xx y 5xx.
   * **Sección 2:** Muestra la **IP que más ha atacado** y cuántos errores ha provocado.
   * **Sección 3:** Recorre tu mapa de rutas. **FILTRO:** Solo debes mostrar aquellas rutas que tengan **más de 100 errores**.
   * Para estas rutas "críticas", imprime: la ruta, la cantidad de errores y la línea de ejemplo que guardaste.

## 3. Salida Esperada (`resumen_errores.txt`)

El fichero resultante debe tener una estructura similar a esta:

```text
=== AUDITORÍA DE SERVIDOR ===

[MÉTRICAS GLOBALES]
- Total Errores Cliente (4xx): 1540
- Total Errores Servidor (5xx): 320

[ATACANTE PRINCIPAL]
- IP: 192.168.1.55
- Total Errores Generados: 850

[RUTAS CRÍTICAS (> 100 errores)]
Detectadas rutas bajo ataque intensivo:

1. RUTA: /admin
   -> Cantidad: 450 errores
   -> Ejemplo: 45.33.22.11::2024-02-15 08:05:00::GET::/admin::403

2. RUTA: /wp-login.php
   -> Cantidad: 120 errores
   -> Ejemplo: 80.80.80.80::2024-02-15 08:06:00::GET::/wp-login.php::404

=== FIN DEL INFORME ===
```
