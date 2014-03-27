package org.shared;
/**
 * @author Emanuel Rivera
 */

import java.sql.Timestamp;
import java.util.Date;
import javolution.text.TextBuilder;
import org.shared.encryption.JackSum;


// TODO: Auto-generated Javadoc
/**
 * The Class DirChanges.
 */
public class DirChanges {	 

	/** The Previous file names. */
	private StringTable PreviousFileNames = new StringTable("WorkingDir;;;FileName;;;CheckSum;;;GrantID",";;;");
	
	/** The Current file names. */
	private StringTable CurrentFileNames  = new StringTable("WorkingDir;;;FileName;;;CheckSum;;;GrantID",";;;");
			
	/**
	 * Sets the previous file names.
	 *
	 * @param previousFileNames the new previous file names
	 */
	public void setPreviousFileNames(StringTable previousFileNames) {
		PreviousFileNames = previousFileNames;
	}

	/**
	 * Sets the current file names.
	 *
	 * @param currentFileNames the new current file names
	 */
	public void setCurrentFileNames(StringTable currentFileNames) {
		CurrentFileNames = currentFileNames;
	}
	
	/**
	 * Prints the previous file names.
	 *
	 * @return the string
	 */
	public String printPreviousFileNames(){
		String Output= PreviousFileNames.toStringBodyDel();
		return Output;
	}//end PrintPreviousFileNames()
	
	/**
	 * Insert previous.
	 *
	 * @param strLine the str line
	 */
	public void insertPrevious(String strLine){
		PreviousFileNames.insertStringColumn(strLine);
	}//end InsertPrevious()
	
	
	/**
	 * Insert previous.
	 *
	 * @param WorkingDir the working dir
	 * @param strFileName the str file name
	 * @param strCheckSum the str check sum
	 * @param strGrantID the str grant id
	 */
	public void insertPrevious(String WorkingDir,String strFileName,String strCheckSum, String strGrantID){
		PreviousFileNames.insertStringArray(new String[]{WorkingDir,strFileName,strCheckSum,strGrantID});
	}//end InsertPrevious()
	
	/**
	 * Insert current.
	 *
	 * @param WorkingDir the working dir
	 * @param strFileName the str file name
	 * @param strCheckSum the str check sum
	 * @param strGrantID the str grant id
	 */
	public void insertCurrent(String WorkingDir,String strFileName,String strCheckSum, String strGrantID){
		CurrentFileNames.insertStringArray(new String[]{WorkingDir,strFileName,strCheckSum,strGrantID});
	}//end InsertCurrent()
	
	/**
	 * Current to previous.
	 */
	public void currentToPrevious(){
		PreviousFileNames.clearArrLRows();
		for(int intList2=0;intList2<=CurrentFileNames.getArrLRowsSize()-1;intList2++){
			PreviousFileNames.insertStringArray(
					new String[]{CurrentFileNames.getColumnValue("WorkingDir", intList2),
							CurrentFileNames.getColumnValue("FileName", intList2), 
							CurrentFileNames.getColumnValue("CheckSum", intList2),
							CurrentFileNames.getColumnValue("GrantID", intList2)});
		}
	}//end CurrentToPrevious()
	
	/**
	 * Clear current.
	 */
	public void clearCurrent(){
		CurrentFileNames.clearArrLRows();
	}//end ClearCurrent()
	
	/**
	 * Compare and report string.
	 *
	 * @return the string
	 */
	public String compareAndReportString(){
		return (String) compareAndReport("",false).toString();
	}
	
	/**
	 * Compare and report string table.
	 *
	 * @return the string table
	 */
	public StringTable compareAndReportStringTable(){
		return  (StringTable) compareAndReport("",true);
	}
	
	/**
	 * Compare and report.
	 *
	 * @param timeStampLog the time stamp log
	 * @param isStringTable the is string table
	 * @return the object
	 */
	private Object compareAndReport(String timeStampLog,boolean isStringTable ){
		JackSum JackSumEngine = new JackSum();
		 // JackSumEngine Used to shorten length of MD5 (128bit) to Crc16 (16bit) for output (email and web page)
		PreviousFileNames.qsort("FileName");
		CurrentFileNames.qsort("FileName");
		
		boolean HasTimeStamp=false;
		if(timeStampLog.trim().length()>=1){
			HasTimeStamp=true;
		}

		Date date = new Date();
		String TimeStampStTemp = new Timestamp(date.getTime()) + "";
		
		StringTable StTemp= new StringTable("Date;;;Action;;;FileName;;;CheckSum;;;GrantID",";;;");
		
		TextBuilder Output=new TextBuilder("");

		//START:Loops Check for Removed Files
		for(int intList1=0;intList1<=PreviousFileNames.getArrLRowsSize()-1;intList1++){
			boolean FileExist=false;
			
			String StrList1=PreviousFileNames.getColumnValue("FileName", intList1);
			String CheckSum=PreviousFileNames.getColumnValue("CheckSum", intList1);
			String GrantID=PreviousFileNames.getColumnValue("GrantID", intList1);
			
			for(int intList2=0;intList2<=CurrentFileNames.getArrLRowsSize()-1;intList2++){//START:INNER1
				String StrList2=CurrentFileNames.getColumnValue("FileName", intList2);
				FileExist=StrList1.equals(StrList2);
				//System.out.println(StrList1+"-"+StrList2+"="+FileExist);
				if(FileExist==true){
					break;
				}
			}//END:INNER1
				
			if(FileExist==false){
				if(isStringTable){
					StTemp.insertStringArray(new String[]{TimeStampStTemp,"Removed",StrList1,CheckSum,GrantID});
				}else{
					if(HasTimeStamp==false){
						Output.append("Removed:"+StrList1+" ("+JackSumEngine.crc16_HEX(CheckSum)+")\n");
					}else{
						Output.append(timeStampLog+"\tRemoved:\t"+StrList1+" ("+JackSumEngine.crc16_HEX(CheckSum)+")\n");
					}	
				}
			}
		}//END:Loops Check for Removed Files
		
		//START:Loops Check for Added Files
		for(int intList2=0;intList2<=CurrentFileNames.getArrLRowsSize()-1;intList2++){
			boolean FileExist=false;
			
			String StrList2=CurrentFileNames.getColumnValue("FileName",intList2);
			String CheckSum=CurrentFileNames.getColumnValue("CheckSum",intList2);
			String GrantID=CurrentFileNames.getColumnValue("GrantID", intList2);
			
			for(int intList1=0;intList1<=PreviousFileNames.getArrLRowsSize()-1;intList1++){//START:INNER1
				
				String StrList1=PreviousFileNames.getColumnValue("FileName",intList1);
				FileExist=StrList2.equals(StrList1);
				
				//System.out.println(StrList2+"-"+StrList1+"="+FileExist);
				if(FileExist==true){
					break;
				}
			}//END:INNER1
	
			if(FileExist==false){
				
				if(isStringTable){
					StTemp.insertStringArray(new String[]{TimeStampStTemp,"Added",StrList2,CheckSum,GrantID});
				}else{
					if(HasTimeStamp==false){
						Output.append("Added:"+StrList2+" ("+JackSumEngine.crc16_HEX(CheckSum)+")\n");
					}else{
						Output.append(timeStampLog+"\tAdded:\t"+StrList2+" ("+JackSumEngine.crc16_HEX(CheckSum)+")\n");
					}	
				}
			}
		}//END:Loops Check for Added Files
				
		//START:Loops Check foradded  Modified Files
		for(int intList2=0;intList2<=CurrentFileNames.getArrLRowsSize()-1;intList2++){
			boolean FileModified=false;
			boolean FileExist=false;
			
			String StrList2=CurrentFileNames.getColumnValue("FileName",intList2);
			String StrList2CheckSum=CurrentFileNames.getColumnValue("CheckSum", intList2);
			String GrantID=CurrentFileNames.getColumnValue("GrantID", intList2);
			
			for(int intList1=0;intList1<=PreviousFileNames.getArrLRowsSize()-1;intList1++){//START:INNER1
				String StrList1=PreviousFileNames.getColumnValue("FileName",intList1);
				String StrList1CheckSum=PreviousFileNames.getColumnValue("CheckSum",intList1);
				FileExist=StrList2.equals(StrList1);
				FileModified=!StrList2CheckSum.equals(StrList1CheckSum);
				
			//System.out.println(StrList2CheckSum+"-"+StrList1CheckSum+"="+FileModified+">"+StrList2+"-"+StrList1+"="+FileExist);
				if(FileModified==true&FileExist==true){
					break;
				}
			}//END:INNER1
	
			if(FileModified==true&&FileExist==true){
				if(isStringTable){
					StTemp.insertStringArray(new String[]{TimeStampStTemp,"Modified",StrList2,StrList2CheckSum,GrantID});
				}else{
					if(HasTimeStamp==false){
						Output.append("Modified:"+StrList2+" ("+JackSumEngine.crc16_HEX(StrList2CheckSum)+")\n");
					}else{
						Output.append(timeStampLog+"\tModified:\t"+StrList2+" ("+JackSumEngine.crc16_HEX(StrList2CheckSum)+")\n");
					}
				}
			}
		}//END:Loops Check for Added Files
		
		if(isStringTable){
			return StTemp;
		}else{
			return Output;
		}
		
	}//end CompareAndReport()	
}//end class
