package GestionCommande;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class Acceuil extends JFrame {
	
	private JPanel contentPane;	

	public Acceuil() throws Exception   {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane =   new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 10, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		new MenuOperations(contentPane,this);
		
		JLabel Lb_infoAcceuil = new JLabel("POUR EFFECTUER UNE OPERATION, CLIQUEZ SUR UN INTUTILE A GAUCHE.");
		Lb_infoAcceuil.setFont(new Font("SansSerif", Font.PLAIN, 18));
		Lb_infoAcceuil.setBounds(494, 290, 704, 149);
		contentPane.add(Lb_infoAcceuil);
		
		JLabel Lb_bienvenue = new JLabel("BIENVENUE !");
		Lb_bienvenue.setFont(new Font("SansSerif", Font.PLAIN, 23));
		Lb_bienvenue.setBounds(752, 236, 146, 43);
		contentPane.add(Lb_bienvenue);
				
	}
}
