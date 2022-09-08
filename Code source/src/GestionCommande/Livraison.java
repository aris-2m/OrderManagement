package GestionCommande;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

public class Livraison extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	Connection con=null;
	PreparedStatement sta=null;
	ResultSet res=null;
	JComboBox IdCommande;
	
	public Livraison() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		con=ConnexionMysql.ConnexionBD();
		new MenuOperations(contentPane,this);
		
		JLabel Livraisons = new JLabel("> LIVRAISONS");
		Livraisons.setFont(new Font("Tahoma", Font.PLAIN, 25));
		Livraisons.setBounds(284, 184, 165, 31);
		contentPane.add(Livraisons);
		
		JPanel panelEnregistrer = new JPanel();
		panelEnregistrer.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Enregistrement", 
				TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(59, 59, 59)));
		panelEnregistrer.setBounds(327, 267, 376, 283);
		contentPane.add(panelEnregistrer);
		panelEnregistrer.setLayout(null);
		
		JLabel TitreIdLivraison = new JLabel("Id Livraison");
		TitreIdLivraison.setBounds(10, 61, 99, 21);
		panelEnregistrer.add(TitreIdLivraison);
		TitreIdLivraison.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreDateLivraison = new JLabel("Date Livraison");
		TitreDateLivraison.setBounds(10, 125, 99, 21);
		panelEnregistrer.add(TitreDateLivraison);
		TitreDateLivraison.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreIdCommande = new JLabel("Id Commande");
		TitreIdCommande.setBounds(10, 197, 99, 21);
		panelEnregistrer.add(TitreIdCommande);
		TitreIdCommande.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane IdLivraison = new JTextPane();
		IdLivraison.setBounds(130, 50, 195, 32);
		panelEnregistrer.add(IdLivraison);
		
		JDateChooser DateLivraison = new JDateChooser();
		DateLivraison.setBounds(130, 125, 195, 28);
		panelEnregistrer.add(DateLivraison);
		
		IdCommande = new JComboBox();
		IdCommande.setBounds(130, 186, 195, 32);
		panelEnregistrer.add(IdCommande);
		combobox_livraison();
	
		JButton Actualiser = new JButton("ACTUALISER");
		Actualiser.setBounds(1295, 227, 131, 28);
		contentPane.add(Actualiser);
		
		JButton Ajouter = new JButton("AJOUTTER");
		Ajouter.setBounds(601, 560, 102, 21);
		contentPane.add(Ajouter);
		
		JScrollPane DataTable = new JScrollPane();
		DataTable.setBounds(770, 267, 656, 434);
		contentPane.add(DataTable);
	
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Livraison", "Date Livraison", "Id Commande"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		DataTable.setViewportView(table);
		
		JButton Modifier = new JButton("MODIFIER");
		Modifier.setBounds(1006, 711, 102, 21);
		contentPane.add(Modifier);
		
		JButton Supprimer = new JButton("SUPPRIMER");
		Supprimer.setBounds(1168, 711, 115, 21);
		contentPane.add(Supprimer);
		
		class EcouteurBouton implements ActionListener{
			 public void actionPerformed(ActionEvent ev){
				 if (ev.getSource()==Ajouter)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		         try {	        	
		        	 java.sql.Date date = new java.sql.Date(DateLivraison.getDate().getTime());
		        	 
		        	 String sql= "INSERT INTO livraison (NumeroLivraison, DateLivraison, NumeroCommande) VALUES (?,?,?)";
		        	 String sql1 = "SELECT *from commande";
		        	 int c=0;
		        	 int id1 = Integer.parseInt(IdCommande.getSelectedItem().toString());
					 PreparedStatement sta1 = con.prepareStatement(sql);
					 ResultSet res1 = sta1.executeQuery(sql1);
					 
					 while(res1.next()) {
						 int num1 = res1.getInt("NumeroCommande");
						 if(num1 == id1) {
							 c=c+1;
							 PreparedStatement sta = con.prepareStatement(sql);
							 sta.setLong(1,Integer.parseInt(IdLivraison.getText()));
							 sta.setDate(2,date);
							 sta.setLong(3, Integer.parseInt(IdCommande.getSelectedItem().toString()));							 
							 sta.executeUpdate();
							 JOptionPane.showMessageDialog(null,"Ajouter avec succès !"); break;
						 }
					 }
					 if(c == 0) {
						 	JOptionPane.showMessageDialog(null, "Commande inexistant");
					 }
					 
					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}
		         
				 }
		     if (ev.getSource()==Modifier)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		         try {
		        	 String sql= "UPDATE livraison SET  DateLivraison =?, NumeroCommande =? WHERE NumeroLivraison =?";
		        	 String sql1 = "SELECT *from commande";
		        	 String sql2 = "SELECT *from livraison";
		        	 int c =0; int d=0;
		        	 java.sql.Date date = new java.sql.Date(DateLivraison.getDate().getTime());
		        	 int id1 = Integer.parseInt(IdCommande.getSelectedItem().toString());
		        	 int id2 = Integer.parseInt(IdLivraison.getText());
		        	 PreparedStatement sta1 = con.prepareStatement(sql);
					 ResultSet res1 = sta1.executeQuery(sql1);								 
					 while(res1.next()) {
						 int num1 = res1.getInt("NumeroCommande");						 
						 if(num1 == id1) {c=c+1;}
					 }
					 
					 PreparedStatement sta2 = con.prepareStatement(sql2);
					 ResultSet res2 = sta2.executeQuery();
					 while(res2.next()) {
						 int num2 = res2.getInt("NumeroLivraison");
						 if(num2 == id2) { d = d+1;}
					 }
					 
					 if( c !=0 && d != 0) {
						 PreparedStatement sta = con.prepareStatement(sql);
						 sta.setLong(3,Integer.parseInt(IdLivraison.getText()));
						 sta.setDate(1,date);
						 sta.setLong(2, Integer.parseInt(IdCommande.getSelectedItem().toString()));
						 sta.executeUpdate();
						 JOptionPane.showMessageDialog(null,"Modifier avec succès !");
					 
					 }
					 
					 if(c == 0 || d == 0) {
						 	JOptionPane.showMessageDialog(null, " Facture ou Commande inexistant");
					 }
					 
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}
		         
			 }
		     
		     if (ev.getSource()==Supprimer)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		         
		         try { 
					 String sql= "DELETE FROM livraison WHERE NumeroLivraison =?";
		        	 String sql1= "SELECT *from livraison";
		        	 int c=0;
		        	 int id = Integer.parseInt(IdLivraison.getText());
					 PreparedStatement sta1 = con.prepareStatement(sql1);
					 ResultSet res = sta1.executeQuery();
					 while(res.next()) {
						 int num = res.getInt("NumeroLivraison");
						 if(num == id) {
							 c=c+1;
							 PreparedStatement sta = con.prepareStatement(sql);
							 sta.setLong(1,Integer.parseInt(IdLivraison.getText()));
							 sta.executeUpdate(); 
							 JOptionPane.showMessageDialog(null,"Supprimer avec succès !");
							 break;
							 }
					 }
					 if(c ==0) {
						 JOptionPane.showMessageDialog(null,"Livraison inexistante!");
					 } 					
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				}
			 }
		    }
		}
		Ajouter.addActionListener(new EcouteurBouton());
		Modifier.addActionListener(new EcouteurBouton());
		Supprimer.addActionListener(new EcouteurBouton());
		Actualiser.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {UpdateTable();}});
		
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				String idLivraison =model.getValueAt(table.getSelectedRow(),0).toString();
				Date dateLivraison = null;
				try {
					dateLivraison = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(table.getSelectedRow(),1).toString());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				//insertion dans la zone de texte corresponde
				
				IdLivraison.setText(idLivraison);
				DateLivraison.setDate(dateLivraison);
				
				
			}
		});
	}
	public void UpdateTable() {
		String sql = "SELECT *from livraison;";
		try {
			 sta = con.prepareStatement(sql);
			ResultSet res = sta.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null,e1);
		}
	}
	public void combobox_livraison() {
		String sql="select numeroCommande from facture";
		try {
			sta=con.prepareStatement(sql);
			res=sta.executeQuery();
			while(res.next()) {
				String NumeroCommande=res.getString("NumeroCommande").toString();
				IdCommande.addItem(NumeroCommande);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
