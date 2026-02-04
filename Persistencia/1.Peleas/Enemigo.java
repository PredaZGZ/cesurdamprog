import java.io.Serializable;

public class Enemigo implements Serializable {
    private String nombre;
    private int damage;
    private int health;

    public Enemigo(String nombre, int damage, int health) {
        this.nombre = nombre;
        this.damage = damage;
        this.health = health;
    }

    public String getNombre() {
        return this.nombre;
    }
    public int getDamage() {
        return this.damage;
    }

    public void recibirDamage(int damage) {
        this.health = this.health - damage;
    }


}
