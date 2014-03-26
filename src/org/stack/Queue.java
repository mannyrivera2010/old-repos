package org.stack;

/**
 * Generic Singly Linked List
 * @author Emanuel Rivera
 *
 * @param <T>
 */
public class Queue<T> {	
	/**
	 * Private class Node
	 */
	private class Node <E> {
		/*
		 * Variables
		 */
		private E Data;
		private Node<E> Next;
		
		/**
		 * Constructor
		 * @param data
		 * @param next
		 */
		
		public Node(E data, Node<E> next) {
			super();
			Data = data;
			Next = next;
		}
		
		/**
		 * Constructor
		 * @param data
		 * @param next
		 */
		public Node(E data) {
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
		public Node<E> getNext() {
			return Next;
		}

		/**
		 * Method used to set Next
		 * @param next
		 */
		public void setNext(Node<E> next) {
			Next = next;
		}
	}//end node class
	
	/*
	 *Variables 
	 */
	private int size=0;
	private Node<T> head=null;
	private Node<T> tail=null;
			
	/**
	 * Constructor 
	 */
	public Queue() {
		super();
	}//End Method

	/**
	 * Used to Enqueue to list
	 * @param Data
	 */
	public void push(T Data){
		this.addToEnd(Data);
	}

	/**
	 * Method is Used to Add Element to the End of the list
	 */
	private void addToEnd(T Data){
		if(head==null){
			Node<T> NewNode=new Node<T>(Data);
			head=NewNode;
			tail=NewNode;
			
			head.setNext(tail);
			tail=head;
		}else{
			Node<T> NewNode=new Node<T>(Data);
			tail.setNext(NewNode);
			tail=tail.getNext();
		}
		size++;
	}//End Method
	
	
	/**
	 * Used to Peek
	 */
	public T peek(){
		return this.get(0) ;
	}

	public T pop(){
		T temp=this.get(0);
		this.remove(0);
		return temp;
	}
	
	/**
	 * Method used to remove elements using index
	 * @param index
	 * @param Data
	 */
	private void remove(int index){
		if(size==0)
			return;
		
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
		
		if(index==0){//Start
			head=head.getNext();
		}else{
			
			Node<T> current = head;
			
			for(int i=-1;i<index && index<this.size && index>=0;i++){
				//System.out.println("i:"+i+"\tIndex:"+index+"\tcurrent.getData()="+current.getData());
				if(i==index-2){
				
					current.setNext(current.getNext().getNext());
			
					if(index+1==this.size){
						this.tail=current;
						//System.out.println("i:"+i+"\tIndex:"+index+"\tLength:"+this.length);
						//System.out.println(">>"+current.getData()+">>");
					}
					break;
				}else{
					current=current.getNext();
				}	
			}
		}
		size--;
	}//End Method
	

	
	/**
	 * Method used to obtain Object using index
	 * @param index
	 * @return
	 */
	private T get(int index){
		if(size==0)
			return null;
		
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
		
		Node<T> current = head;
		
		T Found=null;
		
		for(int i=-1;i<index && index<this.size && index>=0;i++){
			//System.out.println(current.getData()+"\ti:"+i+"\tindex="+index);
			if(i==index-1){
				Found=(T) current.getData();
				//System.out.println(current.getData());
			}
			current=current.getNext();		
		}
		return (T) Found;
	}//End Method
	
	/**
	 * Method is used to get the Length of list 
	 * @return
	 */
	public int size() {
		return size;
	}//End Method
	
	/**
	 * Method is used to print out the elements of queue
	 */
	public String toString() {
		StringBuilder Output= new StringBuilder();
		
		Node<T> current = head;
		
		for(int i=0;i<this.size;i++){
			
			if(i==this.size-1){
				Output.append("{"+current.getData()+"}");
			}else{
				Output.append("{"+current.getData()+"}, ");
			}
			
			current=current.getNext();			
		}
		return "Stack [" + Output + "]";		
	}//End Method

	/**
	 * Method is used to clear list
	 */
	public void clear() {
		size=0;
		head=null;
		tail=null;
	}//End Method

	/**
	 * Method is used to check if list is empty
	 */
	public boolean isEmpty() {
		return size==0?true:false;
	}//End Method


	
}//End Class
