package org.apache.maven.security.crypto;

//TODO: add copyright

/**
 * Decorates ciphered text.
 * 
 * @author Karin Karlsson
 *
 */
public abstract class CryptoDecorator implements Crypto 
{
	
	/**
	 * The crypto to decorate.
	 */
	protected final Crypto decoratedCrypto;
	
	public CryptoDecorator(final Crypto crypto)
	{
		this.decoratedCrypto = crypto;
	}

	/**
	 * Encloses the text with a prefix and a postfix.
	 * @param text 
	 * @return the text with a prefix and a postfix.
	 */
	public abstract String decorateText(final String text);
	
	/**
	 * Removes the decoration from the text.
	 * @param text the text to undecorate
	 * @return the text without decoration.
	 * @see #decorateText(String)
	 */
	public abstract String unDecorateText(final String text);
	
	/**
	 * {@inheritDoc}
	 */
	public String encrypt(String plaintext) throws CryptoException
	{
		return decoratedCrypto.encrypt(plaintext);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String decrypt(String ciphertext) throws CryptoException
	{
		return decoratedCrypto.decrypt(ciphertext);
	}
	
}
