package org.apache.maven.security.crypto;

import java.nio.ByteBuffer;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.plexus.components.cipher.DefaultPlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipher;
import org.sonatype.plexus.components.sec.dispatcher.DefaultSecDispatcher;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcher;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcherException;

/**
 * This class handles encryption and decryption details.
 *
 * @author Karin Karlsson
 * @see SecDispatcher
 * @see PlexusCipher
 */
@Component(role = Crypto.class)
public class DefaultCrypto implements SecretKeyCrypto 
{
	
	@Requirement
	private SecDispatcher securityDispatcher;
	
	@Requirement
	private PlexusCipher chiper;
	
	/**
	 * {@inheritDoc}
	 */
	public String encrypt(String plaintext) throws CryptoException 
	{
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] encrypt(byte[] plaintext) throws CryptoException 
	{
		String encrypted = encrypt(new String(plaintext));
		return encrypted.getBytes();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int encrypt(ByteBuffer plaintext, ByteBuffer ciphertext) throws CryptoException 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String decrypt(String ciphertext) throws CryptoException
	{
		try {
			return securityDispatcher.decrypt(ciphertext);
		} catch (SecDispatcherException e) {
			throw new CryptoException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public byte[] decrypt(byte[] ciphertext) throws CryptoException 
	{
		String decrypted = decrypt(new String(ciphertext));
		return decrypted.getBytes();
	}

	/**
	 * {@inheritDoc}
	 */
	public int decrypt(ByteBuffer ciphertext, ByteBuffer plaintext) throws CryptoException 
	{
		String decrypted = decrypt(new String(ciphertext.array()));
		byte [] decryptedAsBytes = decrypted.getBytes();
		plaintext.put(decryptedAsBytes);
		plaintext.compact();
		return decryptedAsBytes.length;
		
	}

	/**
	 * {@inheritDoc}
	 */
	public SecretKey getKey() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String encryptSecretKey(String plaintext) 
	{
		// TODO Auto-generated method stub
//
//
//        DefaultPlexusCipher cipher = new DefaultPlexusCipher();
//        System.out.println( cipher.encryptAndDecorate( passwd, DefaultSecDispatcher.SYSTEM_PROPERTY_SEC_LOCATION ) );
		return null;
	}
	

}
