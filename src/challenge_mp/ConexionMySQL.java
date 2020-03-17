package challenge_mp;
import java.sql.*;

import javax.swing.JOptionPane;


public class ConexionMySQL  {

    private String db = "challenge";
    private String url = "jdbc:mysql://localhost/"+db;
    private String user ;
    private String pass;
    private Connection conexion;

   public Connection Conectar(String u,String p ){

       Connection link = null;

       try{
    	   
           Class.forName("org.gjt.mm.mysql.Driver");
           user = u;
           pass = p;

           link = DriverManager.getConnection(this.url, this.user, this.pass);

       }catch(Exception ex){
    	   System.out.println(ex.getMessage());
           JOptionPane.showMessageDialog(null, "El usuario o la contraseña es incorrecto","Error de acceso a la base de datos",JOptionPane.ERROR_MESSAGE);

       }

       conexion=link;
       return link;

   }
   
   public String getUser(){
	   
	   return user;
	   
   }
   
   public String getPass(){
	   return pass;
	   
   }
   
   public String getdriver(){
	   
	   return "com.mysql.jdbc.Driver";
   }
   
   public String uri(){
	   
	   return url;
   }
   
   public Connection getConnection(){
	   return conexion;
   }

}
