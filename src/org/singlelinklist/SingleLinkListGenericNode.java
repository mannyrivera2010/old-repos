package org.singlelinklist;

public class SingleLinkListGenericNode <E> {
	/*
	 * Variables
	 */
	private E Data;
	private SingleLinkListGenericNode<E> Next;
	
	/**
	 * Constructor
	 * @param data
	 * @param next
	 */
	public SingleLinkListGenericNode(E data, SingleLinkListGenericNode<E> next) {
		super();
		Data = data;
		Next = next;
	}
	
	/**
	 * Constructor
	 * @param data
	 * @param next
	 */
	public SingleLinkListGenericNode(E data) {
		Data=data;
		Next=null;
	}
	
	/**
	 * Method used to get Data 
	 * @return
	 */
	public E getData() {
		return Data;
	}

	/**
	 * Method used to set Data
	 * @param data
	 */
	public void setData(E data) {
		Data = data;
	}

	/**
	 * Method used to get Next
	 * @return
	 */
	public SingleLinkListGenericNode<E> getNext() {
		return Next;
	}

	/**
	 * Method used to set Next
	 * @param next
	 */
	public void setNext(SingleLinkListGenericNode<E> next) {
		Next = next;
	}
	
}
