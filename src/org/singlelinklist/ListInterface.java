package org.singlelinklist;

public interface ListInterface<T> {
	public void add(int arg0, T arg1);
	public void addToEnd(T Data);
	public void addToStart(T Data);
	
	public void insert(int index, T Data);
	
	public void set(int arg0, T arg1);
	
	public boolean remove(T arg0);
	public void remove(int arg0);
	
	public T get(int arg0);
	public T getEnd();
	public T getStart();
	
	public boolean isEmpty();
	public int size();
	public void clear();

	public ListInterface<T> subList(int arg0, int arg1);
	public Object[] toArray();
}
