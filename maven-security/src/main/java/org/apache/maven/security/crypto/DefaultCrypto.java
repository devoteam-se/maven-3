package org.apache.maven.security.crypto;


import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.plexus.components.cipher.PlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
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
	private PlexusCipher cipher;
	
	private static final String DEFAULT_SECRET_KEY = "settings.security";
	
	/**
	 * {@inheritDoc}
	 */
	public String encrypt(String plaintext) throws CryptoException 
	{
		String key = getKey().getValue();
		if (isDecorated(key)) 
		{
			try 
			{
				key = cipher.unDecorate(key);
			} 
			catch (PlexusCipherException e) 
			{
				throw new CryptoException(e);
			}
		}
		String decryptedMasterKey;
		try 
		{
			decryptedMasterKey = cipher.decrypt(getKey().getValue(), DEFAULT_SECRET_KEY);
			return cipher.encrypt(plaintext, decryptedMasterKey);
		} 
		catch (PlexusCipherException e) 
		{
			throw new CryptoException(e);
		}
		
		
	}

	
	
	/**
	 * {@inheritDoc}
	 */
	public String decrypt(String ciphertext) throws CryptoException
	{
		try 
		{
			return securityDispatcher.decrypt(ciphertext);
		} 
		catch (SecDispatcherException e) 
		{
			throw new CryptoException(e);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public SecretKey getKey() 
	{
		// TODO Auto-generated method stub
		// settings-security.xml via maven 
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String encryptSecretKey(String plaintext) throws CryptoException
	{

		try 
		{
			return cipher.encrypt(plaintext, DEFAULT_SECRET_KEY);
		} 
		catch (PlexusCipherException e) 
		{
			throw new CryptoException(e);
		}

	}
	
	/**
	 * Test if the text is decorated or not.
	 * @param text the text to test
	 * @return true if the text starts with the 
	 * {@code PlexusCipher.ENCRYPTED_STRING_DECORATION_START} character and 
	 * ends with the {@code PlexusCipher.ENCRYPTED_STRING_DECORATION_STOP} character.
	 */
	private boolean isDecorated(final String text) 
	{
		StringBuilder builder = new StringBuilder(text);
		if (builder.charAt(0) == PlexusCipher.ENCRYPTED_STRING_DECORATION_START && 
				builder.charAt(builder.length() - 1) == PlexusCipher.ENCRYPTED_STRING_DECORATION_STOP) 
		{
			return true;
		}
		return false;
	}
	

}
