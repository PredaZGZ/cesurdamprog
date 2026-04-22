package views;

import java.util.Scanner;
import java.util.List;
import models.Producto;
import dao.ProductoDAO;

public class ProductView {
	private Scanner sc = new Scanner(System.in);
	private ProductoDAO productoDAO = new ProductoDAO();
	
	public ProductView() {}
	
	public void iniciar() {
		int opcion;
		do {
			opcion = this.mostrarMenu();
			switch(opcion) {
			case 1 -> {
				this.listarProductos();
			}
			case 2 -> {
				this.addProducto();
			}
			}			
		} while (opcion != 5);
	}
	
	
	private void addProducto() {
		System.out.println("Introduce un nombre: ");
		String nombre = sc.nextLine();
		System.out.println("Introduce un precio: ");
		double precio = sc.nextDouble();
		System.out.println("Introduce un stock: ");
		int stock = sc.nextInt();
		
		Producto p = new Producto(nombre, precio, stock);
				
		if(this.productoDAO.add(p)) {
			System.out.println("Producto Insertado correctamente");
		} else {
			System.out.println("Ha ocurrido un error!");
		}

	}

	private void listarProductos() {
		List<Producto> productos = this.productoDAO.getAll();

		for (Producto p : productos) {
			System.out.println(p);
		}
		
	}


	private int mostrarMenu() {
		System.out.println("-- Gestión de productos --");
		System.out.println("1. Ver todos los productos");
		System.out.println("2. Añadir producto");
		System.out.println("3. Actualizar producto");
		System.out.println("4. Borrar producto");
		System.out.println("5. Salir");		
		int opcion = sc.nextInt();
		sc.nextLine();
		
		return opcion;
	}
	
}
