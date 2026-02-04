import java.io.Serializable;

public class Batalla implements Serializable {
    private Enemigo e1;
    private Enemigo e2;

    public Batalla(Enemigo e1, Enemigo e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String getNombreBatalla() {
        return "== " + e1.getNombre() + " vs " + e2.getNombre() + "==";
    }

}
