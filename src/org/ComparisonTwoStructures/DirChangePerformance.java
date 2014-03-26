package org.ComparisonTwoStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.shared.performance.Timing;

public class DirChangePerformance {
	static Timing T1=new Timing();

	public static void main(String[] args) {
		Sequencial();
	}//end sub
	
	
	public static void Sequencial(){

		ArrayList<String> alBefore=new ArrayList<String>();
		ArrayList<String> alAfter=new ArrayList<String>();
		
		
		StringTable Perf= new StringTable("Items Count\tV3 Time\tV4 Time","\t");
		
		
		int size=1000;
		
		while(size<=500000){
			
			ArrayList<String> Times=new ArrayList<String>();

			Times.add(size+"");
			
			int lowStart=1;
			int lowEnd=size/3;
			int middleStart=lowEnd+1;
			int middleEnd= size/3+lowEnd;
			
			//Generating ArrayList
			for(int i = 1;i<=size;i++){
				if(i>=lowStart&&i<=lowEnd){
					//System.out.println("START\t"+i);
					alBefore.add(i+"");
				}else if(i>=middleStart&&i<=middleEnd){
					//System.out.println("MIDDLE\t"+i);
					alBefore.add(i+"");
					alAfter.add(i+"");
				}else{
					//System.out.println("END\t"+i);
					alAfter.add(i+"");
				}
				
			}
			
			
			long seed = System.nanoTime();
			Collections.shuffle(alBefore, new Random(seed));
			Collections.shuffle(alAfter, new Random(seed));
			
			
			
			/*
			 * DirChanges V3
			 */	
			
			System.out.println("DirChanges V3"+"\tSize="+size);
			DirChangesV3 DirChangesV3=new DirChangesV3();
			
			T1.start();
			System.out.println("Adding and Comparing");
			
			
			//Insert Before
			for(int i = 0;i<alBefore.size();i++){
				//DirChangesV3.InsertPrevious(alBefore.get(i));
			}
			
			//Insert After
			for(int i = 0;i<alAfter.size();i++){
				//DirChangesV3.InsertCurrent(alAfter.get(i));
			}
			
			//DirChangesV3.CompareAndReport();
			System.out.println(T1.stop_SecDouble());
			Times.add(T1.stop_SecDouble()+"");
			System.out.println("------------");
		
			/*
			 * DirChanges V4
			 */	
			
			System.out.println("DirChanges V4"+"\tSize="+size);
			
			DirChangesV4 DirChangesV4=new DirChangesV4();
			
			T1.start();
			System.out.println("Adding and Comparing");
			
			//Insert Before
			for(int i = 0;i<alBefore.size();i++){
				DirChangesV4.InsertPrevious(alBefore.get(i));
			}
			
			//Insert After
			for(int i = 0;i<alAfter.size();i++){
				DirChangesV4.InsertCurrent(alAfter.get(i));
			}
			
		
			DirChangesV4.CompareAndReport();
			System.out.println(T1.stop_SecDouble());
			Times.add(T1.stop_SecDouble()+"");
			System.out.println("------------");
			//
			
			String[] TimeArray= new String[Times.size()];
			
			for(int i=0;i<Times.size();i++){
				TimeArray[i]=Times.get(i);
			}
			Perf.insertStringArray(TimeArray);
			
			if(size<=999){
				size=size+100;
			}else if(size<=9999){
				size=size+1000;
			}else{
				size=size+10000;
			}
		}//end  loop
		
		
		System.out.println(Perf);
	}

}
