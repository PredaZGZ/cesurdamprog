public class Generador {
    public static void main(String[] args) {
        String[] personas = {
            "Tay", "Carlos", "DavidN", "Marcos", "Álvaro", 
            "DavidL", "Julia", "Chema", "Nico", "Pablo"
        };        
        
        for (String persona : personas) {
            System.out.printf(
                "CREATE USER '%s'@'%%' IDENTIFIED BY '%s';%n", persona, generarPassword(10)
            );
        }
    }

    static String generarPassword(int longitud) {
        String minusculas = "abcdefghijklmnopqrstuvwxyz";
        String mayusculas = minusculas.toUpperCase();
        String numeros = "1234567890";
        String especiales = "|@#~€!$%&/()=[]{}^*-.,;:_";

        String diccionario = minusculas + mayusculas + numeros + especiales;

        int n = indiceAleatorio(minusculas.length());
        String password = "" + minusculas.charAt(n);
        
        n = indiceAleatorio(mayusculas.length());
        password = password + mayusculas.charAt(n);

        n = indiceAleatorio(numeros.length());
        password = password + numeros.charAt(n);

        n = indiceAleatorio(especiales.length());
        password = password + especiales.charAt(n);

        for(int i = 0; i < (longitud - 4); i++) {
            password = password + diccionario.charAt(indiceAleatorio(diccionario.length()));
        }
        return password;
    }    
    
    static int indiceAleatorio(int longitud) {
    		int n = (int) (Math.random()*(longitud-1));    		
        return n;
    }

}