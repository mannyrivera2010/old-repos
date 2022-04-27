package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import java.awt.Color;

public class main extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
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
	public main() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 506);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblText = new JLabel("Text");
		lblText.setBounds(10, 11, 46, 14);
		contentPane.add(lblText);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 34, 512, 389);
		contentPane.add(scrollPane);
		
		final JTextArea txtrText = new JTextArea();
		scrollPane.setViewportView(txtrText);
		txtrText.setLineWrap(true);
		txtrText.setFont(new Font("Arial", Font.BOLD, 12));
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrText.setText(encryption.Encoder.Encode(txtrText.getText(), passwordField.getText()));
			}
		});
		btnEncrypt.setBounds(10, 434, 89, 23);
		contentPane.add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtrText.setText(encryption.Encoder.Decode(txtrText.getText(), passwordField.getText()));
			}
		});
		btnDecrypt.setBounds(109, 434, 89, 23);
		contentPane.add(btnDecrypt);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(297, 434, 225, 20);
		contentPane.add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(213, 438, 74, 14);
		contentPane.add(lblPassword);
	}
}
