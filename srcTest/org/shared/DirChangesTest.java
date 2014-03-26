package org.shared;

public class DirChangesTest {

	public static void main(String[] args) {
		DirChanges NewTest=new DirChanges();
		
		NewTest.InsertPrevious("file1.txt");
		NewTest.InsertPrevious("file2.txt");
		NewTest.InsertPrevious("file3.txt");

		NewTest.InsertCurrent("file6.txt");
		NewTest.InsertCurrent("file9.txt");
		NewTest.InsertCurrent("file8.txt");
		NewTest.InsertCurrent("file1.txt");

		System.out.println(NewTest.CompareAndReport());
		
		NewTest.CurrentToPrevious();
		NewTest.InsertCurrent("file1.txt");
		NewTest.InsertCurrent("file41.txt");
		
		System.out.println(NewTest.CompareAndReport());
	}

}
