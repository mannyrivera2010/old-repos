package org.singlelinklist;

/**
 * Generic Singly Linked List
 * @author Emanuel Rivera
 *
 * @param <T>
 */
public class SinglyLinkedList<T> implements ListInterface<T> {	
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
	public SinglyLinkedList() {
		super();
	}//End Method

	/**
	 * Method is Used to Add Element to the beginning of the list
	 */
	@Override
	public void addToStart(T Data){
		if(head==null){
			Node<T> NewNode=new Node<T>(Data);
			head=NewNode;
			tail=NewNode;
			
			head.setNext(tail);
			tail=head;
		}else{
			Node<T> NewNode=new Node<T>(Data,head);
			head=NewNode;
		}
		size++;
	}//End Method
	
	/**
	 * Method is Used to Add Element to the End of the list
	 */
	@Override
	public void addToEnd(T Data){
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
	 * Method used to add elements into list before index
	 * @param index
	 * @param Data
	 */
	@Override
	public void add(int index,T Data){
		if (index < 0 || index >= size+1)
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
		
		if(index==0){//Start
			this.addToStart(Data);
			//Above doing : length++;
		}else if(index==size){//End
			this.addToEnd(Data);
			//Above doing : length++;
		}else{
			this.insert(index-1, Data);
			//Above doing : length++;
		}
	}//End Method
	
	/**
	 * Method used to remove elements using index
	 * @param index
	 * @param Data
	 */
	@Override
	public void remove(int index){
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
	 * Method used to add elements into list after index
	 * @param Data
	 * @param index
	 */
	@Override
	public void insert(int index,T Data){
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
		
		Node<T> current = head;
		
		for(int i=-1;i<index && index<this.size && index>=0;i++){
			
			if(i==index-1){
				
				Node<T> NewNode=new Node<T>(Data,current.getNext());
				current.setNext(NewNode);
		
				//System.out.println(current.getData());	
				break;
			}else{
				current=current.getNext();
			}
			
		}
		size++;
	}//End Method
	
	/**
	 * Method used to obtain Object using index
	 * @param index
	 * @return
	 */
	@Override
	public T get(int index){
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
	 * Method used to get first element
	 * @return
	 */
	@Override
	public T getStart(){
		return head.getData();
	}//End Method
	
	/**
	 * Method used to get last element
	 * @return
	 */
	@Override
	public T getEnd(){
		return tail.getData();
	}//End Method
	
	/**
	 * Method used to set Object using index
	 * @param index
	 * @return
	 */
	@Override
	public void set(int index, T Data){
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
		
		Node<T> current = head;
		
		for(int i=-1;i<index && index<this.size && index>=0;i++){
			//System.out.println(current.getData()+"\ti:"+i+"\tindex="+index);
			if(i==index-1){
				current.setData(Data);
				//System.out.println(current.getData());
			}	
			current=current.getNext();		
		}
	}//End Method
	
	/**
	 * Method is used to get the Length of list 
	 * @return
	 */
	@Override
	public int size() {
		return size;
	}//End Method
	
	/**
	 * Method is used to print out the elements of list
	 */
	@Override
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
		return "SingleLinkedList [" + Output + "]";		
	}//End Method

	/**
	 * Method is used to clear list
	 */
	@Override
	public void clear() {
		size=0;
		head=null;
		tail=null;
	}//End Method

	/**
	 * Method is used to check if list is empty
	 */
	@Override
	public boolean isEmpty() {
		return size==0?true:false;
	}//End Method

	@Override
	public boolean remove(T arg0) {
		// TODO Auto-generated method stub
		return false;
	}//End Method

	@Override
	public SinglyLinkedList<T> subList(int arg0, int arg1) {
		if (arg0 < 0 || arg1 >= size || arg0>arg1)
			throw new IndexOutOfBoundsException("Out of range "+", Size: "+size);
		
		SinglyLinkedList<T> OutList= new SinglyLinkedList<T>();
		
		Node<T> current = head;
		
		for(int i=0;i<this.size;i++){	
			if(i>=arg0&&i<=arg1){
				OutList.addToEnd(current.getData());
			}
			if(i>=arg1)break;
			
			current=current.getNext();			
		}
		
		// TODO Auto-generated method stub
		return OutList;
	}//End Method

	/**
	 * Method is used to convert the list into an Array
	 */
	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] Output=(T[]) new Object[this.size];
		
		Node<T> current = head;
		
		for(int i=0;i<this.size;i++){	
			Output[i]=current.getData();
			current=current.getNext();			
		}
		return Output;
	}//End Method

}//End Class
