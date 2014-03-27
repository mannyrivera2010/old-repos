package org.client;

import gnu.crypto.pad.WrongPaddingException;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSlider;

import org.diffie_hellman.DiffieHellman;
import org.shared.encryption.ciphers.AES128CBCPad_Compress;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;


//Test Comment from Manny-PC 
//Test comment from laptop
//Test Comment 4 from pc 


public class Client extends JFrame {
	
    static String server=null;
    final static int servPort = 12011;
    private static Socket echoSocket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    
    static boolean blnGlobalEncryption=false;
    
    static BigInteger globalEncryption_modulus = null;
    static BigInteger globalEncryption_privateKey = null;
    static BigInteger globalEncryption_publicKey=null; 
    static BigInteger globalEncryption_ServerpublicKey=null; 
    static String globalEncryption_sharedSecret = null;
    
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
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
	public Client() {
		setResizable(false);
		setTitle("Echo Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTcpSocketPort = new JLabel("TCP Socket Port #12011");
		lblTcpSocketPort.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTcpSocketPort.setBounds(10, 11, 181, 14);
		contentPane.add(lblTcpSocketPort);
		
		JLabel lblServerIpAddress = new JLabel("Server IP Address or Host Name");
		lblServerIpAddress.setFont(new Font("Arial", Font.PLAIN, 12));
		lblServerIpAddress.setBounds(10, 36, 181, 14);
		contentPane.add(lblServerIpAddress);
		
		final JCheckBox chcBoxEncyption = new JCheckBox("AES 128bit CBC mode using a 256bit Diffie-Hellman key with Java Deflater Compression Encryption");
		chcBoxEncyption.setBounds(153, 8, 610, 23);
		contentPane.add(chcBoxEncyption);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"All Upper Case", "All Lower Case", "String to Hex", "Hex To String", "String to Ascii", "Ascii to String", "No Change"}));
		comboBox.setBounds(10, 370, 219, 20);
		contentPane.add(comboBox);
		
		JLabel lblEchoFromServer = new JLabel("Echo from Server");
		lblEchoFromServer.setFont(new Font("Arial", Font.BOLD, 12));
		lblEchoFromServer.setBounds(10, 60, 181, 14);
		contentPane.add(lblEchoFromServer);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 80, 753, 110);
		contentPane.add(scrollPane);
		
		final JTextArea txtFromServer = new JTextArea();
		txtFromServer.setEditable(false);
		txtFromServer.setLineWrap(true);
		txtFromServer.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(txtFromServer);
		
		final JButton btnReset = new JButton("Reset");
		
		btnReset.setEnabled(false);
		btnReset.setBounds(674, 369, 89, 23);
		contentPane.add(btnReset);
		
		final JTextPane txtServerIP = new JTextPane();
		txtServerIP.setBounds(199, 30, 232, 20);
		contentPane.add(txtServerIP);
		
		final JButton btnConnect = new JButton("Connect");
	
		btnConnect.setBounds(674, 33, 89, 23);
		contentPane.add(btnConnect);
		
		final JButton btnSubmit = new JButton("Submit");
		btnSubmit.setEnabled(false);
		btnSubmit.setBounds(267, 369, 89, 23);
		contentPane.add(btnSubmit);
		
		JLabel lblMessageToServer = new JLabel("Message to Server");
		lblMessageToServer.setFont(new Font("Arial", Font.BOLD, 12));
		lblMessageToServer.setBounds(10, 197, 118, 14);
		contentPane.add(lblMessageToServer);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 223, 753, 136);
		contentPane.add(scrollPane_1);
		
		final JTextArea txtToServer = new JTextArea();
		txtToServer.setLineWrap(true);
		txtToServer.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane_1.setViewportView(txtToServer);
		
		
		/*/////////////////////
		 * Action Listeners
		 *////////////////////
		
		//btnSubmit
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//btnSubmit CODE
				System.out.println("btnSubmit push");
				System.out.println((String)comboBox.getSelectedItem());
				
			
			    try {
					txtFromServer.setText(TCP_Command(txtToServer.getText(),(String)comboBox.getSelectedItem()));
				} catch (IOException e) {
					// TODO Auto-generated catch block		
					e.printStackTrace();
					btnReset.doClick();
					txtFromServer.setText("Lost connection to Server");
					
				}

			}//end void actionPerformed
		});
		
		//btnReset
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					echoSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				btnConnect.setEnabled(true);
				chcBoxEncyption.setEnabled(true);
				chcBoxEncyption.setSelected(false);
				txtServerIP.setText("");
				txtFromServer.setText("");
				txtToServer.setText("");
				txtServerIP.setEnabled(true);
				btnSubmit.setEnabled(false);
				btnReset.setEnabled(false);
			}
		});
		
		//btnConnect
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				server=txtServerIP.getText().trim();
				blnGlobalEncryption=chcBoxEncyption.isSelected();
				
				if(createSocket()){
					//Connected Successful
					txtFromServer.setText("Connected Successful");
					btnSubmit.setEnabled(true);
					btnReset.setEnabled(true);
					btnConnect.setEnabled(false);
					chcBoxEncyption.setEnabled(false);
					txtServerIP.setEnabled(false);
					
					
					if(blnGlobalEncryption){
						globalEncryption_modulus = DiffieHellman.gen256bitRandomBigInt();
						globalEncryption_privateKey = DiffieHellman.gen256bitRandomBigInt();
						globalEncryption_publicKey = DiffieHellman.BASE_5.generatePublicKey(globalEncryption_privateKey, globalEncryption_modulus);
						
						
						String TempPublicKey = null;
						try {
							TempPublicKey = TCP_Command(globalEncryption_modulus+","+globalEncryption_publicKey,"SET");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							btnReset.doClick();
							txtFromServer.setText("Lost connection to Server");
						}
						//System.out.println(">>>>>>"+TempPublicKey);
						
						globalEncryption_ServerpublicKey=new BigInteger(TempPublicKey);
						//Send server modulus and Client public key to get Server Public Key
						globalEncryption_sharedSecret=DiffieHellman.getSharedSecretKey(globalEncryption_privateKey, globalEncryption_modulus, globalEncryption_ServerpublicKey).toString();
						//System.err.print(globalEncryption_sharedSecret);
					}
					
					
				}else{
					txtFromServer.setText("Can't Connect to Server");
				}
				
	
			}//end void actionPerformed
		});
	}//end Create Frame
	
	/**
	 * CreateSocket
	 * @return
	 */
	public static boolean createSocket() {
		boolean blnConnected=false;
		
		if(server.trim().length()==0){
			return blnConnected;
		}
				
		try {
			blnConnected=true;
            echoSocket = new Socket(server, servPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + server);
            blnConnected=false;
            //System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + server);
            blnConnected=false;
            
        }
		
		return blnConnected;
	}//end sub
	
	
	/**
	 * Sending Commands to Server
	 * @param Input
	 * @param StrStatus
	 * @return
	 * @throws WrongPaddingException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 */
	public static String TCP_Command(String Input,String StrStatus) throws IOException{
     	String userInput=Input;
    	String Output="";
        try {
        		String Command="";
        		
        	    if(StrStatus.equalsIgnoreCase("ALL UPPER CASE")){
        	    	Command="UC|";
        	    }
        	    else if(StrStatus.equalsIgnoreCase("ALL LOWER CASE")){
        	    	Command="LC|";
        	    }        	    
        	    else if(StrStatus.equalsIgnoreCase("STRING TO HEX")){
        	    	Command="SH|";
        	    }
        	    else if(StrStatus.equalsIgnoreCase("HEX TO STRING")){
        	    	Command="HS|";
        	    }
        	    else if(StrStatus.equalsIgnoreCase("STRING TO ASCII")){
        	    	Command="SA|";
        	    }
        	    else if(StrStatus.equalsIgnoreCase("ASCII TO STRING")){
        	    	Command="AS|";
        	    }
        	    else if(StrStatus.equalsIgnoreCase("NO CHANGE")){
        	    	Command="NO|";
        	    }
        	    else if(StrStatus.equalsIgnoreCase("SET")){
        	    	Command="SET|";
        	    }
        	    else{
        	    	Command="??|";
        	    }
        
        	    AES128CBCPad_Compress AesEn = new AES128CBCPad_Compress();
        	    
        	    if(blnGlobalEncryption==true&&!StrStatus.equals("SET")){
         	    	userInput=AesEn.AES_encrypt(userInput,globalEncryption_sharedSecret );
        	    }
        	    
                out.println(org.shared.EncodeWeak.String2Hex(Command+userInput));//Out to Server
              
                
                String StrBackFromServer=org.shared.EncodeWeak.Hex2String(in.readLine());//Back from Server
                
                if(blnGlobalEncryption==true&&!StrStatus.equals("SET")){
                	//Encrypted
                	Output=AesEn.AES_decrypt(StrBackFromServer, globalEncryption_sharedSecret);//Back from Server
                }else{
                	//Not Encrypted
                	Output=StrBackFromServer;
                }

        } catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return Output;	
    }//end sub
}
