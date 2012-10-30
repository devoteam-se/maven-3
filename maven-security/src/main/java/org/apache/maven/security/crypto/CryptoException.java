package org.apache.maven.security.crypto;

//TODO: add copyright

/**
 * Occurs when there is a problem encrypting or decrypting a text.
 * @author Karin Karlsson
 *
 */
public class CryptoException extends Exception 
{

	private static final long serialVersionUID = 1L;

	public CryptoException() 
	{
		super();

	}

	public CryptoException(String arg0, Throwable arg1) 
	{
		super(arg0, arg1);

	}

	public CryptoException(String arg0) 
	{
		super(arg0);

	}

	public CryptoException(Throwable arg0) 
	{
		super(arg0);

	}
	
	

}
