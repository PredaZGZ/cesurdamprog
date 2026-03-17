# Respuestas Teóricas - Examen 2º Trimestre

## 1. Sobrecarga vs Sobreescritura de métodos

**Sobrecarga (Overloading)** ocurre cuando en la misma clase existen varios métodos con el mismo nombre pero con diferentes parámetros (ya sea diferente número o diferente tipo de parámetros).

**Sobreescritura (Overriding)** ocurre cuando una clase hija redefine un método heredado de su clase padre. El método de la clase hija debe tener el mismo nombre, mismos parámetros y un tipo de retorno compatible. (enlazamiento dinámico o polimorfismo).

**Aplicado a constructores:** Los constructores **sí pueden sobrecargarse** (es decir, una clase puede tener varios constructores con diferentes parámetros). Sin embargo, **no pueden sobreescribirse**, ya que los constructores no son heredados por las subclases.

---

## 2. Herencia, Encapsulación y Polimorfismo

Para establecer **herencia** en Java, la clase hija debe utilizar la palabra reservada `extends` seguida del nombre de la clase padre (ej. `public class Hija extends Padre`). Además, si la clase padre no tiene un constructor vacío por defecto, el constructor de la hija debe llamar a `super(...)` invocando alguno de los constructores del padre.

**Encapsulación con herencia:** **Sí tiene sentido**. En la POO, los atributos de la clase padre generalmente se declaran como `protected`. Esto oculta cómo se guardan internamente los datos ("encapsulamiento") y sólo expone métodos o getters/setters controlados. Así, aunque una hija herede de su padre, sigue sin poder saltarse arbitrariamente validaciones de datos expuestas en las propiedades privadas.

**Relación con Polimorfismo:** Encapsular permite mantener contratos seguros sobre los datos de la propia clase, y al combinarlo con herencia obtenemos Polimorfismo: una instancia de la clase hija puede comportarse como si fuera de la clase padre (y ser albergada en una variable de tipo Padre), pero al llamar a un método sobreescrito se ejecutará la implementación específica de la clase hija.

---

## 3. En java.io: jerarquía InputStream/OutputStream vs Reader/Writer

Ambas jerarquías sirven para leer datos (Input/Reader) o escribirlos (Output/Writer).

* **`InputStream / OutputStream`**: Operan orientados a **bytes** (sistema binario). Se utilizan para procesar archivos que no son de texto, como imágenes (jpg, png), archivos de sonido (mp3, wav), archivos comprimidos, ejecutables, etc.
* **`Reader / Writer`**: Operan a nivel de **caracteres**. Aplican implícita y automáticamente la traducción de codificación (charset, como UTF-8). Están diseñados específicamente para leer o escribir información de texto pleno.

**Caso práctico:** Si el desarrollador requiere crear la funcionalidad de **escribir o leer un archivo de texto como un `.txt` o un archivo de valores separados por coma `.csv`**, resulta **preferible** e idiomático utilizar la jerarquía de `Reader/Writer` (como `BufferedReader` o `FileWriter`) en lugar de bytes puros. Esto previene automáticamente problemas de visualización e incompatibilidad de vocales con acento o la letra "ñ" originada por desajustes de codificación de caracteres a bytes.

---

## 4. Serialización de objetos

La **Serialización** es el mecanismo mediante el cual el estado completo de un objeto (sus atributos y valores en RAM) es convertido a un flujo secuencial de bytes (stream de datos), de forma tal que pueda ser persistido, por ejemplo, guardándolo en un archivo en disco duro u objeto de base de datos, o transmitiéndolo a través de una conexión de red.

**Interfaz Serializable:** En Java, implementar la interfaz `java.io.Serializable` es imprescindible si queremos serializar objetos de esa clase, porque sirve como un "marcador" que otorga permiso explícito a la Máquina Virtual de Java (JVM) para alterar el estado de esos objetos durante los procesos internos de serialización y deserialización. Sin esa interfaz, el compilador rechazará la serialización lanzando `NotSerializableException`.

**Evitar que un atributo sea guardado:** Para prevenir que un atributo en específico sea incluido o persistido durante la serialización, tan solo se debe declarar ese atributo utilizando el modificador `transient`. Por ejemplo:
`private transient String password;`
