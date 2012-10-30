package org.apache.maven.security.crypto;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.plexus.components.cipher.PlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcher;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcherException;
import org.sonatype.plexus.components.sec.dispatcher.SecUtil;
import org.sonatype.plexus.components.sec.dispatcher.model.SettingsSecurity;

//TODO: add copyright

/**
 * This class handles encryption and decryption details.
 *
 * @author Karin Karlsson
 */
@Component(role = Crypto.class)
public class DefaultCrypto implements SecretKeyCrypto 
{
	
	/**
	 * Deals with decryption.
	 */
	@Requirement
	private SecDispatcher securityDispatcher;
	
	/**
	 * Deals with encryption.
	 */
	@Requirement
	private PlexusCipher cipher;
	
	/**
	 * This secret key is used when encrypting (by this implementation) and decrypting (by the securityDispatcher)
	 * the master password.
	 */
	private static final String DEFAULT_SECRET_KEY = "settings.security";
	
	/**
	 * {@inheritDoc}
	 */
	public String encrypt(String plaintext) throws CryptoException 
	{
		
		//encrypts a master password
		
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
		return new SecretKey() {

			public String getValue() throws CryptoException {
				
				//Reads the value from the {@code settings-security.xml} file.
				//the code is copied from plexus cipher lib
				
				final String location = System.getProperty(DEFAULT_SECRET_KEY, "~/.settings-security.xml");
				final String realLocation = location.charAt( 0 ) == '~' ? System.getProperty("user.home") + location.substring(1) : location;
				
				try {
					SettingsSecurity sec = SecUtil.read(realLocation, true);
					if (sec != null && sec.getMaster() != null) {
						return sec.getMaster();
					}
					
				} catch (SecDispatcherException e) {
					throw new CryptoException(e);
				}
				
				throw new CryptoException ("master password is not set");
			}
			
		};
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
