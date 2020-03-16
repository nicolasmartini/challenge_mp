package challenge_mp;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import java.awt.Color;

/**
 * Clase Login 
 * @author N1C0
 *
 */
public class Login extends JFrame {
	private JTextField txtUser;
	private JTextField txtPass;
	private Login context;
	private Store store;
	
	public Login() {
		
		context = this;
		
	PanelImagen panel_login = new PanelImagen("./src/imagenes/fondo_login.jpg");			    
		getContentPane().add(panel_login, BorderLayout.CENTER);
		panel_login.setLayout(new BorderLayout(0, 20));
		
		JLabel lblNewLabel = new JLabel("Ingrese datos de su cuenta de Gmail");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_login.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel_login.add(panel, BorderLayout.CENTER);
		panel.setOpaque(false);		
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(50);
		panel_1.setPreferredSize(new Dimension(panel_1.getWidth(),100));
		panel_1.setOpaque(false);	
		panel.add(panel_1, BorderLayout.NORTH);
		
		JLabel lblNewLabel_1 = new JLabel();			
		ImageIcon ima = new ImageIcon("./src/imagenes/user.png");
		Icon icon = new ImageIcon(ima.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
		lblNewLabel_1.setIcon(icon);
		panel_1.add(lblNewLabel_1);
		
		
		txtUser = new JTextField();
		txtUser.setText("nicolasarielmartini@gmail.com");
		panel_1.add(txtUser);
		txtUser.setOpaque(false);
		txtUser.setColumns(30);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);	
		panel.add(panel_2, BorderLayout.CENTER);
		
		JLabel lblNewLabel_2 = new JLabel();
		ImageIcon ima2 = new ImageIcon("./src/imagenes/lock.png");
		Icon icon2 = new ImageIcon(ima2.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
		lblNewLabel_2.setIcon(icon2);
		panel_2.add(lblNewLabel_2);
		
		txtPass = new JPasswordField();
		panel_2.add(txtPass);
		txtPass.setOpaque(false);
		txtPass.setColumns(30);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		panel_3.setForeground(Color.WHITE);
		panel_3.setBackground(Color.WHITE);
		panel_3.setOpaque(false);
		panel.add(panel_3, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.WHITE);
		ImageIcon ima3 = new ImageIcon("./src/imagenes/aceptar.png");
		Icon icon3 = new ImageIcon(ima3.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
		btnNewButton.setIcon(icon3);
		btnNewButton.setOpaque(false);
		btnNewButton.setBorder(null);
		panel_3.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			private Principal p;

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String msg = validar();
				if (msg == null) {
										
					if (iniciar_seccion()) {
						
						p = new Principal(store,context);
						
						
					}else {
						
						JOptionPane.showMessageDialog(context,"Error al inicar session", "Error",JOptionPane.ERROR_MESSAGE);
					}
					
				}else {
					
					JOptionPane.showMessageDialog(context,msg, "Error",JOptionPane.ERROR_MESSAGE);
				}
				
			}

			private String validar() {
				
				String user = txtUser.getText();
				String pass = txtPass.getText();
				
				if (user== null || user.trim().equals("")) {
					return "Debe ingresar un correo electrónico";
					
				}
				
				if (!user.contains("@gmail.com")) {
					
					return  "Debe ingresar un correo válido de Gmail";
				}
				
				if (pass == null || pass.trim().equals("")) {
					
					return "Debe ingresar una contraseña";
				}
				
				
				
				return null;
			}
		});
		
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setBorder(null);
		ImageIcon ima4 = new ImageIcon("./src/imagenes/cancelar.png");
		Icon icon4 = new ImageIcon(ima4.getImage().getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING));
		btnNewButton_1.setIcon(icon4);
		btnNewButton_1.setOpaque(false);
		panel_3.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.dispose();
				
			}
		});
		
		this.setSize(new Dimension(600,350));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.repaint();

	}
	
	 private boolean iniciar_seccion() {
		// Se obtiene la Session
       Properties prop = new Properties();
       prop.setProperty("mail.pop3.starttls.enable", "false");
       prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
       prop.setProperty("mail.pop3.socketFactory.fallback", "false");
       prop.setProperty("mail.pop3.port", "995");
       prop.setProperty("mail.pop3.socketFactory.port", "995");
       Session sesion = Session.getInstance(prop);
       
       String user = txtUser.getText();
	String pass = txtPass.getText();
       // sesion.setDebug(true);

     
         // Se obtiene el Store y el Folder, para poder leer el
         // correo.
        
			try {
				store = sesion.getStore("pop3");
				store.connect("pop.gmail.com", user, pass);
				
				System.out.println("Inicio de Seccion");
				
				return true;
			} catch (NoSuchProviderException e) {
				JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
				
			} catch (MessagingException e) {
				JOptionPane.showMessageDialog(this,"Usuario y/o clave incorrecta","Error",JOptionPane.ERROR_MESSAGE);
			}
       
		
			return false;
	}
	
	
	  public static void main(String[] args)
	    {
		  
		  Login l =  new Login();
		  
		  
	    }
}
