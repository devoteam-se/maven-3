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
    @Requirement( hint = "maven" )
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

		try 
		{
			String decryptedMasterKey = cipher.decrypt(key, DEFAULT_SECRET_KEY);
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
				
				//Reads the value from the settings-security.xml file.
				//FIXME: the code is copied from plexus sec dispatcher lib
				
				final String location = System.getProperty(DEFAULT_SECRET_KEY, "~/.m2/settings-security.xml");
				final String realLocation = location.charAt( 0 ) == '~' ? System.getProperty("user.home") + location.substring(1) : location;
				
				try {
					SettingsSecurity sec = SecUtil.read(realLocation, true);
					if (sec != null && sec.getMaster() != null) {
						return sec.getMaster();
					}
					
				} catch (SecDispatcherException e) {
					throw new CryptoException(e);
				}
				
				throw new CryptoException ("Master password is not set");
			}
			
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public String encryptSecretKey(String plainkey) throws CryptoException
	{
		try 
		{
			return cipher.encrypt(plainkey, DEFAULT_SECRET_KEY);
		} 
		catch (PlexusCipherException e) 
		{
			throw new CryptoException(e);
		}
	}
	
	/**
	 * Test if the text is decorated or not.
	 * @param text the text to test
	 * @return true if the text is decorated, false otherwise
	 */
	private boolean isDecorated(final String text) 
	{
		//PlexusChiper.isEncryptedString method checks if given string is decorated
		return cipher.isEncryptedString(text);
	}
	
}
