package org.shared;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.main.Gui;

public class PersistentDirChanges {
	private Util UtilObj= new Util();
	private String Directory_Location="";
	private DirChanges DirChangesObj= new DirChanges();
	
	public PersistentDirChanges(String directory_Location) {
		super();
		Directory_Location = directory_Location;
		UtilObj.folderExistAndMake("history");
	}

	public String CompareList(){
		System.out.println("Comparing List");
		return DirChangesObj.CompareAndReport();
	}
	
	public ArrayList<String> GetAddedFiles() {
		return DirChangesObj.GetAddedFiles();
	}
	
	public void processPreviousFileList(){
		try {
			//System.out.println("Getting Previous Directory Index...");
			Gui.outputToSystemAndGui("Getting Previous Directory Index...");
			ArrayList<String> AlFileList=UtilObj.fileto1dArrayList("history\\"+UtilObj.Hash_MD5(Directory_Location)+".txt");
			//System.err.println(AlFileList.size());
			DirChangesObj.setPreviousFileNames(AlFileList);
			
			
			//System.out.println("Finished Previous Directory Index");
			Gui.outputToSystemAndGui("Finished Previous Directory Index");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("Error Getting Directory Index - Making Blank File");
			UtilObj.writeStringToFile("", "history\\"+UtilObj.Hash_MD5(Directory_Location)+".txt", false);
		}
	}
	
	
	public void processCurrentFileList() throws IOException{
		ArrayList<String> tempCurrentFiles= new ArrayList<String>();
		
	
			//System.out.println("Getting Current List of Directory");
			Gui.outputToSystemAndGui("Getting Current List of Directory");
			File root = new File(Directory_Location);
			File[] list = root.listFiles();
			//System.out.println(list.length);
			//System.out.println("Done Getting Current List of Directory");
			Gui.outputToSystemAndGui("Done Getting Current List of Directory");
			//System.out.println("Adding Current Files to Index");
			Gui.outputToSystemAndGui("Adding Current Files to Index");
			for (File f : list) {
				
				if (f.isDirectory()) {
					//System.out.println("Dir:" + f.getAbsoluteFile());
					//DO NOTHING
				} else {
					String AbsoluteFileName = f.getAbsoluteFile().toString().trim() + "";
					//System.out.println( "File:" + AbsoluteFileName );
						if(UtilObj.AspFileNamePatternMatch(AbsoluteFileName)){
							//Start pattern match
			
							//System.out.println("Adding >> "+  f.getAbsolutePath()); 
							tempCurrentFiles.add(f.getAbsolutePath());
						}else{
							//System.out.println( "File Does not match pattern>>" +   f.getAbsolutePath() );
						}//end pattern match
					}//end extension matching
				

			}// end loop

		//System.out.println("Writing Index to File");
		Gui.outputToSystemAndGui("Writing Index to File");
		//String strFastTableList=UtilObj.ArrayListToString(tempCurrentFiles,false);
		//UtilObj.writeStringToFile(strFastTableList,"history\\"+UtilObj.Hash_MD5(Directory_Location)+".txt",false);
		//System.out.println("Done Writing Index to File");
		Gui.outputToSystemAndGui("Done Writing Index to File");
		DirChangesObj.setCurrentFileNames(tempCurrentFiles);
	}
	
	
	
	
	
}//end class
