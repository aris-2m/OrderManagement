package GestionCommande;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Commande extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection con=null;
	PreparedStatement sta=null;
	ResultSet res=null;
	JComboBox IdClient;
	JComboBox IdProduit;

	public Commande() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1450, 800);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane =   new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		con=ConnexionMysql.ConnexionBD();
		
		new MenuOperations(contentPane,this);
		
		JLabel Commandes = new JLabel("> COMMANDES");
		Commandes.setFont(new Font("Tahoma", Font.PLAIN, 25));
		Commandes.setBounds(284, 184, 174, 31);
		contentPane.add(Commandes);
		
		JScrollPane DataTable = new JScrollPane();
		DataTable.setBounds(829, 257, 512, 352);
		contentPane.add(DataTable);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id Commande", "Date Commande", "Id Client", "Id Produit"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Integer.class, Integer.class
			};
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
		DataTable.setViewportView(table);
		
		JButton Modifier = new JButton("MODIFIER");
		Modifier.setBounds(1105, 635, 99, 21);
		contentPane.add(Modifier);
		
		JButton Supprimer = new JButton("SUPPRIMER");
		Supprimer.setBounds(1234, 635, 107, 21);
		contentPane.add(Supprimer);
		
		JPanel panelEnregistrer = new JPanel();
		panelEnregistrer.setBorder(new TitledBorder(null, "Enregistrement", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelEnregistrer.setBounds(282, 257, 392, 368);
		contentPane.add(panelEnregistrer);
		panelEnregistrer.setLayout(null);
		
		JLabel TitreIdCommande = new JLabel("Id Commande");
		TitreIdCommande.setBounds(26, 57, 99, 21);
		panelEnregistrer.add(TitreIdCommande);
		TitreIdCommande.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextPane IdCommande = new JTextPane();
		IdCommande.setBounds(167, 57, 160, 21);
		panelEnregistrer.add(IdCommande);
		
		JLabel TitreDateCommande = new JLabel("Date Commande");
		TitreDateCommande.setBounds(26, 136, 112, 21);
		panelEnregistrer.add(TitreDateCommande);
		TitreDateCommande.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel TitreIdClient = new JLabel("Id Client");
		TitreIdClient.setBounds(26, 202, 56, 21);
		panelEnregistrer.add(TitreIdClient);
		TitreIdClient.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JDateChooser DateCommande = new JDateChooser();
		DateCommande.setBounds(167, 129, 160, 28);
		panelEnregistrer.add(DateCommande);
		
		
		IdClient=new JComboBox();
		IdClient.setBounds(167, 202, 160, 21);
		panelEnregistrer.add(IdClient);
		combobox_client();
		
		JLabel TitreIdProduit = new JLabel("Id Produit");
		TitreIdProduit.setBounds(31, 264, 77, 21);
		panelEnregistrer.add(TitreIdProduit);
		TitreIdProduit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		IdProduit=new JComboBox();
		IdProduit.setBounds(167, 264, 160, 21);
		panelEnregistrer.add(IdProduit);
		combobox_produit();
		
		JButton Actualiser = new JButton("ACTUALISER");
		Actualiser.setBounds(1209, 220, 131, 28);
		contentPane.add(Actualiser);
		
		JButton Ajouter = new JButton("AJOUTER");
		Ajouter.setBounds(567, 649, 107, 21);
		contentPane.add(Ajouter);
		
		class EcouteurBouton implements ActionListener{
			 public void actionPerformed(ActionEvent ev){
				 String str1 = "jdbc:mysql://localhost:3306/gestion des commandes";
				 String str2 = "root";
				 String str3 = "";
				 if (ev.getSource()==Ajouter)
				 {
		         try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         Connection con=null;
		         try {        	
		        	 java.sql.Date date = new java.sql.Date(DateCommande.getDate().getTime());
		        	 
		        	 String sql= "INSERT INTO commande (NumeroCommande, DateCommande, NumeroClient, NumeroProduit) VALUES (?,?,?,?)";
		        	 String sql1 = "SELECT *from client";
		        	 String sql2 = "SELECT *from produit";
		        	 int c=0; int d=0; 
		        	 int id1 = Integer.parseInt(IdClient.getSelectedItem().toString());
		        	 int id2 = Integer.parseInt(IdProduit.getSelectedItem().toString());
					 con = DriverManager.getConnection(str1,str2,str3);
					 
					 Statement sta1 = con.createStatement();
					 ResultSet res1 = sta1.executeQuery(sql1);
					 while(res1.next()) {
						 int num1 = res1.getInt("NumeroClient");
						 if(num1 == id1) { c = c+1; }
					 }
					 sta1.close();
					 
					 Statement sta2 = con.createStatement();					 
					 ResultSet res2 = sta2.executeQuery(sql2);
					 while(res2.next()) {
						 int num2 = res2.getInt("NumeroProduit");						
						 if(num2 == id2) { d = d+1;}						
					 }
					 sta2.close();
					 if( c!= 0 && d != 0) {
						 PreparedStatement sta = con.prepareStatement(sql);										 					
						 sta.setLong(1,Integer.parseInt(IdCommande.getText()));
						 sta.setDate(2,date);
						 sta.setLong(3, Integer.parseInt(IdClient.getSelectedItem().toString()));
						 sta.setLong(4, Integer.parseInt(IdProduit.getSelectedItem().toString()));
						 sta.executeUpdate();
						 JOptionPane.showMessageDialog(null,"Ajouter avec succès !"); 
						 System.out.println("succès");
					 }
					 
					 if(c == 0 || d == 0) {
						 JOptionPane.showMessageDialog(null,"Client ou Produit inexistant");
					 }
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null, "Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null,"Champ(s) de texte vide(s)");
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
		         Connection con=null;
		         try {		        	 		        
		        	 java.sql.Date date = new java.sql.Date(DateCommande.getDate().getTime());
		        	 
		        	 String sql= "UPDATE commande SET  DateCommande=?, NumeroClient=?, NumeroProduit=? WHERE NumeroCommande=?";
		        	 String sql1 = "SELECT *from client";
		        	 String sql2 = "SELECT *from produit";
		        	 String sql3 = "SELECT *from commande";
		        	 int c=0; int d=0; int e=0;
		        	 int id1 = Integer.parseInt(IdClient.getSelectedItem().toString());
		        	 int id2 = Integer.parseInt(IdProduit.getSelectedItem().toString());
		        	 int id3 = Integer.parseInt(IdCommande.getText());
					 con = DriverManager.getConnection(str1,str2,str3);
					 
					 Statement sta1 = con.createStatement();
					 ResultSet res1 = sta1.executeQuery(sql1);
					 while(res1.next()) {
						 int num1 = res1.getInt("NumeroClient");
						 if(num1 == id1) { c = c+1; }
					 }
					 sta1.close();
					 
					 Statement sta2 = con.createStatement();					 
					 ResultSet res2 = sta2.executeQuery(sql2);
					 while(res2.next()) {
						 int num2 = res2.getInt("NumeroProduit");						
						 if(num2 == id2) { d = d+1;}						
					 }
					 sta2.close();
					 
					 Statement sta3 = con.createStatement();
					 ResultSet res3 = sta3.executeQuery(sql3);
					 while(res3.next()) {
						 int num3 = res3.getInt("NumeroCommande");
						 if(num3 == id3) { e = e+1; }
					 }
					 sta3.close();
					 
					 if( c!= 0 && d != 0 && e !=0) {
						 PreparedStatement sta = con.prepareStatement(sql);										 					
						 sta.setLong(4,Integer.parseInt(IdCommande.getText()));
						 sta.setDate(1,date);
						 sta.setLong(2, Integer.parseInt(IdClient.getSelectedItem().toString()));
						 sta.setLong(3, Integer.parseInt(IdProduit.getSelectedItem().toString()));
						 sta.executeUpdate();
						 JOptionPane.showMessageDialog(null,"Modifier avec succès !"); 
						 System.out.println("succès");
					 }
					 
					 if(c == 0 || d == 0 || e == 0) {
						 JOptionPane.showMessageDialog(null,"Client, Produit ou Commande inexistant");
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
		         Connection con=null;
		         try {
		        	 
		        	 String sql= "DELETE FROM commande WHERE NumeroCommande =?";
		        	 String sql1= "SELECT *from Commande";
		        	 int c=0;
		        	 int id = Integer.parseInt(IdCommande.getText());
					 con = DriverManager.getConnection(str1,str2,str3);
					
					 
					 PreparedStatement sta1 = con.prepareStatement(sql1);
					 ResultSet res = sta1.executeQuery();
					 while(res.next()) {
						 int num = res.getInt("NumeroCommande");
						 if(num == id) {
							 c=c+1;
							 PreparedStatement sta = con.prepareStatement(sql);
							 sta.setLong(1,Integer.parseInt(IdCommande.getText()));
							 sta.executeUpdate(); 
							 JOptionPane.showMessageDialog(null,"Supprimer avec succès !");
							 break;
							 }
					 }
					 if(c ==0) {
						 JOptionPane.showMessageDialog(null,"Commande inexistante!");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e);
				}catch (NumberFormatException e) {
					
					JOptionPane.showMessageDialog(null,"Erreur: Insertion caractère alphabétique dans un champs de texte numérique ");
				}catch (NullPointerException e) {
					
					JOptionPane.showMessageDialog(null, "Champ(s) de texte vide(s)");
				} }
		    }
		}
		Ajouter.addActionListener(new EcouteurBouton());
		Modifier.addActionListener(new EcouteurBouton());
		Supprimer.addActionListener(new EcouteurBouton());
		Actualiser.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {UpdateTable();}});	
		

		table.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			public void mouseClicked(MouseEvent e) {
				// mettre les données dans leurs zones de textes correspondantes quand une ligne est sélectionnée
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				
				String idCommande =( model.getValueAt(table.getSelectedRow(),0).toString());
				Date dateCommande = null;
				try {
					dateCommande = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(table.getSelectedRow(),1).toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String idClient = model.getValueAt(table.getSelectedRow(),2).toString();
				String idProduit = model.getValueAt(table.getSelectedRow(),3).toString();
				
				//insertion dans la zone de texte corresponde
				
				IdCommande.setText(idCommande);
				DateCommande.setDate(dateCommande);
				IdClient.addItem(idClient);
				IdProduit.addItem(idProduit);
			}
		});
		
		
	}
	public void UpdateTable() {
		String sql = "SELECT *from Commande;";
		try {
			 sta = con.prepareStatement(sql);
			ResultSet res = sta.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(res));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,e1);
		}
	}	
	public void combobox_client() {
		String sql="select * from client";
		try {
			sta=con.prepareStatement(sql);
			res=sta.executeQuery();
			while(res.next()) {
				String NumeroClient=res.getString("NumeroClient").toString();
				IdClient.addItem(NumeroClient);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void combobox_produit() {
		String sql="select * from produit";
		try {
			sta=con.prepareStatement(sql);
			res=sta.executeQuery();
			while(res.next()) {
				String NumeroProduit=res.getString("NumeroProduit").toString();
				IdProduit.addItem(NumeroProduit);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}	
}
