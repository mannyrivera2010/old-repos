package org.exceptions;

/**
 * The Class SubscriptionException.
 */
public class SubscriptionException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1611326625207881666L;

	/**
	 * Instantiates a new subscription exception.
	 *
	 * @param exc the exc
	 */
	public SubscriptionException(String exc){
		super(exc);
	}
	
	/**
	 * Instantiates a new subscription exception.
	 *
	 * @param cause the cause
	 */
	public SubscriptionException(Throwable cause){
		super(cause);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage(){
		return super.getMessage();
	}

}
