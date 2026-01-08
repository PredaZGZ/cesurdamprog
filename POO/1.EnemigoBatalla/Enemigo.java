package enemigos;

public class Enemigo {
	// ATRIBUTOS
	
	String nombre;
	int vida;
	int ataque;
	int velocidad;
	boolean estaVivo = true;
	
	// ----------------------
	
	void recibirDamage(int damage) {
		if (estaVivo) {
			vida = vida - damage;
			if (vida <= 0) {
				estaVivo = false;
				vida = 0;
			}
		}
	}
	
	void addVida(int vidaextra) {
		vida = vida + vidaextra;
	}
	
	
}
