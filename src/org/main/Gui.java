package org.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.performance.Timing;
import org.shared.PersistentDirChanges;
import org.shared.Util;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Gui extends JFrame {

	private JPanel contentPane;

	//Text Fields
	private static JTextField txtInputDirectory;
	private static JTextField txtOutputDirectory;

	//Text Area
	private static JTextArea txtStatus;

	//Labels
	private static JLabel lblPercent;
	private static JLabel lblFilescount;
	private static JLabel lblEta;
	private static JLabel lblFilePerSecond;

	//Buttons
	private static JButton btnStopProcess;
	private static JButton btnProcess;

	//ProgressBars
	private static JProgressBar progressBar;

	//Menu
	private JMenu mnFile;
	private JMenuItem mntmSelectInputDirectory;
	private JMenuItem mntmSelectOutputDirectory;
	private JMenuItem mntmExit;

	//Global Variables
	static boolean blnBreakMainLoop=false;
	static String SysLine= System.getProperty("line.separator").toString();
	static Util UtilObj= new Util();

	static enum Mode{CONSOLE,GUI};
	static Mode CurrentMode=null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Options opt = new Options();

			opt.addOption("h","help", false, "Print help for this application");
			opt.addOption("in", "input", true, "Input folder name");
			opt.addOption("out","ouput", true, "Input output name");
			opt.addOption("i","interactive", false, "Interactive mode");
			opt.addOption("g","gui", false, "Gui mode");

			GnuParser parser = new GnuParser();
			CommandLine cl = parser.parse(opt, args);

			if (cl.hasOption("h")) {
				HelpFormatter f = new HelpFormatter();
				f.printHelp("OptionsTip", opt);
			}else if(cl.hasOption("i")){
				//Run Console
				//System.out.println("Run Console");
				CurrentMode= Mode.CONSOLE;
				runConsole();
			}else if(cl.hasOption("g")){
				//Run Gui
				//System.out.println("Run Gui");
				CurrentMode= Mode.GUI;
				runGui();

			}else if(cl.hasOption("in")&&cl.hasOption("out")){
				//Command Mode
				String input = cl.getOptionValues("in").toString();
				String output = cl.getOptionValue("out").toString();
				//System.out.println("input:"+input+"\toutput"+output);

				CurrentMode= Mode.CONSOLE;

				try {
					StartProcess(input,output);
				} catch (Exception e) {
					outputToSystemAndGui("Could not find Directories");
					
					if(CurrentMode==Mode.GUI){
						
						JOptionPane.showMessageDialog(null, "Could not find Directories", "Error",JOptionPane.ERROR_MESSAGE);
					}
					

					//e.printStackTrace();
					return;
				};



			}else {
				//run GUI
				CurrentMode= Mode.GUI;
				runGui();
			}
		}
		catch (ParseException e) {
			System.err.println(e.getMessage());
			//e.printStackTrace();
		}


	}

	static void runConsole(){

		Scanner scanIn = new Scanner(System.in);

		System.out.println("Welcome to the WordPress ASP to HTML Converter System");
		UtilObj.PrintSepLine();
		System.out.println("\tType in \"1\" for the default path"+SysLine);

		System.out.println("Enter Input directory (FULL PATH  H:\\dir1\\dir2\\ ):");
		System.out.println("\t1=\t\\\\www1\\dailydigest$\\Databases\\News");
		System.out.println("\t2=\tinput");
		String InputDir= scanIn.nextLine().trim();

		if (InputDir.equals("1")){
			InputDir="\\\\www1\\dailydigest$\\Databases\\News";
		}else if(InputDir.equals("2")){
			InputDir="input";
		}

		System.out.println("Enter Ouput directory (FULL PATH  H:\\dir1\\dir2\\ ):");
		System.out.println("\t1=\tC:\\TTT");
		System.out.println("\t2=\toutput");
		String OutputDir= scanIn.nextLine().trim();

		if (OutputDir.equals("1")){
			OutputDir="C:\\TTT";
		}else if(OutputDir.equals("2")){
			OutputDir="output";
		}

		UtilObj.PrintSepLine();
		System.out.println("Verify Directory Locations");
		System.out.println("\tInput Directory="+InputDir);
		System.out.println("\tOuput Directory="+OutputDir);

		if(InputDir.equals(OutputDir)){
			System.out.println("ERROR-Output Directory can not be the same as Input Directory");
			scanIn.close();
			return;

		}

		UtilObj.PrintSepLine();
		UtilObj.PrintSepLine();
		try {
			StartProcess(InputDir,OutputDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UtilObj.PrintSepLine();
		System.out.println("DONE");
		scanIn.close();

	}

	static void runGui(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public static void guiFormInProcess(){
		lblFilescount.setText("0 out of 0 files processed");
		lblPercent.setText("Percent: 0.00%");
		lblEta.setText("Estimate Time (HH:MM:SS) = 00:00:00");
		lblFilePerSecond.setText("File Per Second : 0");
		progressBar.setMaximum(0);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		btnProcess.setEnabled(false);
		btnStopProcess.setEnabled(true);
		txtInputDirectory.setEnabled(false);
		txtOutputDirectory.setEnabled(false);
		blnBreakMainLoop=false;
	}

	public static void guiFormFinished(){
		txtInputDirectory.setText("");
		txtOutputDirectory.setText("");
		txtInputDirectory.setEnabled(true);
		txtOutputDirectory.setEnabled(true);
		btnProcess.setEnabled(true);
		btnStopProcess.setEnabled(false);
	}

	public static String jFolderChooser(){
		//File Chooser
		JFileChooser chooser;
		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//    
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			/*
			System.out.println("getCurrentDirectory(): " 
					+  chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : " 
					+  chooser.getSelectedFile());

			 */
			return chooser.getSelectedFile().toString();
		}
		else {
			//System.out.println("No Selection");
			return "No Selection";
		}


	}//end sub

	/**
	 * Create the frame.
	 */
	public Gui() {
		final String Version="3.1";

		setResizable(false);
		setTitle("WordPress Processor V"+Version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 620);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmSelectInputDirectory = new JMenuItem("Select Input Directory");
		mntmSelectInputDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtInputDirectory.setText(jFolderChooser());
			}
		});
		mnFile.add(mntmSelectInputDirectory);

		mntmSelectOutputDirectory = new JMenuItem("Select Output Directory");
		mntmSelectOutputDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtOutputDirectory.setText(jFolderChooser());
			}
		});
		mnFile.add(mntmSelectOutputDirectory);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				if(CurrentMode==Mode.GUI){
					JOptionPane.showMessageDialog(null, "Created by Emanuel Rivera (2012)\n Version "+Version, "About",JOptionPane.PLAIN_MESSAGE);
				}
				
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		progressBar = new JProgressBar(0,100);
		progressBar.setForeground(new Color(0, 0, 128));
		progressBar.setStringPainted(true);
		progressBar.setBounds(10, 497, 664, 33);
		contentPane.add(progressBar);

		btnProcess = new JButton("Process Files");
		btnProcess.addActionListener(new ActionListener() {
			/*
			 * BtnProcess
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent arg0) {
				guiFormInProcess();

				String strInputDirectory=txtInputDirectory.getText().trim();
				String strOutputDirectory=txtOutputDirectory.getText().trim();


				if(strInputDirectory.length()==0
						||strOutputDirectory.length()==0){
					outputToSystemAndGui("Nothing to process");

					if(CurrentMode==Mode.GUI){
						JOptionPane.showMessageDialog(null, "Nothing to process, Enter a directory", "Error",JOptionPane.ERROR_MESSAGE);
					}
					
					guiFormFinished();
					return;	
				}

				if(strInputDirectory.equals("1")&&strOutputDirectory.equals("1")){
					//Choose 1
					strInputDirectory="\\\\www1\\dailydigest$\\Databases\\News";

				}else if(strInputDirectory.equals("2")&&strOutputDirectory.equals("2")){
					//Choose 2
				}else if(strInputDirectory.equals(strOutputDirectory)){					
					outputToSystemAndGui("Output Directory can not be the same as Input Directory");
					
					if(CurrentMode==Mode.GUI){
						JOptionPane.showMessageDialog(null, "Output Directory can not be the same as Input Directory", "Error",JOptionPane.ERROR_MESSAGE);
					}
					
					guiFormFinished();
					return;	
				}

				StartProcessThread(strInputDirectory,strOutputDirectory);
			}
		});

		btnProcess.setBounds(95, 121, 164, 23);
		contentPane.add(btnProcess);

		JLabel lblInputDirectory = new JLabel("Enter Input directory (FULL PATH  H:\\dir1\\dir2\\)");
		lblInputDirectory.setBounds(10, 0, 424, 23);
		contentPane.add(lblInputDirectory);

		txtInputDirectory = new JTextField();
		txtInputDirectory.setBounds(10, 36, 664, 20);
		contentPane.add(txtInputDirectory);
		txtInputDirectory.setColumns(10);

		JLabel lblInputdirectory = new JLabel("1=\\\\www1\\dailydigest$\\Databases\\News");
		lblInputdirectory.setBounds(20, 22, 280, 14);
		contentPane.add(lblInputdirectory);

		JLabel lblEnterOutputDirectory = new JLabel("Enter Output directory (FULL PATH  H:\\dir1\\dir2\\)");
		lblEnterOutputDirectory.setBounds(10, 55, 424, 23);
		contentPane.add(lblEnterOutputDirectory);

		JLabel lblcttt = new JLabel("1=C:\\TTT");
		lblcttt.setBounds(20, 75, 280, 14);
		contentPane.add(lblcttt);

		txtOutputDirectory = new JTextField();
		txtOutputDirectory.setBounds(10, 90, 664, 20);
		contentPane.add(txtOutputDirectory);
		txtOutputDirectory.setColumns(10);

		btnStopProcess = new JButton("Stop Process");
		btnStopProcess.setEnabled(false);
		btnStopProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				blnBreakMainLoop=true;
			}
		});
		btnStopProcess.setBounds(390, 121, 162, 23);
		contentPane.add(btnStopProcess);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 155, 664, 312);
		contentPane.add(scrollPane);

		txtStatus = new JTextArea();
		txtStatus.setEditable(false);
		scrollPane.setViewportView(txtStatus);

		JLabel lblProgressPercent = new JLabel("Progress");
		lblProgressPercent.setBounds(10, 478, 69, 14);
		contentPane.add(lblProgressPercent);

		lblFilescount = new JLabel("0 out of 0 files processed");
		lblFilescount.setBounds(111, 478, 229, 14);
		contentPane.add(lblFilescount);

		lblPercent = new JLabel("Percent: 0.00%");
		lblPercent.setBounds(479, 478, 96, 14);
		contentPane.add(lblPercent);

		lblEta = new JLabel("Estimate Time (HH:MM:SS) = 00:00:00");
		lblEta.setBounds(10, 544, 370, 14);
		contentPane.add(lblEta);

		lblFilePerSecond = new JLabel("File Per Second : 0");
		lblFilePerSecond.setBounds(390, 544, 162, 14);
		contentPane.add(lblFilePerSecond);
	}//end sub


	public static void outputToSystemAndGui(String input) {
		if(CurrentMode==Mode.GUI){
			System.out.println(input);
			txtStatus.append(input + SysLine);
			txtStatus.setCaretPosition(txtStatus.getDocument().getLength());
		}else if(CurrentMode==Mode.CONSOLE){
			System.out.println(input);
		}
	}//end sub


	public static void StartProcessThread(String InputDir, String OutputDir){
		final String strInputDirectoryThread=InputDir;
		final String strOutputDirectoryThread=OutputDir;

		Thread thread = new Thread() {
			public void run() {
				try {
					StartProcess(strInputDirectoryThread,strOutputDirectoryThread);
				} catch (Exception e) {
					if(CurrentMode==Mode.GUI){
						JOptionPane.showMessageDialog(null, "Could not find Directories", "Error",JOptionPane.ERROR_MESSAGE);
					}
					
					guiFormFinished();
					//e.printStackTrace();
					return;
				};
			}
		};
		thread.start();
	}//end sub


	public static void StartProcess(String InputDir, String OutputDir) throws Exception{	
		outputToSystemAndGui("Starting to process");
		outputToSystemAndGui("System is deleting Previous output directory");
		UtilObj.folderDelete(OutputDir);
		outputToSystemAndGui("System deleted previous output directory");
		UtilObj.folderExistAndMake(OutputDir);
		outputToSystemAndGui("System made a new output directory");


		String Directory_Location=InputDir;

		PersistentDirChanges PDCObj=new PersistentDirChanges(Directory_Location);

		PDCObj.processPreviousFileList();
		PDCObj.processCurrentFileList();

		ArrayList<String> ALFilesToProcess=PDCObj.GetAddedFiles();

		outputToSystemAndGui(">>>>>>Obtain Added File List");

		int max=ALFilesToProcess.size();

		if(max==0){
			outputToSystemAndGui("\tNo new files to process");
			if(CurrentMode==Mode.GUI){
				JOptionPane.showMessageDialog(null, "No new files to process", "Info",JOptionPane.ERROR_MESSAGE);
			}
			
			guiFormFinished();
			return;
		}


		if(CurrentMode==Mode.GUI){
			//Set Max and Min Value Progress bar
			progressBar.setMinimum(0);
			progressBar.setMaximum(ALFilesToProcess.size());	
		}


		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat dfInteger = new DecimalFormat("#");

		Timing Timer=new Timing();

		double dblCurrentCount=0;
		String strHoldingCurrentCount="0";
		double dblTimerTracker=0;


		for(int i = 0;i<max;i++){

			if(blnBreakMainLoop==true){
				outputToSystemAndGui("\tStopped Process. Processed "+ (i) +" files");
				if(CurrentMode==Mode.GUI){
					JOptionPane.showMessageDialog(null, "Stopped Process. Processed "+ (i) +" files", "Info",JOptionPane.ERROR_MESSAGE);
				}
				
				guiFormFinished();
				return;
			}

			Timer.start();
			dblCurrentCount=dblCurrentCount+1.0;

			String AbsoluteFileName=ALFilesToProcess.get(i);
			String strFileName=AbsoluteFileName.substring(AbsoluteFileName.lastIndexOf("\\")+1);

			// System.out.println( "*------------------------*");
			// System.out.println( "File>>" + strFileName );
			ArrayList<String> CurrentProcessing = UtilObj.fileto1dArrayList(AbsoluteFileName);

			String strTitle = getTags(CurrentProcessing,
					"<FONT FACE='Arial' SIZE=3>", "</FONT>").replace(SysLine, "");
			strTitle = strTitle.substring(strTitle.indexOf("<B><C>") + 6,
					strTitle.indexOf("</FONT")).trim();
			// System.out.println(strTitle);

			String strBody = getTagsExclude(CurrentProcessing,
					"<FONT FACE='Arial' SIZE=2>", "</FONT>");
			// System.out.println(strBody);

			String strDistribution = getLine(CurrentProcessing, "Distribution:");
			// System.out.println(strDistribution.trim().length()==0);

			if (strDistribution.trim().length() == 0) {
				strDistribution = "unknown";
				UtilObj.folderExistAndMake(OutputDir + "\\" + strDistribution);
			} else {
				try {
					strDistribution = strDistribution
							.substring(strDistribution.indexOf(":") + 1,
									strDistribution.indexOf("</FONT>")).trim()
									.replace("/", "_");
				} catch (Exception E) {
					strDistribution = "error";
				}

				UtilObj.folderExistAndMake(OutputDir + "\\" + strDistribution);
			}
			// System.out.println(">>>>"+strDistribution+"<<<<");

			StringBuilder HtmlOut = new StringBuilder();
			HtmlOut.append("<HTML>" + SysLine + "<HEAD><TITLE>" + strTitle
					+ "</TITLE></HEAD>" + SysLine);
			HtmlOut.append("<BODY>" + SysLine + strBody + SysLine + "</BODY>"
					+ SysLine);
			HtmlOut.append("</HTML>");

			//String Sep = ";;;";
			// System.out.println(f.getAbsolutePath()+Sep+strFileName+Sep+strTitle+Sep+strBody+Sep+strDistribution+Sep+HtmlOut);

			String OutputFileLoc = OutputDir + "\\" + strDistribution + "\\"
					+ strFileName + ".html";
			UtilObj.writeStringToFile(HtmlOut.toString(), OutputFileLoc, false);

			UtilObj.ChangeFileModifiedDate(OutputFileLoc);

			double dblI=i+1;
			double dblmax=max;
			double Left=dblmax-dblI;
			double percent = dblI/dblmax;
			String strPercent = df.format(percent*100); 

			outputToSystemAndGui("Done Processing File >> " + AbsoluteFileName+"");

			System.out.println("\t\t("+(i+1)+" out of "+(ALFilesToProcess.size())+") > "+strPercent+"% >> File per second: "+strHoldingCurrentCount+"");			


			if(CurrentMode==Mode.GUI){
				lblFilePerSecond.setText("Files per second: "+strHoldingCurrentCount);
				lblPercent.setText("Percent: "+strPercent+"%");
				lblFilescount.setText((i+1)+" out of "+(ALFilesToProcess.size())+ " files processed");

				//Update Progress Bar
				progressBar.setValue(i+1);

			}


			double Eta=-1;
			if(Double.parseDouble(strHoldingCurrentCount)==0.0){
				Eta=-1;
			}else{
				Eta=Left/Double.parseDouble(strHoldingCurrentCount);
			}


			String StrEta=dfInteger.format(Eta).toString();

			//System.out.println("*"+StrEta+"*");


			if(StrEta.contains("-1")){
				StrEta="Estimating Running Time";
			}else{
				Integer intEta1=Integer.parseInt(StrEta);
				int hours = intEta1 / 3600,
						remainder = intEta1 % 3600,
						minutes = remainder / 60,
						seconds = remainder % 60;
				StrEta=(hours < 10 ? "0" : "") + hours
						+ ":" + (minutes < 10 ? "0" : "") + minutes
						+ ":" + (seconds< 10 ? "0" : "") + seconds;

			}


			System.out.println("Estimate Time (HH:MM:SS) = "+StrEta);

			if(CurrentMode==Mode.GUI){
				lblEta.setText("Estimate Time (HH:MM:SS) = "+StrEta);	
			}



			UtilObj.writeStringToFile(AbsoluteFileName+SysLine,"history\\"+UtilObj.Hash_MD5(Directory_Location)+".txt",true);


			dblTimerTracker=dblTimerTracker+Timer.stop_MilliDouble();

			//System.err.println(dblTimerTracker);
			if(dblTimerTracker>=1000){
				strHoldingCurrentCount=df.format(dblCurrentCount/(dblTimerTracker/1000));
				dblTimerTracker=0.0;
				dblCurrentCount=0.0;
			}
		}//end loop	

		outputToSystemAndGui("Finished processing "+ max + " files");
		if(CurrentMode==Mode.GUI){
			JOptionPane.showMessageDialog(null, "Finished processing "+ max + " files", "Info",JOptionPane.ERROR_MESSAGE);
		}
		
		guiFormFinished();

	}//end sub


	public static String getTags(ArrayList<String> inputArrayList,String StartTag,String EndTag){
		StringBuilder Output= new StringBuilder();

		boolean blnOnTag=false;

		for(int i=0; i<inputArrayList.size();i++){
			String CurrentLine=inputArrayList.get(i);

			if(CurrentLine.trim().contains(StartTag.trim())){
				blnOnTag=true;
			}

			if(blnOnTag)Output.append(CurrentLine+System.getProperty("line.separator").toString());

			if(CurrentLine.trim().contains(EndTag.trim())){
				blnOnTag=false;
			}
		}//end loop
		return Output.toString();
	}//end sub

	public static String getTagsExclude(ArrayList<String> inputArrayList,String StartTag,String EndTag){
		StringBuilder Output= new StringBuilder();

		boolean blnOnTag=false;

		for(int i=0; i<inputArrayList.size();i++){
			String CurrentLine=inputArrayList.get(i);
			if(CurrentLine.trim().contains(EndTag.trim())){
				blnOnTag=false;
			}

			if(blnOnTag)Output.append(CurrentLine+System.getProperty("line.separator").toString());


			if(CurrentLine.trim().contains(StartTag.trim())){
				blnOnTag=true;
			}


		}//end loop
		return Output.toString();
	}//end sub


	public static String getLine(ArrayList<String> inputArrayList,String EndTag){
		StringBuilder Output= new StringBuilder();

		for(int i=0; i<inputArrayList.size();i++){
			String CurrentLine=inputArrayList.get(i);

			if(CurrentLine.trim().contains(EndTag.trim())){
				Output.append(CurrentLine+System.getProperty("line.separator").toString());
			}
		}//end loop
		return Output.toString().trim();
	}//end sub
}
