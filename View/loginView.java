package MVC.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class loginView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField usernameField;
	public JTextField passwordField;
	public JLabel lblUsername;
	public JLabel lblPassword;
	public JButton btnLogin;
	private JLabel lblNewLabel;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loginView frame = new loginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public loginView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUsername = new JLabel("Tên người dùng");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(10, 96, 115, 25);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel("Mật khẩu");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(10, 143, 115, 25);
		contentPane.add(lblPassword);
		
		usernameField = new JTextField();
		usernameField.setBounds(135, 100, 242, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(135, 147, 242, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		btnLogin = new JButton("Đăng nhập");
		btnLogin.setBounds(140, 200, 131, 23);
		contentPane.add(btnLogin);
		
		lblNewLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(101, 47, 226, 38);
		contentPane.add(lblNewLabel);
		
	    setVisible(true);
	}
}
