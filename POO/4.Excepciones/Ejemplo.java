
import java.util.Scanner;
import java.lang.NumberFormatException;

public class Ejemplo {
    public static void main(String[] args) {

        // Parser array [1,2,3,4,5]
        Scanner sc = new Scanner(System.in);    

        String entrada = sc.nextLine();
        entrada = entrada.substring(1, entrada.length()-1);

        String[] dividido = entrada.split(",");
        int[] resultado = new int[dividido.length];
        
        try {
            for(int i = 0; i < resultado.length; i++) {
                resultado[i] = Integer.parseInt(dividido[i]);
            } 
        } catch (Exception error) {}
    }
}