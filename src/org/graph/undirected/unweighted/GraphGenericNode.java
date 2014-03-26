package org.graph.undirected.unweighted;

import java.util.HashSet;

public class GraphGenericNode <E>{
	/*
	 * Variables
	 */
	private E Data;
	private boolean isVisited;
	private HashSet<GraphGenericNode<E>> EdgeNode;

	/**
	 * Constructor - Set Data to input
	 * @param data
	 * @param next
	 */
	public GraphGenericNode(E data) {
		super();
		Data = data;
		isVisited=false;
	}
	
	/**
	 * 
	 */
	public GraphGenericNode() {
		super();
		Data = null;
		isVisited=false;
	}

	@Override
	public String toString() {
		return "" + Data + "";
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

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Data == null) ? 0 : Data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphGenericNode<?> other = (GraphGenericNode<?>) obj;
		if (Data == null) {
			if (other.Data != null)
				return false;
		} else if (!Data.equals(other.Data))
			return false;
		return true;
	}

}//end class
