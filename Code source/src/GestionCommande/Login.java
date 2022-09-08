package GestionCommande;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Login extends JFrame {
	
	private JPanel contentPane;
	private JTextField login;
	private JPasswordField password;
	public void fermer() {
		dispose();
	}
	public Login() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		this.setLocationRelativeTo(null); //Permet de centrer la fênetre par rapprt à l'écran
		contentPane =   new JPanel();
		contentPane.setBackground(new Color(29, 72, 81)); //new Color(0, 128, 128)
		contentPane.setBorder(new EmptyBorder(0, 0, 10, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(0, 0, 386, 74);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel Lb_connexion = new JLabel("CONNECTION");
		Lb_connexion.setForeground(Color.WHITE);
		Lb_connexion.setFont(new Font("SansSerif", Font.BOLD, 17));
		Lb_connexion.setBounds(122, 34, 122, 16);
		panel.add(Lb_connexion);
		
		JLabel Lblogin = new JLabel("Login");
		Lblogin.setFont(new Font("SansSerif", Font.BOLD, 18));
		Lblogin.setForeground(new Color(211, 211, 211));
		Lblogin.setBounds(42, 142, 84, 27);
		contentPane.add(Lblogin);
		
		JLabel LbMotdepasse = new JLabel("Mot de passe ");
		LbMotdepasse.setForeground(new Color(211, 211, 211));
		LbMotdepasse.setFont(new Font("SansSerif", Font.BOLD, 18));
		LbMotdepasse.setBounds(42, 207, 138, 27);
		contentPane.add(LbMotdepasse);
		
		login = new JTextField();
		login.setBounds(206, 143, 146, 28);
		contentPane.add(login);
		login.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(206, 208, 146, 28);
		contentPane.add(password);
		
		JButton BtnSeConnecter = new JButton("Se connecter");
		BtnSeConnecter.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String log = login.getText();
				String pwd = password.getText();
				final String str1 = "Admin";
				final String str2 = "1234";
				
				if(log.trim().equals(str1) && pwd.trim().equals(str2)) {
					Acceuil a = null;
					try {
						a = new Acceuil();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					a.setVisible(true);
					fermer();
				}
				else {
					
					JOptionPane.showMessageDialog(null,"Login ou Mot de passe invalide...");
				}
			}
		});
		
		BtnSeConnecter.setFont(new Font("SansSerif", Font.PLAIN, 14));
		BtnSeConnecter.setBounds(137, 284, 121, 28);
		contentPane.add(BtnSeConnecter);
		
		
	}
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel()); //Nouveau look des composants bouttons etc
		Login frame = new Login();
		frame.setVisible(true);
		
	}

}
