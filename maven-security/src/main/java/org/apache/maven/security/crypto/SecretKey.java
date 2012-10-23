package org.apache.maven.security.crypto;

/**
 * 
 * @author Karin Karlsson
 *
 */
public interface SecretKey 
{
	/**
	 * The secret key represented as a string
	 * @return the key
	 */
	public String getValue();
}
