package org.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
	
		final JLabel lblLblseconds = new JLabel("lblSeconds");
		lblLblseconds.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLblseconds.setBounds(10, 11, 414, 30);
		panel.add(lblLblseconds);
		
		
		JButton btnTest = new JButton("Test1");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Timing TimTiming=new Timing();
				
				Double average=0.0;
				
				for (int kl = 0; kl < 1000; kl++) {
					//
					TimTiming.start();
					ArrayList<Double> ArlOne= new ArrayList<Double>();
					for (int i = 0; i < 5000; i++) {
						ArlOne.add((double) i);
					}
					
					
					ArrayList<Double> ArlTwo= new ArrayList<Double>();
					for (int i = 0; i < 5000; i++) {
						ArlTwo.add((double) i);
					}
					
					
					
					for (int j = 0; j < ArlOne.size(); j++) {
						for (int i = 0; i < ArlTwo.size(); i++) {
							double temp1=ArlOne.get(j);
							double temp2=ArlTwo.get(i);
							double temp3=temp1+temp2;
							double temp4=temp1*temp2;
							double temp5=temp1-temp2;
							double temp6=temp1/temp2;
						}
					}
					
					double dblTime=TimTiming.stop_SecDouble();
					average+=dblTime;
					//
				}
				
				DecimalFormat decim = new DecimalFormat("#.##");
				double score=Double.parseDouble(decim.format((average/1000)*1717));
		

				
				
				lblLblseconds.setText("Score:"+score);
			}
		});
		btnTest.setBounds(10, 215, 89, 23);
		panel.add(btnTest);
		
		
	}
}
