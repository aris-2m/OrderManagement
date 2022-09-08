package GestionCommande;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class Client extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection con=null;
	PreparedStatement sta=null;
	ResultSet res=null;
	
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		new MenuOperations(contentPane,this);
		con=ConnexionMysql.ConnexionBD();
		
		JLabel titreClients = new JLabel("> CLIENTS");
		titreClients.setFont(new Font("Tahoma", Font.PLAIN, 25));
		titreClients.setBounds(284, 184, 150, 31);
		contentPane.add(titreClients);
			
		JPanel panneau = new JPanel();
		panneau.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				"Enregistrement", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		panneau.setBounds(327, 267, 376, 434);
		contentPane.add(panneau);
		panneau.setLayout(null);
		
		JLabel TitreIdClient = new JLabel("Id Client");
		TitreIdClient.setBounds(10, 61, 99, 21);
		panneau.add(TitreIdClient);
		TitreIdClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreNom = new JLabel("Nom ");
		TitreNom.setBounds(10, 125, 99, 21);
		panneau.add(TitreNom);
		TitreNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitrePrenom = new JLabel("Pr\u00E9noms");
		TitrePrenom.setBounds(10, 197, 99, 21);
		panneau.add(TitrePrenom);
		TitrePrenom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreAdresse = new JLabel("Adresse");
		TitreAdresse.setBounds(10, 257, 99, 21);
		panneau.add(TitreAdresse);
		TitreAdresse.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreTelephone = new JLabel("T\u00E9l\u00E9phone");
		TitreTelephone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		TitreTelephone.setBounds(10, 330, 99, 21);
		panneau.add(TitreTelephone);
		
		JTextPane IdClient = new JTextPane();
		IdClient.setBounds(130, 50, 195, 32);
		panneau.add(IdClient);
		
		JTextPane NomClient = new JTextPane();
		NomClient.setBounds(130, 114, 195, 32);
		panneau.add(NomClient);
		
		JTextPane PrenomClient = new JTextPane();
		PrenomClient.setBounds(130, 186, 195, 32);
		panneau.add(PrenomClient);
		
		
		JTextPane AdresseClient = new JTextPane();
		AdresseClient.setBounds(130, 246, 195, 32);
		panneau.add(AdresseClient);
		
		JTextPane TelephoneClient = new JTextPane();
		TelephoneClient.setBounds(130, 319, 195, 32);
		panneau.add(TelephoneClient);
		
		JButton Actualiser = new JButton("ACTUALISER");
		Actualiser.setBounds(1295, 235, 131, 28);
		contentPane.add(Actualiser);
		
		JButton Ajouter = new JButton("AJOUTTER");
		
		Ajouter.setBounds(601, 711, 102, 21);
		contentPane.add(Ajouter);
		
		JScrollPane DataTable = new JScrollPane();
		DataTable.setBounds(770, 267, 656, 434);
		contentPane.add(DataTable);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Client", "Nom", "Pr\u00E9noms", "Adresse", "T\u00E9l\u00E9phone"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, Long.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		DataTable.setViewportView(table);
		
		JButton Modifier = new JButton("MODIFIER");
		Modifier.setBounds(1135, 707, 129, 28);
		contentPane.add(Modifier);
		
		JButton Supprimer = new JButton("SUPPRIMER");
		Supprimer.setBounds(1297, 707, 129, 28);
		contentPane.add(Supprimer);
		
		/*	  Ecouteur des boutons afin d'exécuter des instructions à la demande		*/
		class EcouteurBouton implements ActionListener {

			 public void actionPerformed(ActionEvent ev){
			
				 if (ev.getSource()==Ajouter)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		
		         try {
					
		        	     String sql= "INSERT INTO client (NumeroClient, Nom, Prénoms, Adresse, Télépnone) VALUES (?,?,?,?,?)";
						 
						 PreparedStatement sta = con.prepareStatement(sql);
						 sta.setLong(1,Integer.parseInt(IdClient.getText()));
						 sta.setString(2,NomClient.getText());
						 sta.setString(3,PrenomClient.getText());
						 sta.setString(4,AdresseClient.getText());
						 sta.setLong(5, Integer.parseInt(TelephoneClient.getText()));
						 sta.executeUpdate();

						 JOptionPane.showMessageDialog(null,"Ajouter avec succès !");
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
					}catch (NumberFormatException e) {
						
						JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
					}catch (NullPointerException e) {
						
						JOptionPane.showMessageDialog(null, e);
					}
		         
			 }
				 
				 if(ev.getSource() == Modifier) { 
					 
					 try {
						Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					 
				 try {
					 
			         
			         String sql= "UPDATE client SET Nom=?,Prénoms=?, Adresse = ?,Télépnone =? WHERE NumeroClient =?";
					 PreparedStatement sta = con.prepareStatement(sql);
					 sta.setLong(5,Integer.parseInt(IdClient.getText()));
					 sta.setString(1,NomClient.getText());
					 sta.setString(2,PrenomClient.getText());
					 sta.setString(3,AdresseClient.getText());
					 sta.setLong(4, Integer.parseInt(TelephoneClient.getText()));
					 sta.executeUpdate();
					 
					 JOptionPane.showMessageDialog(null,"Modifier avec succès !");
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}
		         
			}	 
				 
			if(ev.getSource()== Supprimer) {
					 
					 try {
						Class.forName("com.mysql.jdbc.Driver");
			
					} catch (ClassNotFoundException e) {
						JOptionPane.showMessageDialog(null,e);
					}
				  try {
					  
					     String sql= "DELETE FROM client WHERE NumeroClient =?";
			        	 String sql1= "SELECT *from client";
			        	 String sql2="DELETE from commande where NumeroClient = ? ";
			        	 String sql3="DELETE FROM facture where NumeroCommande in (select "
			        	 		+ "NumeroCommande from commande where numeroClient =?)";
			        	 int c=0;
			        	 int id = Integer.parseInt(IdClient.getText());
						 PreparedStatement sta1 = con.prepareStatement(sql1);
						 ResultSet res = sta1.executeQuery();
						 while(res.next()) {
							 int num = res.getInt("NumeroClient");
							 if(num == id) {
								 c=c+1;
								 PreparedStatement sta = con.prepareStatement(sql);
								 sta.setLong(1,Integer.parseInt(IdClient.getText()));
								 PreparedStatement sta2 = con.prepareStatement(sql2);
								 sta2.setLong(1,Integer.parseInt(IdClient.getText()));
								 PreparedStatement sta3 = con.prepareStatement(sql3);
								 sta3.setLong(1,Integer.parseInt(IdClient.getText()));
								 sta3.executeUpdate();
								 sta2.executeUpdate();
								 sta.executeUpdate(); 
								 JOptionPane.showMessageDialog(null,"Supprimer avec succès !");
								 break;
								 }
						 }
						 if(c ==0) {
							 JOptionPane.showMessageDialog(null,"Client inexistant!");
						 }
				}catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}
		         
			 
			 
			}
		  }
			 
		}
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// mettre les données dans leurs zones de textes correspondantes quand une ligne est sélectionnée
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				String idClient =( model.getValueAt(table.getSelectedRow(),0).toString());
				String nomClient = model.getValueAt(table.getSelectedRow(),1).toString();
				String prenomClient = model.getValueAt(table.getSelectedRow(),2).toString();
				String adresseClient = model.getValueAt(table.getSelectedRow(),3).toString();
				String telephoneClient = model.getValueAt(table.getSelectedRow(),4).toString();
				
				//insertion dans la zone de texte corresponde
				
				IdClient.setText(idClient);
				NomClient.setText(nomClient );
				PrenomClient.setText(prenomClient );
				AdresseClient.setText(adresseClient );
				TelephoneClient.setText(telephoneClient);
			}
		});
		
		Actualiser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateTable();
		}
		});	
		
		Ajouter.addActionListener(new EcouteurBouton());
		Modifier.addActionListener(new EcouteurBouton());
		Supprimer.addActionListener(new EcouteurBouton());
	}
	
	public void UpdateTable() {
		String sql = "SELECT *from client;";
		try {
			 sta = con.prepareStatement(sql);
			ResultSet res = sta.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,e1);
		}
	}
}
