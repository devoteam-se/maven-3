package org.apache.maven.security.crypto;

//TODO: add copyright

/**
 * This class handles cryptographic details.
 * 
 * @author Karin Karlsson
 *
 */
public interface Crypto 
{
	/**
	 * Encrypt plain text string and return an output cipher text.
	 * 
	 * @param plaintext the string to encrypt
	 * @return the cipher text
	 * @throws CryptoException if encryption fails
	 */
	public String encrypt(final String plaintext) throws CryptoException;

	/**
	 * Decrypt cipher text and return a plain text output.
	 * 
	 * @param ciphertext the string to decrypt
	 * @return the decrypted string
	 * @throws CryptoException if decryption fails
	 */
	public String decrypt(final String ciphertext) throws CryptoException;

	

}
