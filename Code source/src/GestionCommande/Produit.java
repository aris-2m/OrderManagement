package GestionCommande;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Produit extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	Connection con=null;
	PreparedStatement sta=null;
	ResultSet res=null;
	
	public Produit() throws Exception {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane =   new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 10, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		con=ConnexionMysql.ConnexionBD();
		
		new MenuOperations(contentPane,this);
		
		JLabel Produits = new JLabel("> PRODUITS");
		Produits.setFont(new Font("Tahoma", Font.PLAIN, 25));
		Produits.setBounds(284, 184, 150, 31);
		contentPane.add(Produits);
		
		JPanel panelEnregistrer = new JPanel();
		panelEnregistrer.setBorder(new TitledBorder(null, "Enregistrement", 
				TitledBorder.LEADING,TitledBorder.ABOVE_TOP, null, null));
		panelEnregistrer.setBounds(329, 285, 336, 359);
		contentPane.add(panelEnregistrer);
		panelEnregistrer.setLayout(null);
		
		JLabel TitreIdProduit = new JLabel("Id Produit");
		TitreIdProduit.setBounds(37, 77, 68, 21);
		panelEnregistrer.add(TitreIdProduit);
		TitreIdProduit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane IdProduit = new JTextPane();
		IdProduit.setBounds(144, 77, 119, 21);
		panelEnregistrer.add(IdProduit);
		
		JLabel TitreNomProduit = new JLabel("Nom Produit");
		TitreNomProduit.setBounds(37, 135, 85, 21);
		panelEnregistrer.add(TitreNomProduit);
		TitreNomProduit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane NomProduit = new JTextPane();
		NomProduit.setBounds(144, 135, 119, 21);
		panelEnregistrer.add(NomProduit);
			
		JLabel TitreQuantité = new JLabel("Quantit\u00E9");
		TitreQuantité.setBounds(37, 195, 57, 21);
		panelEnregistrer.add(TitreQuantité);
		TitreQuantité.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane Quantité = new JTextPane();
		Quantité.setBounds(144, 195, 119, 21);
		panelEnregistrer.add(Quantité);
		
		JLabel TitrePrix = new JLabel("Prix");
		TitrePrix.setBounds(37, 256, 34, 21);
		panelEnregistrer.add(TitrePrix);
		TitrePrix.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane Prix = new JTextPane();
		Prix.setBounds(144, 256, 119, 21);
		panelEnregistrer.add(Prix);
				
		JButton Supprimer = new JButton("SUPPRIMER");
		Supprimer.setBounds(1237, 656, 140, 28);
		contentPane.add(Supprimer);
		
		JButton Modifier = new JButton("MODIFIER");
		Modifier.setBounds(1082, 656, 131, 28);
		contentPane.add(Modifier);
		
		JButton Actualiser = new JButton("ACTUALISER");
		Actualiser.setBounds(1237, 250, 131, 28);
		contentPane.add(Actualiser);
		
		JButton Ajouter = new JButton("AJOUTER");
		Ajouter.setBounds(557, 656, 108, 28);
		contentPane.add(Ajouter);
		
		class EcouteurBouton implements ActionListener {

			 public void actionPerformed(ActionEvent ev){		
				 if (ev.getSource()==Ajouter)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null, e);
				}
		  
		         try {
		        	 String sql = "INSERT INTO Produit (NumeroProduit, NomProduit, Quantité, Prix ) VALUES (?,?,?,?)";
		        	 PreparedStatement sta = con.prepareStatement(sql);
			        
		        	 sta.setLong(1,Integer.parseInt(IdProduit.getText()));
					 sta.setString(2,NomProduit.getText());
					 sta.setLong(3, Integer.parseInt(Quantité.getText()));
					 sta.setDouble(4,Double.parseDouble(Prix.getText()));
					 sta.executeUpdate();
			                
			         JOptionPane.showMessageDialog(null, "Enregistrer avec succès !");
			    

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère"
							+ " alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}   
				 }

				if(ev.getSource() == Modifier) { 
							 
							 try {
								Class.forName("com.mysql.jdbc.Driver");
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}						 
						 try {
	     			         String sql= "UPDATE produit SET NomProduit=?, Quantité = ?, Prix=? WHERE NumeroProduit = ?";
							 PreparedStatement sta = con.prepareStatement(sql);
							 sta.setLong(4,Integer.parseInt(IdProduit.getText()));
							 sta.setString(1,NomProduit.getText());
							 sta.setLong(2, Integer.parseInt(Quantité.getText()));
							 sta.setDouble(3,Double.parseDouble(Prix.getText()));
							 sta.executeUpdate();
							 
							 JOptionPane.showMessageDialog(null,"Modifier avec succès !");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, e);
						}catch (NumberFormatException e) {
							
							JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère "
									+ "alphabétique dans un champs de texte numérique ");
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
					  String sql= "DELETE FROM produit WHERE NumeroProduit =?";
			        	 String sql1= "SELECT *from produit";
			        	 int c=0;
			        	 int id = Integer.parseInt(IdProduit.getText());
						 PreparedStatement sta1 = con.prepareStatement(sql1);
						 ResultSet res = sta1.executeQuery();
						 while(res.next()) {
							 int num = res.getInt("NumeroProduit");
							 if(num == id) {
								 c=c+1;
								 PreparedStatement sta = con.prepareStatement(sql);
								 sta.setLong(1,Integer.parseInt(IdProduit.getText()));
								 sta.executeUpdate(); 
								 JOptionPane.showMessageDialog(null,"Supprimer avec succès !");
								 break;
								 }
						 }
						 if(c ==0) {
							 JOptionPane.showMessageDialog(null,"Produit inexistant!");
						 }
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique"
							+ " dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}					 
			} }		 
		}
		
		Ajouter.addActionListener(new EcouteurBouton());
		Supprimer.addActionListener(new EcouteurBouton());
		Modifier.addActionListener(new EcouteurBouton());
		
		JScrollPane DataTable = new JScrollPane();
		DataTable.setBounds(879, 285, 498, 350);
		contentPane.add(DataTable);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Produit", "Nom Produit", "Quantit\u00E9", "Prix"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, Double.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// mettre les données dans leurs zones de textes correspondantes quand une ligne est sélectionnée
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				String idProduit =( model.getValueAt(table.getSelectedRow(),0).toString());
				String nomProduit = model.getValueAt(table.getSelectedRow(),1).toString();
				String quantite = model.getValueAt(table.getSelectedRow(),2).toString();
				String prix = model.getValueAt(table.getSelectedRow(),3).toString();
				
				//insertion dans la zone de texte corresponde
				
				IdProduit.setText(idProduit);
				NomProduit.setText(nomProduit );
				Quantité.setText(quantite);
				Prix.setText(prix);
			}
		});
		
		DataTable.setViewportView(table);

		 
		 Actualiser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UpdateTable();
			}
			});	
	}
	public void UpdateTable() {
		String sql = "SELECT *from produit;";
		try {
			 sta = con.prepareStatement(sql);
			ResultSet res = sta.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,e1);
		}
	}	
}
