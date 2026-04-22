package dao;

import java.util.List;
import java.util.ArrayList;
import models.Producto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ProductoDAO {

	public List<Producto> getAll() {
		List<Producto> productos = new ArrayList<>();
		String sql = "SELECT * FROM productos";
		
		try (Connection conn = Conexion.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Producto p = new Producto(
						rs.getInt("id"), 
						rs.getString("nombre"), 
						rs.getDouble("precio"),
						rs.getInt("stock")
				);
				
				productos.add(p);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productos;
	}
	
	public boolean add(Producto p) {
		
		String sql = "INSERT INTO productos (nombre, precio, stock) VALUES (?,?,?)";
		
		try (Connection conn = Conexion.getConnection()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, p.getNombre());
			pstmt.setDouble(2, p.getPrecio());
			pstmt.setInt(3, p.getStock());
			
			return pstmt.executeUpdate() > 0;

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
