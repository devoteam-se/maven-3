package org.apache.maven.security.crypto;

//TODO: add copyright

/**
 * A secret key is used with symmetric encryption (such as AES).
 * 
 * @author Karin Karlsson
 *
 */
public interface SecretKey 
{
	
	/**
	 * The secret key represented as a string
	 * @return the key
	 * @throws CryptoException 
	 */
	public String getValue() throws CryptoException;
}
