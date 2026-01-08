package enemigos;

public class Batalla {

	Enemigo e1;
	Enemigo e2;
	
	Enemigo combate() {
		while (e1.estaVivo && e2.estaVivo) {
			e2.recibirDamage(e1.ataque);
			e1.recibirDamage(e2.ataque);
		}
		System.out.printf("Pelea %s vs %s %n", e1.nombre, e2.nombre);
		if(e1.estaVivo) return e1;
		return e2;
	}
}
