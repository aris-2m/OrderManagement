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
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class Facture extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	Connection con=null;
	PreparedStatement sta=null;
	ResultSet res=null;
	JComboBox IdCommande;
	
	public Facture() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		new MenuOperations(contentPane,this);
		con=ConnexionMysql.ConnexionBD();
		
		JLabel Factures = new JLabel("> FACTURES");
		Factures.setFont(new Font("Tahoma", Font.PLAIN, 25));
		Factures.setBounds(284, 184, 150, 31);
		contentPane.add(Factures);	
		
		JPanel panelEnregistrer = new JPanel();
		panelEnregistrer.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Enregistrement", TitledBorder.LEADING,
				TitledBorder.ABOVE_TOP, null, new Color(59, 59, 59)));
		panelEnregistrer.setBounds(327, 298, 376, 328);
		contentPane.add(panelEnregistrer);
		panelEnregistrer.setLayout(null);
		
		JLabel TitreIdFacture = new JLabel("Id Facture");
		TitreIdFacture.setBounds(10, 61, 99, 21);
		panelEnregistrer.add(TitreIdFacture);
		TitreIdFacture.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane IdFacture = new JTextPane();
		IdFacture.setBounds(130, 50, 195, 32);
		panelEnregistrer.add(IdFacture);
		
		JLabel TitreDateFacture = new JLabel("Date Facture");
		TitreDateFacture.setBounds(10, 125, 99, 21);
		panelEnregistrer.add(TitreDateFacture);
		TitreDateFacture.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
		JLabel Montant = new JLabel();
		Montant.setBounds(130, 246, 195, 40);
		Montant.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panelEnregistrer.add(Montant); 
		
		JButton BtMontant = new JButton("Montant");
		BtMontant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			try {
			
				int i = Integer.parseInt(IdCommande.getSelectedItem().toString());
				System.out.println(i);
			    String sql="SELECT SUM(Prix*Quantité) as mont FROM produit WHERE NumeroProduit IN(SELECT NumeroProduit FROM Commande where numeroCommande = ?) ";
			
			            PreparedStatement sta = con.prepareStatement(sql);
						sta.setLong(1,i);
						res = sta.executeQuery();
						
						while (res.next()){ 
							
							String a = res.getString("mont");
							Montant.setText(a); }		
						
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}				
			}	
			
		});
		BtMontant.setBounds(10, 257, 99, 21);
		panelEnregistrer.add(BtMontant);
		BtMontant.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreIdCommande = new JLabel("Id Commande");
		TitreIdCommande.setBounds(10, 197, 99, 21);
		panelEnregistrer.add(TitreIdCommande);
		TitreIdCommande.setFont(new Font("Tahoma", Font.PLAIN, 15));
	
		IdCommande = new JComboBox();
		IdCommande.setBounds(130, 186, 195, 32);
		panelEnregistrer.add(IdCommande);
		combobox_commande();
		
		JDateChooser DateFacture = new JDateChooser();
		DateFacture.setBounds(130, 113, 195, 39);
		panelEnregistrer.add(DateFacture);
		
		JButton Actualiser = new JButton("ACTUALISER");
		Actualiser.setBounds(1295, 227, 131, 28);
		contentPane.add(Actualiser);
		
		JButton Ajouter = new JButton("AJOUTER");
		Ajouter.setBounds(601, 636, 102, 21);
		contentPane.add(Ajouter);
		
		JScrollPane DataTable = new JScrollPane();
		DataTable.setBounds(770, 267, 656, 434);
		contentPane.add(DataTable);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Facture", "Date Facture", "Montant", "Id Commande"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Double.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
 		});
		DataTable.setViewportView(table);
		JButton Modifier = new JButton("MODIFIER");
		Modifier.setBounds(1003, 711, 110, 21);
		contentPane.add(Modifier);
		
		JButton Supprimer = new JButton("SUPPRIMER");
		Supprimer.setBounds(1173, 710, 110, 21);
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
		        	 		        		     
		         String sql= "INSERT INTO facture (NumeroFacture, DateFacture, Montant, NumeroCommande) VALUES (?,?,?,?)";
		         String sql1 = "SELECT *from commande";
		         String sql2="SELECT SUM(Prix*Quantité) as mont FROM produit WHERE NumeroProduit IN(SELECT"
				    		+ " NumeroProduit FROM Commande where numeroCommande = ?) ";
		         int c=0;  String montant = "" ;
		         java.sql.Date date = new java.sql.Date(DateFacture.getDate().getTime());
		         int id1 = Integer.parseInt(IdCommande.getSelectedItem().toString());		    
				 Statement sta1 = con.createStatement();
				 ResultSet res1 = sta1.executeQuery(sql1);
					 
				 int i = Integer.parseInt(IdCommande.getSelectedItem().toString());								
				            PreparedStatement sta2 = con.prepareStatement(sql2);
							sta2.setLong(1,i);
							res = sta2.executeQuery();
							
					while (res.next()){ 					
								montant = res.getString("mont");								
								 }
				 
					while(res1.next()) {
						 int num1 = res1.getInt("NumeroCommande");
						 System.out.println(num1);
						 if(num1 == id1) { 
							 c = c+1;
							 PreparedStatement sta = con.prepareStatement(sql);
							 sta.setLong(1,Integer.parseInt(IdFacture.getText()));
							 sta.setDate(2,date);
							 sta.setDouble(3, Double.parseDouble(montant));
							 sta.setLong(4, Integer.parseInt(IdCommande.getSelectedItem().toString()));
							 
							 sta.executeUpdate();
						     JOptionPane.showMessageDialog(null,"Ajouter avec succès !");
						    
						     
						     break;}	 
					 }
					 
					 if( c == 0 ) { 
						 JOptionPane.showMessageDialog(null,"Commande inexistante");
					 }
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, e);
				}
		         
			 }
		     if (ev.getSource()==Modifier)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         try {
		        	 
		        	 String sql= "UPDATE facture SET  DateFacture =?, Montant=?, NumeroCommande =? WHERE NumeroFacture =?";
		        	 String sql1 = "SELECT *from commande";
		        	 String sql2 = "SELECT *from facture";
		        		
		        	 int c=0; int d=0;
		        	 java.sql.Date date = new java.sql.Date(DateFacture.getDate().getTime());
		        	 int id1 = Integer.parseInt(IdCommande.getSelectedItem().toString());		
		        	 int id2 = Integer.parseInt(IdFacture.getText().toString());				 
					 Statement sta1 = con.createStatement();
					 ResultSet res1 = sta1.executeQuery(sql1);				 
					 while(res1.next()) {
						 int num1 = res1.getInt("NumeroCommande");
						 if(num1 == id1) { c = c+1; } 
					 }
					 sta1.close();
					 
					 Statement sta2 = con.createStatement();
					 ResultSet res2 = sta2.executeQuery(sql2);
					 while(res2.next()) {
						 int num2 = res2.getInt("NumeroFacture");
						 if(num2 == id2) {d = d+1; }
					 }
					 sta2.close();
					 
					 if(d !=0 &&  c!=0) {
						 PreparedStatement sta = con.prepareStatement(sql);
						 sta.setLong(4,Integer.parseInt(IdFacture.getText()));
						 sta.setDate(1,date);
						 sta.setDouble(2, Double.parseDouble(Montant.getText()));
						 sta.setLong(3, Integer.parseInt(IdCommande.getSelectedItem().toString()));
						 
						 sta.executeUpdate();
					     JOptionPane.showMessageDialog(null,"Modifier avec succès !");
					 }
					 if( c == 0 || d == 0 ) { 
						 JOptionPane.showMessageDialog(null,"Facture ou Commande inexistante");
					 }
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         try {
		        	 
		        	 String sql= "DELETE FROM facture WHERE NumeroFacture =?";
		        	 String sql1= "SELECT *from facture";
		        	 int c=0;
		        	 int id = Integer.parseInt(IdFacture.getText());
					 PreparedStatement sta1 = con.prepareStatement(sql1);
					 ResultSet res = sta1.executeQuery();
					 while(res.next()) {
						 int num = res.getInt("NumeroFacture");
						 if(num == id) {
							 c=c+1;
							 PreparedStatement sta = con.prepareStatement(sql);
							 sta.setLong(1,Integer.parseInt(IdFacture.getText()));
							 sta.executeUpdate(); 
							 JOptionPane.showMessageDialog(null,"Supprimer avec succès !");
							 break;
							 }
					 }
					 if(c ==0) {
						 JOptionPane.showMessageDialog(null,"Facture inexistante!");
					 }
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
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
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				String idFacture =( model.getValueAt(table.getSelectedRow(),0).toString());
				Date dateFacture = null;
				try {
					dateFacture = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(table.getSelectedRow(),1).toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String idCommande = model.getValueAt(table.getSelectedRow(),2).toString();
				String montant = model.getValueAt(table.getSelectedRow(),2).toString();
				//insertion dans la zone de texte corresponde
				
				IdFacture.setText(idFacture);
				DateFacture.setDate(dateFacture);
				IdCommande.addItem(idCommande);
				Montant.setText(montant);
			}
		});
		
		Ajouter.addActionListener(new EcouteurBouton());
		Modifier.addActionListener(new EcouteurBouton());
		Supprimer.addActionListener(new EcouteurBouton());
		Actualiser.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {UpdateTable();}});
		
		
	}
	public void UpdateTable() {
		String sql = "SELECT *from Facture;";
		try {
			 sta = con.prepareStatement(sql);
			ResultSet res = sta.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,e1);
		}
	}
	public void combobox_commande() {
		String sql="select * from commande";
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
