package GestionCommande;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ConnexionMysql {
Connection con=null;

public static Connection ConnexionBD() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost/gestion des commandes","root","");
		return con;
	}catch(Exception e) {
		JOptionPane.showMessageDialog(null, e);
		return null;
	}
}
}
