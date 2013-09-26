package org.lgg.lucassite.exception;

public class DataException extends RuntimeException
{
	private static final long serialVersionUID = -1367672298038085217L;

	/**
	 * Default full constructor for the data exception.
	 * 
	 * @param message
	 *            to add to exception
	 * @param e
	 *            exception being re-wrapped
	 */
	public DataException(String message, Throwable e)
	{
		super(message, e);
	}

	/**
	 * Default constructor for exceptions which are not being re-wrapped.
	 * 
	 * @param message
	 *            to throw
	 */
	public DataException(String message)
	{
		super(message);
	}

	/**
	 * Default constructor for exceptions which are being re-wrapped without a message.
	 * 
	 * @param re
	 *            to re-wrap
	 */
	public DataException(Throwable re)
	{
		super(re);
	}
}
