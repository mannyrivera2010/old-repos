package org.table;

import table.coders.Base64Coder;
import table.coders.FileEngine;
import table.compression.ZipInflater;
import javolution.text.TextBuilder;
import javolution.util.FastMap;
import javolution.util.FastTable;


public class StringTableManager {
	private FastMap<String,StringTable> stringTableManager=new FastMap<String,StringTable>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringTableManager StrTableMgr= new StringTableManager();	
		StrTableMgr.addTable("person","first;;;last;;;birthday",";;;");
		for(int i =0;i<100;i++){
			StrTableMgr.getTable("person").insertStringArray(new String[]{"namea"+i,"Laction"+i,"date"+i});
		}
		
		StrTableMgr.addTable("Computers","Name;;;Location",";;;");
		for(int i =0;i<100;i++){
			StrTableMgr.getTable("Computers").insertStringArray(new String[]{"namea"+i,"Laction"+i});
		}
		
		//table2.qsort("Subject");
				
		//System.out.println(StrTableMgr.getTable("person").toString());
		
		System.out.println("END");
		StrTableMgr.SaveTablesToFileXML();
	}

	public void addTable(String strTableName,String strConRaw, String strDelimiter){
		stringTableManager.put(strTableName, new StringTable(strConRaw,strDelimiter) );
	}
	
	public void removeTable(String strTableName){
		stringTableManager.remove(strTableName);
	}
	
	public StringTable getTable(String strTableName){
		if(!stringTableManager.containsKey(strTableName)){
			return null;
		}
		return stringTableManager.get(strTableName);
	}
	
	
	public void SaveTablesToFileCompressedXML(){
		
	}
	
	public void SaveTablesToFileXML(){
		FileEngine filesEngines=new FileEngine();
		ZipInflater ZipCom=new ZipInflater();
		
		TextBuilder TextAppender= new TextBuilder();
		TextAppender.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		TextAppender.append("<Root>\n");
	     for (FastMap.Entry<String, StringTable> e = stringTableManager.head(), end = stringTableManager.tail(); (e = e.getNext()) != end;) {
	          String key = e.getKey(); // No typecast necessary.
	          StringTable value = e.getValue(); // No typecast necessary.
	          
	          TextAppender.append("\t<Table>\n");
	          //TextAppender.append("\t\t<Settings>\n");
	          TextAppender.append("\t\t<Table_Name>"+key+"</Table_Name>\n");
	          TextAppender.append("\t\t<Delimiter>"+value.getStrDelimiter()+"</Delimiter>\n");
	          //TextAppender.append("\t\t</Settings>\n");
	          
	         int columnSize=value.getColumnSize();
	         String[] arrColumnHeader = value.getTableColumnNames();
	         FastTable<String[]> fTRows=value.getTableRows();
	         
	         //
	         TextAppender.append("\t\t<Header>\n");
	         for(int i =0;i<columnSize;i++){
	 				TextAppender.append("\t\t\t<title>"+arrColumnHeader[i]+"</title>\n");
	 		}
	         TextAppender.append("\t\t</Header>\n");
	         
	         
	         for(int i=0;i<fTRows.size();i++){
	 			String[] temp=fTRows.get(i);
	 			TextAppender.append("\t\t<Entry>\n");
	 			
	 			for(int j =0;j<temp.length;j++){
	 					TextAppender.append("\t\t\t<Value>"+temp[j]+"</Value>\n");
	 			}
	 			TextAppender.append("\t\t</Entry>\n");
	 		}

	         //
	         TextAppender.append(new String("\t</Table>\n"));    
	     }
	     TextAppender.append("</Root>\n");
	     
	     System.out.println(TextAppender.subSequence(1, 10).toString());
	     
	     //System.out.println(TextAppender.toString().getBytes().length+"MB");
	     byte[] CompressedBytesLevel1=ZipCom.Compress(TextAppender.toString().getBytes());
	     byte[] base64BytesLevel1=Base64Coder.encodeLines(CompressedBytesLevel1).toString().getBytes();
	     byte[] CompressedBytesLevel2=ZipCom.Compress(base64BytesLevel1);
	     //byte[] base64BytesLevel2=Base64Coder.encodeLines(CompressedBytesLevel2).toString().getBytes();

	     
	     filesEngines.WriteBytesToFile("data.dbx", CompressedBytesLevel2);
	     filesEngines.WriteBytesToFile("data.xml", TextAppender.toString().getBytes());
	}
		
	
}
