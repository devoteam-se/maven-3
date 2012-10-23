package org.apache.maven.security.crypto;

import java.nio.ByteBuffer;

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
	 * Encrypt a byte array and return the cipher text as a byte array.
	 * 
	 * @param plaintext the bytes to encrypt
	 * @return the cipher text as a byte array 
	 * @throws CryptoException if encryption fails
	 */
	public byte[] encrypt(final byte[] plaintext) throws CryptoException;
	
	/**
	 * Encrypt the contents of the input buffer and 
	 * write the cipher text to the output buffer.
	 * 
	 * @param plaintext the buffer to encrypt
	 * @param ciphertext the encrypted cipher text
	 * @return the number of bytes written to cipher text byte buffer
	 * @throws CryptoException if encryption fails
	 */
	public int encrypt(final ByteBuffer plaintext, ByteBuffer ciphertext) throws CryptoException;	
	
	/**
	 * Decrypt cipher text and return a plain text output.
	 * 
	 * @param ciphertext the string to decrypt
	 * @return the decrypted string
	 * @throws CryptoException if decryption fails
	 */
	public String decrypt(final String ciphertext) throws CryptoException;
	
	/**
	 * Decrypt a cipher text byte array and return the plain text as a byte array.
	 * 
	 * @param ciphertext the bytes to decrypt
	 * @return the plain text as byte array
	 * @throws CryptoException if decryption fails
	 */
	public byte[] decrypt(final byte[] ciphertext) throws CryptoException;
	
	/**
	 * Decrypt the cipher text byte buffer and write its output into the given output 
	 * byte buffer. Return the number of bytes written to the plain text buffer.
	 * 
	 * @param ciphertext the buffer to decrypt
	 * @param plaintext the decrypted buffer
	 * @return the number of bytes written to the plain text buffer
	 * @throws CryptoException if decryption fails
	 */
	public int decrypt(final ByteBuffer ciphertext, ByteBuffer plaintext) throws CryptoException;

}
