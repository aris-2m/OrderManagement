package GestionCommande;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Lancement {

	public static void main (String args []) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel()); //Nouveau look des composants bouttons etc
		Login l =new Login();
		l.setVisible(true);
	}
}