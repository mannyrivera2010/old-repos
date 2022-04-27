package org.shared;

public class DirChangesTest {

	public static void main(String[] args) {
		DirChanges NewTest=new DirChanges();
		
		NewTest.insertFile1("file1.txt");
		NewTest.insertFile1("file2.txt");
		NewTest.insertFile1("file3.txt");

		NewTest.insertFile2("file6.txt");
		NewTest.insertFile2("file9.txt");
		NewTest.insertFile2("file8.txt");
		NewTest.insertFile2("file1.txt");

		System.out.println(NewTest.CompareAndReport());
		
	
	}

}
