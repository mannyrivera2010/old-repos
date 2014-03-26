package org.treeMap.compareTwoSets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class TwoListHashSetCompare <E> {
	private TreeSet<E> HashSetLeft= new TreeSet<E>();
	private TreeSet<E> HashSetRight= new TreeSet<E>();
	
	/**
	 * Replace Left HashSet with ArrayList
	 * @param ArrayListInput
	 */
	public void setLeftHashSetWithArrayList(ArrayList<E> ArrayListInput){
		HashSetLeft=new TreeSet<E>(ArrayListInput);
	}
	
	/**
	 * Replace right HashSet with ArrayList
	 * @param ArrayListInput
	 */
	public void setRightHashSetWithArrayList(ArrayList<E> ArrayListInput){
		HashSetRight=new TreeSet<E>(ArrayListInput);
	}
	
	/**
	 * Add Item to Left HashSet
	 * @param Item
	 */
	public void addItemToLeft(E Item){
		HashSetLeft.add(Item);
	}
	
	/**
	 * Add Item to Right HashSet
	 * @param Item
	 */
	public void addItemToRight(E Item){
		HashSetRight.add(Item);
	}
	
	/**
	 * Remove Item from Left
	 * @param Item
	 */
	public void removeItemFromLeft(E Item){
		HashSetLeft.remove(Item);
	}
	
	/**
	 * Remove Item from Right
	 * @param Item
	 */
	public void removeItemFromRight(E Item){
		HashSetRight.remove(Item);
	}
	
	/**
	 * Get what is common in both Sets
	 * @return
	 */
	public ArrayList<E> getIntersectionFromBoth(){
		ArrayList<E> temp= new ArrayList<E>();
		
		Iterator<E> itr = HashSetLeft.iterator();
		
		while(itr.hasNext()){
			E current=itr.next();
			
			if(HashSetRight.contains(current)){
				temp.add(current);
			}
		}
		
		return temp;
	}
	
	/**
	 * Get what is unique in Left
	 * @return
	 */
	public ArrayList<E> getLeftRelativecomplement(){
		ArrayList<E> temp= new ArrayList<E>();
		
		Iterator<E> itr = HashSetLeft.iterator();
		
		while(itr.hasNext()){
			E current=itr.next();
			
			if(!HashSetRight.contains(current)){
				temp.add(current);
			}
		}
		
		return temp;
	}
	
	/**
	 * Get what is unique in Right
	 * @return
	 */
	public ArrayList<E> getRightRelativecomplement(){
		ArrayList<E> temp= new ArrayList<E>();
		
		Iterator<E> itr = HashSetRight.iterator();
		
		while(itr.hasNext()){
			E current=itr.next();
			
			if(!HashSetLeft.contains(current)){
				temp.add(current);
			}
		}
		
		return temp;
	}
	
	
}
