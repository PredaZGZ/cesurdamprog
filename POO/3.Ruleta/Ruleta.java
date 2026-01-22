public class Ruleta {

    public final int NUMEROS_RULETA = 37;
    public final int MULTIPLICADOR_PAR = 2;
    public final int MULTIPLICADOR_IMPAR = 2;
    public final int MULTIPLICADOR_ROJO = 2;
    public final int MULTIPLICADOR_NEGRO = 2;
    public final int MULTIPLICADOR_DOCENA = 3;
    public final int MULTIPLICADOR_NUMERO = 36;

    public final String VERDE = "VERDE";
    public final String ROJO = "ROJO";
    public final String NEGRO = "NEGRO";

    private double saldo;
    private int ultimoNumero; // Almacena el último número sorteado

    public Ruleta(double saldo) {
        this.saldo = this.setSaldo(saldo);
    }

    public double getSaldo() {
        return saldo;
    }

    // Devuelve el último número para que el frontend pueda consultarlo
    public int getUltimoNumero() {
        return ultimoNumero;
    }

    public double setSaldo(double saldo) {
        if (saldo < 0) {
            return 0;
        }
        return saldo;
    }

    // Genera el número aleatorio y actualiza el estado
    public int tirarBolita() {
        this.ultimoNumero = (int) (Math.random() * this.NUMEROS_RULETA);
        return this.ultimoNumero;
    }

    public String getColor(int numero) {
        String[] COLORES = { VERDE, ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, NEGRO, ROJO, NEGRO,
                ROJO, NEGRO, ROJO, NEGRO, ROJO, ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, NEGRO,
                ROJO, NEGRO, ROJO, NEGRO, ROJO, NEGRO, ROJO };
        if (numero >= 0 && numero < COLORES.length) {
            return COLORES[numero];
        }
        return VERDE;
    }

    public boolean esPar(int numero) {
        // En ruleta el 0 no es ni par ni impar para apuestas (o pierde)
        if (numero == 0)
            return false;
        return numero % 2 == 0;
    }

    public boolean apostarNumero(int numero, double cantidad) {
        if (cantidad > this.saldo || numero < 0 || numero > 36)
            return false;

        this.saldo -= cantidad;
        tirarBolita();

        if (checkNumero(numero)) {
            this.saldo += cantidad * MULTIPLICADOR_NUMERO;
            return true;
        }
        return false;
    }

    public boolean apostarColor(String color, double cantidad) {
        if (cantidad > this.saldo)
            return false;
        color = color.toUpperCase();
        if (!color.equals(ROJO) && !color.equals(NEGRO))
            return false;

        this.saldo -= cantidad;
        tirarBolita();

        if (checkColor(color)) {
            this.saldo += cantidad * MULTIPLICADOR_ROJO;
            return true;
        }
        return false;
    }

    public boolean apostarParidad(String paridad, double cantidad) {
        if (cantidad > this.saldo)
            return false;
        paridad = paridad.toUpperCase();
        if (!paridad.equals("PAR") && !paridad.equals("IMPAR"))
            return false;

        this.saldo -= cantidad;
        tirarBolita();

        if (checkParidad(paridad)) {
            this.saldo += cantidad * MULTIPLICADOR_PAR;
            return true;
        }
        return false;
    }

    public boolean apostarDocena(int docena, double cantidad) {
        if (cantidad > this.saldo || docena < 1 || docena > 3)
            return false;

        this.saldo -= cantidad;
        tirarBolita();

        if (checkDocena(docena)) {
            this.saldo += cantidad * MULTIPLICADOR_DOCENA;
            return true;
        }
        return false;
    }

    private boolean checkNumero(int apostado) {
        return apostado == ultimoNumero;
    }

    private boolean checkColor(String color) {
        // Si sale 0 (VERDE), las apuestas a ROJO o NEGRO pierden
        return getColor(ultimoNumero).equalsIgnoreCase(color);
    }

    private boolean checkParidad(String paridad) {
        if (ultimoNumero == 0)
            return false;
        boolean esPar = (ultimoNumero % 2 == 0);

        if (paridad.equalsIgnoreCase("PAR"))
            return esPar;
        if (paridad.equalsIgnoreCase("IMPAR"))
            return !esPar;
        return false;
    }

    private boolean checkDocena(int docena) {
        if (ultimoNumero == 0)
            return false; // 0 no pertenece a ninguna docena
        if (docena == 1)
            return ultimoNumero >= 1 && ultimoNumero <= 12;
        if (docena == 2)
            return ultimoNumero >= 13 && ultimoNumero <= 24;
        if (docena == 3)
            return ultimoNumero >= 25 && ultimoNumero <= 36;
        return false;
    }

}
