package org.apache.maven.security.crypto;

import org.sonatype.plexus.components.cipher.PlexusCipher;

//TODO: add copyright

/**
 * Decorates ciphered text.
 * 
 * @author Karin Karlsson
 *
 */
public class DefaultCryptoDecorator extends CryptoDecorator 
{

	private static final char PREFIX = PlexusCipher.ENCRYPTED_STRING_DECORATION_START;
	private static final char POSTFIX = PlexusCipher.ENCRYPTED_STRING_DECORATION_STOP;

	public DefaultCryptoDecorator(final Crypto crypto) 
	{
		super(crypto);
	}
	
	/**
	 * {@inheritDoc}
	 * <p/>The decorated text will start with a {@link PlexusCipher.ENCRYPTED_STRING_DECORATION_START} character 
	 * and end with a {@link PlexusCipher.ENCRYPTED_STRING_DECORATION_STOP} character.
	 * 
	 */
	@Override
	public String decorateText(final String text) 
	{
		final StringBuilder builder = new StringBuilder(text);
		builder.insert(0, PREFIX);
		builder.append(POSTFIX);
		return builder.toString();

	}
	
	/**
	 * {@inheritDoc}
	 * <p/>Undecorates the text if the text starts with the 
	 * {@link PlexusCipher.ENCRYPTED_STRING_DECORATION_START} character and ends with the 
	 * {@link PlexusCipher.ENCRYPTED_STRING_DECORATION_STOP} character.
	 * If the text doesn't start and end with these characters the return is text.
	 */
	@Override
	public String unDecorateText(final String text) 
	{
		final StringBuilder builder = new StringBuilder(text);
		if (builder.charAt(0) == PREFIX && builder.charAt(builder.length() - 1) == POSTFIX) 
		{
			builder.deleteCharAt(0);
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		}
		return text;
	}
	
	

}
