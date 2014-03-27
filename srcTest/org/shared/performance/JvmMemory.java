package org.shared.performance;

import java.text.NumberFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class JvmMemory.
 */
public class JvmMemory {

    /** The format. */
    static NumberFormat format = NumberFormat.getInstance();

    /** The sb. */
    static StringBuilder sb = new StringBuilder();
    
    /** The max memory. */
    static long maxMemory = Runtime.getRuntime().maxMemory();
    
    /** The allocated memory. */
    static long allocatedMemory = Runtime.getRuntime().totalMemory();
    
    /** The free memory. */
    static long freeMemory = Runtime.getRuntime().freeMemory();



	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SeeMemUsage();
	}
	
	/**
	 * See mem usage.
	 */
	public static void SeeMemUsage(){
	    StringBuilder sb = new StringBuilder();
	    long maxMemory = Runtime.getRuntime().maxMemory();
	    long allocatedMemory = Runtime.getRuntime().totalMemory();
	    long freeMemory = Runtime.getRuntime().freeMemory();
	    sb.append("free memory: " + format.format(freeMemory / 1024) + " kb\t");
	    sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + " kb\t");
	    sb.append("max memory: " + format.format(maxMemory / 1024) + " kb\t");
	    sb.append("used memory: " + format.format((maxMemory-freeMemory) / 1024) + " kb\t");
	    sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + " kb");
	    
	    System.out.println(sb.toString());
	}
	
	/**
	 * Is32or64bit.
	 */
	public static void is32or64bit(){
		System.out.println(System.getProperty("sun.arch.data.model"));
	}

}
