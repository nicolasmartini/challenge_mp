package challenge_mp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;


public class Operaciones {
	
	
	private Connection conexion;


	public Operaciones(Connection con) {
		
		this.conexion = con;
	}
	
	
	public void guardar_registro(String fecha,String from,String subject) {
		
		String ins = "INSERT INTO correos(fecha,from_,subject) VALUES (?, ?, ?)";
		// Se crea un sentencia preparada
		java.sql.PreparedStatement stmt2;
		try {
			stmt2 = conexion.prepareStatement(ins);
			// Se ligan los parámetros efectivos
			stmt2.setString(1, fecha);
			stmt2.setString(2,from );
			stmt2.setString(3, subject);
			
			stmt2.executeUpdate();
			stmt2.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	

}
