package GestionCommande;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public  class MenuOperations  {
	
	
	public MenuOperations( JPanel contentPane , JFrame f) {
		
		
		JPanel panneau = new JPanel();
		panneau.setBackground(new Color(29, 72, 81));
		panneau.setForeground(new Color(128, 128, 128));
		panneau.setBounds(10, 10, 251, 869);
		contentPane.add(panneau);
		panneau.setLayout(null);
		
		JLabel Operations = new JLabel("OPERATIONS");
		Operations.setBackground(SystemColor.window);
		Operations.setForeground(SystemColor.window);
		Operations.setFont(new Font("Tahoma", Font.BOLD, 20));
		Operations.setBounds(35, 135, 139, 27);
		panneau.add(Operations);
		
		JLabel Produits = new JLabel("PRODUITS");
		Produits.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Produit p = null;
				try {
					p = new Produit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				p.setVisible(true);
				f.dispose();
			}
		});
		Produits.setForeground(new Color(211, 211, 211));
		Produits.setBackground(new Color(0, 128, 128));
		Produits.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Produits.setBounds(85, 243, 103, 27);
		panneau.add(Produits);
		
		JLabel Clients = new JLabel("CLIENTS");
		Clients.addMouseListener(new MouseAdapter() {	
			public void mouseClicked(MouseEvent e) {
				Client c = new Client();
				c.setVisible(true);
				f.dispose();
			}
		}); // Permet d'acceder le frame Client après l'évènement click le label 'CLIENTS'
		Clients.setForeground(new Color(211, 211, 211));
		Clients.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Clients.setBackground(new Color(0, 128, 128));
		Clients.setBounds(85, 301, 103, 27);
		panneau.add(Clients);
		
		JLabel Commandes = new JLabel("COMMANDES");
		Commandes.addMouseListener(new MouseAdapter() {	
			public void mouseClicked(MouseEvent e) {
					Commande c = new Commande();
					c.setVisible(true);
					f.dispose();
			}
		});
		Commandes.setForeground(new Color(211, 211, 211));
		Commandes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Commandes.setBackground(new Color(0, 128, 128));
		Commandes.setBounds(85, 355, 117, 27);
		panneau.add(Commandes);
		
		JLabel Factures = new JLabel("FACTURES");
		Factures.addMouseListener(new MouseAdapter () {
			public void mouseClicked(MouseEvent e) {
				Facture F = new Facture();
				F.setVisible(true);
				f.dispose();
			}
		});
		Factures.setForeground(new Color(211, 211, 211));
		Factures.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Factures.setBackground(new Color(0, 128, 128));
		Factures.setBounds(85, 409, 103, 27);
		panneau.add(Factures);
		
		JLabel Livraisons = new JLabel("LIVRAISONS");
		Livraisons.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Livraison l = new Livraison();
				l.setVisible(true);
				f.dispose();
			}
		});
		Livraisons.setForeground(new Color(211, 211, 211));
		Livraisons.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Livraisons.setBackground(new Color(0, 128, 128));
		Livraisons.setBounds(85, 470, 103, 27);
		panneau.add(Livraisons);
		
		JLabel Quitter = new JLabel("QUITTER");
		Quitter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		Quitter.setForeground(new Color(211, 211, 211));
		Quitter.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Quitter.setBackground(new Color(0, 128, 128));
		Quitter.setBounds(85, 543, 103, 27);
		panneau.add(Quitter);
		
		JLabel sys = new JLabel("SYSTEME DE GESTION");
		sys.setFont(new Font("Tahoma", Font.PLAIN, 40));
		sys.setBounds(614, 10, 669, 136);
		contentPane.add(sys);
	}
}
