package enemigos;

public class Juego {

	public static void main(String[] args) {
		
		Enemigo orco = new Enemigo();
		orco.nombre = "Orco";
		orco.vida = 100;
		orco.ataque = 20;
		
		Enemigo dragon = new Enemigo();
		dragon.nombre = "Dragon";
		dragon.vida = 200;
		dragon.ataque = 10;
		
		Batalla batalla = new Batalla();
		batalla.e1 = orco;
		batalla.e2 = dragon;
		Enemigo ganador = batalla.combate();
		
		System.out.println("Ha ganado "+ ganador.nombre);
		
		
	}

}
