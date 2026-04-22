package dao;

import java.sql.*;

public class Conexion {
	
	private static final String URL = "jdbc:mysql://saulmoreno.es:3333/marcos_tienda";
	private static final String USER = "marcos";
	private static final String PASSWORD = "15b6508d24964cdb93c78ee3977f0490";
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch(SQLException e) {
			System.err.println("No se ha conectado a la base de datos: \n"+e.getMessage());
		}
		return conn;
	}
}
