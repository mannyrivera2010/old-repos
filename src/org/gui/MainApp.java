package org.gui;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JButton;

import javax.swing.JScrollPane;
import java.awt.Font;

import javax.swing.ScrollPaneConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import org.core.EventMutliThread.DirectoryEventController;
import org.enums.DatabaseEngineEnum;
import org.output.html.WebSiteCreator;
import org.shared.IniEngine;

// TODO: Auto-generated Javadoc
/**
 * The Class MainApp.
 */
public class MainApp extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6572794611399567594L;
	/*
	 * Variables
	 */
	/** The Int wait. */
	static int IntWait = 30;  //Number of seconds of timer
	
	/** The int tick. */
	int intTick = 0;
	
	//Used for Timer.  For which ever one istrue. it will automatically create webpages 
	/** The Grant list history. */
	static boolean GrantListHistory = false;
	
	/** The Folder grant list history. */
	static boolean FolderGrantListHistory = false;
	
	/** The Folder files history. */
	static boolean FolderFilesHistory = false;
	
	/** The Individual files history. */
	static boolean IndividualFilesHistory = false;
	
	/** The Processed grant list history. */
	static boolean ProcessedGrantListHistory = false;
	
	/** The Complete web history. */
	static boolean CompleteWebHistory = false;

	//Used for comparing each directory, and creating logs to mail and store in database
	//It is set to use the Sqlite4Wrapper driver for sqlite database 
	/** The De c. */
	DirectoryEventController DeC = new DirectoryEventController(DatabaseEngineEnum.SQLITE4WRAPPER);
	
	/** The total web. */
	private WebSiteCreator totalWeb=new WebSiteCreator(); // used for website creation
	
	/** The prerequistie. */
	private PreLoad prerequistie = new PreLoad();  //Checks if files needed files exist
	
	/** The content pane. */
	private JPanel contentPane;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp frame = new MainApp();
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

	Timer timer;
	
	/** The update pro bar. */
	ActionListener updateProBar;
	
	/** The pwd txtpass. */
	private JPasswordField pwdTxtpass;
	
	/**
	 * Instantiates a new main app.
	 */
	public MainApp() {
		prerequistie.CheckForSettings(); //Check for Files		
		
		IniEngine iniS= new IniEngine(); //for ini File setting
		IntWait = iniS.getIniIntValue("Settings.ini","Timer","Wait");
		
		if(iniS.getIniStringValue("Settings.ini","Timer","GrantListHistory").contains("on")){
			GrantListHistory = true;
		}
		
		if(iniS.getIniStringValue("Settings.ini","Timer","FolderFilesHistory").contains("on")){
			 FolderFilesHistory= true;
		}
		
		if(iniS.getIniStringValue("Settings.ini","Timer","IndividualFilesHistory").contains("on")){
			IndividualFilesHistory = true;
		}
		
		if(iniS.getIniStringValue("Settings.ini","Timer","ProcessedGrantListHistory").contains("on")){
			ProcessedGrantListHistory = true;
		}
		
		if(iniS.getIniStringValue("Settings.ini","Timer","CompleteWebHistory").contains("on")){
			CompleteWebHistory = true;
		}

		final TrayIcon trayIcon;

		if (SystemTray.isSupported()) {

			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage(
					"resources/images/bulb.gif");
		
			MouseListener mouseListener = new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					System.out.println("Tray Icon - Mouse clicked!");
				}

				public void mouseEntered(MouseEvent e) {
					System.out.println("Tray Icon - Mouse entered!");
				}

				public void mouseExited(MouseEvent e) {
					System.out.println("Tray Icon - Mouse exited!");
				}

				public void mousePressed(MouseEvent e) {
					System.out.println("Tray Icon - Mouse pressed!");
				}

				public void mouseReleased(MouseEvent e) {
					System.out.println("Tray Icon - Mouse released!");
				}
			};

			ActionListener showListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Show");
					setVisible(true);
				}
			};

			ActionListener hideListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Hide");
					setVisible(false);
				}
			};

			//History Listeners
			/*
			 
				public void actionPerformed(ActionEvent e) {
					System.out.println("GrantListListener");
					totalWeb.makeHTMLGrantListHistory();
				}
			};
			*/
			
			ActionListener FolderFilesListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("FolderFilesListener");
					totalWeb.makeHTMLFolderFilesHistory();
				}
			};
			
			ActionListener IndividualFilesListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("IndividualFilesListener");
					totalWeb.makeHTMLIndividualFilesHistory();
				}
			};
			
			ActionListener ProcessedGrantListListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("ProcessedGrantListListener-NA");
					totalWeb.makeHTMLProcessedGrantList();
				}
			};
			
			ActionListener CompleteWebListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("CompleteWebListener-NA");
					totalWeb.CreateIndex();					
				}
			};
			//end History Listeners
			
			PopupMenu popup = new PopupMenu();

			MenuItem defaultItemShow = new MenuItem("Show");
			defaultItemShow.addActionListener(showListener);
			popup.add(defaultItemShow);

			MenuItem defaultItemHide = new MenuItem("Hide");
			defaultItemHide.addActionListener(hideListener);
			popup.add(defaultItemHide);

			//Historys
			/*
			MenuItem defaultGrantListItem = new MenuItem("Grant List");
			defaultGrantListItem.addActionListener(GrantListListener);
			popup.add(defaultGrantListItem);
			*/
			
			MenuItem defaultFolderFilesHistoryItem = new MenuItem("Folder Files");
			defaultFolderFilesHistoryItem.addActionListener(FolderFilesListener);
			popup.add(defaultFolderFilesHistoryItem);
			
			MenuItem defaultIndividualFilesItem = new MenuItem("Individual Files");
			defaultIndividualFilesItem.addActionListener(IndividualFilesListener);
			popup.add(defaultIndividualFilesItem);
			
			MenuItem defaultProcessedGrantListItem = new MenuItem("Processed Grant List");
			defaultProcessedGrantListItem.addActionListener(ProcessedGrantListListener);
			popup.add(defaultProcessedGrantListItem);
			
			MenuItem defaultCompleteWebItem = new MenuItem("Complete Web");
			defaultCompleteWebItem.addActionListener(CompleteWebListener);
			popup.add(defaultCompleteWebItem);
			
			//End Historys

			trayIcon = new TrayIcon(image, "Tray", popup);

			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					trayIcon.displayMessage("Action Event",
							"An Action Event Has Been Performed!",
							TrayIcon.MessageType.INFO);
				}
			};

			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(actionListener);
			trayIcon.addMouseListener(mouseListener);

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println("TrayIcon could not be added.");
			}

		} else {

			// System Tray is not supported

		}

		// //////////////////
		// //////////////////
		// /////////////////

		setResizable(false);
		//Table1.loadDataBase("event.csv");// Initiate

		setTitle("Grant Process Monitor with Email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 392);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 775, 281);
		contentPane.add(scrollPane);
		
				final JTextArea textArea = new JTextArea();
				scrollPane.setViewportView(textArea);
				textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
				textArea.setEditable(false);

		pwdTxtpass = new JPasswordField();
		pwdTxtpass.setBounds(95, 303, 117, 20);
		contentPane.add(pwdTxtpass);

		final JTextPane txt_rest_password = new JTextPane();
		txt_rest_password.setBounds(10, 336, 664, 20);
		contentPane.add(txt_rest_password);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 306, 87, 14);
		contentPane.add(lblPassword);

		JButton btnNewButton = new JButton("Create Password");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				txt_rest_password.setText(org.shared.encryption.Encoder
						.Encode(pwdTxtpass.getText()));
			}
		});
		btnNewButton.setBounds(222, 302, 142, 23);
		contentPane.add(btnNewButton);
		
		final JLabel lblLbltimerstatus = new JLabel("Timer: Loading");
		lblLbltimerstatus.setBounds(557, 306, 117, 14);
		contentPane.add(lblLbltimerstatus);

		Runnable runner = new Runnable() {
			public void run() {
				// Timer.setLogTimers(true);
				ActionListener actionListener = new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {

						intTick++;
						lblLbltimerstatus.setText("Timer:"+intTick+"<"+IntWait);
						System.out.println("Timer:"+intTick+"<"+IntWait);

						if (intTick >= IntWait) {
							System.out.println("<-*-*-*-*-*-*-*-*-*-*-*-*-*->");

							DeC.runActions();//Run Events
							//This starts the process.
							//It Gets the list of directories,compares each one, email and log the events to database 

							try {
								textArea.append(DeC.getLogs());//Append Results of directory events to textArea 
							} catch (Exception e) {
								e.printStackTrace();
							} 
		
							/*
							if (GrantListHistory == true) {
								totalWeb.makeHTMLGrantListHistory();
							}
							*/

							if (FolderFilesHistory == true) {
								totalWeb.makeHTMLFolderFilesHistory();
							}
							
							if (IndividualFilesHistory == true) {
								totalWeb.makeHTMLIndividualFilesHistory();
							}
							
							if (ProcessedGrantListHistory == true) {
								//org.logOutput.Engine.printHTMLProcessedGrantList();
								System.out.println("ProcessedGrantListHistory");
								totalWeb.makeHTMLProcessedGrantList();
							}
							
							if (CompleteWebHistory== true) {
								//org.logOutput.Engine.printHTMLIndividualFilesHistory();
								System.out.println("CompleteWebHistory");
								totalWeb.CreateIndex();
							}
						

							intTick = 0;
							System.out.println("<-*-*-*-*-*-*-*-*-*-*-*-*-*->");
						}// end if tick
					}// end actionPerformed
				};//end ActionListener

				Timer timer = new Timer(1000, actionListener);
				timer.start();
			}//end public void run()
		};//end Runnable
		EventQueue.invokeLater(runner);
	}
}//end class
