package org.shared;

public class PersistentDirChangesTest {

	public static void main(String[] args) throws Exception {
		PersistentDirChanges PDCObj=new PersistentDirChanges("H:\\workspace\\Eric_WordPress2\\input");
		
		PDCObj.processPreviousFileList();
		PDCObj.processCurrentFileList();
		System.out.println(PDCObj.CompareList());
		
		System.out.println(PDCObj.GetAddedFiles());
		
	}// end sub
}
