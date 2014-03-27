package org.core.EventMutliThread;

import org.enums.DatabaseEngineEnum;

// TODO: Auto-generated Javadoc
/**
 * The Class DirectoryEventThreadTest.
 */
public class DirectoryEventThreadTest {

	/**
	 * Used for Testing.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// DirectoryEvent Test1=new
		// DirectoryEvent("H:\\Public","TO","CC","Subject","Body");
		DirectoryEventThread Test2 = new DirectoryEventThread(
				"H:\\Grant\\Ellie", "TO", "", "Subject", "Body",
				DatabaseEngineEnum.SQLITE4WRAPPER);
		Test2.go();
		 //StringTable StTemp=new StringTable("FileName;;;CheckSum",";;;");
		 //System.out.println(Test2.MakeFileList("O:\\Grant\\Ellie",StTemp));

		//StringTable tem = Test2.Database.data.getHistoryTableWhere("%");

		//System.out.println(tem);
	}
	
}
