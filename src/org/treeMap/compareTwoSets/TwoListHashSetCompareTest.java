package org.treeMap.compareTwoSets;

public class TwoListHashSetCompareTest {


	public static void main(String[] args) {
		TwoListHashSetCompare<String> TestLists=new TwoListHashSetCompare<String>();
		
		TestLists.addItemToLeft("1");
		TestLists.addItemToLeft("2");
		TestLists.addItemToLeft("3");
		TestLists.addItemToLeft("4");
		TestLists.addItemToLeft("5");
		TestLists.addItemToLeft("5u");
		
		TestLists.addItemToRight("4");
		TestLists.addItemToRight("5");
		TestLists.addItemToRight("5u");
		TestLists.addItemToRight("7");
		
		System.out.println("Both:"+TestLists.getIntersectionFromBoth());
		System.out.println("Left:"+TestLists.getLeftRelativecomplement());
		System.out.println("Right:"+TestLists.getRightRelativecomplement());
		
		
	}

}


