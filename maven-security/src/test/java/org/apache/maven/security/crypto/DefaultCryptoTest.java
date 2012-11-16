package org.apache.maven.security.crypto;

import junit.framework.Assert;

import org.apache.maven.security.crypto.Crypto;
import org.apache.maven.security.crypto.CryptoDecorator;
import org.apache.maven.security.crypto.DefaultCrypto;
import org.apache.maven.security.crypto.SecretKey;
import org.apache.maven.security.crypto.SecretKeyCrypto;
import org.apache.maven.security.util.FileLocator;
import org.apache.maven.security.util.TstSettingsSecurityFileLocator;
import org.codehaus.plexus.PlexusTestCase;

/**
 * Tests the {@link DefaultCrypto} class.
 * 
 * @author Karin
 */
public class DefaultCryptoTest
    extends PlexusTestCase
{
    private Crypto crypto = null;

    private CryptoDecorator decorator = null;

    protected void setUp()
    {
        try
        {
            super.setUp();
            crypto = lookup( Crypto.class );
            decorator = lookup( CryptoDecorator.class );

        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    protected void tearDown()
    {
        try
        {
            super.tearDown();
            crypto = null;
            decorator = null;
        }
        catch ( Exception e )
        {
            // do nothing
        }
    }

    /**
     * Tests the {@link SecretKeyCrypto#getKey()} method of the default implementation of {@link DefaultCrypto}
     * implementation.
     * 
     * @see TstSettingsSecurityFileLocator
     */
    public void testGetKey()
    {
        try
        {
            Assert.assertTrue( crypto instanceof SecretKeyCrypto );
            SecretKey key = ( (SecretKeyCrypto) crypto ).getKey();
            Assert.assertNotNull( key );
            Assert.assertEquals( "{GiTE7JtdcWUHNZYC+p5P3AZVLDzI7ygb0EOFiFZZ7P0=}", key.getValue() );
        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

    /**
     * Tests the {@link SecretKeyCrypto#encryptSecretKey(String)} method of the default implementation of
     * {@link DefaultCrypto} implementation.
     * 
     * @see TstSettingsSecurityFileLocator
     */
    public void testEncryptSecretKey()
    {
        final String encryptThis = "blablabla";

        try
        {
            Assert.assertTrue( crypto instanceof SecretKeyCrypto );
            FileLocator fileLocator = lookup( FileLocator.class );
            decorator = lookup( CryptoDecorator.class );

            // point out the test settings-security.xml file (see test/resources dir)
            System.setProperty( "settings.security", fileLocator.getLocation() );
            final String encrypted = ( (SecretKeyCrypto) crypto ).encryptSecretKey( encryptThis );

            Assert.assertNotNull( encrypted );
            Assert.assertNotSame( encryptThis, encrypted );
            // the SecretKeyCrypto.encryptSecretKey must not decorate the
            // encrypted string.
            Assert.assertFalse( decorator.isDecoratedText( encrypted ) );

            String encryptedDecorated = decorator.decorateText( encrypted );
            Assert.assertTrue( decorator.isDecoratedText( encryptedDecorated ) );

        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }

    }

    /**
     * Test to encrypt a string using the secret key found in the test/resources/settings-security.xml file.
     */
    public void testEncrypt()
    {

        try
        {
            final String txtToEncrypt = "secret123";

            FileLocator fileLocator = lookup( FileLocator.class );
            // point out the test settings-security.xml file (see test/resources dir)
            System.setProperty( "settings.security", fileLocator.getLocation() );

            String encrypted = crypto.encrypt( txtToEncrypt );

            Assert.assertNotNull( encrypted );
            Assert.assertNotSame( txtToEncrypt, encrypted );

            Assert.assertFalse( decorator.isDecoratedText( encrypted ) );

            String decoratedEnryped = decorator.decorateText( encrypted );
            Assert.assertTrue( decorator.isDecoratedText( decoratedEnryped ) );

        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }

    }

    /**
     * Test to decrypt a string encrypted with the secret key found in the test/resources/settings-security.xml file.
     */
    public void testDecrypt()
    {
        final String pwd = "secret512";

        FileLocator fileLocator;
        try
        {
            fileLocator = lookup( FileLocator.class );
            // point out the test settings-security.xml file (see test/resources dir)
            System.setProperty( "settings.security", fileLocator.getLocation() );
            String encrypted = crypto.encrypt( pwd );
            Assert.assertNotNull( encrypted );
            Assert.assertNotSame( pwd, encrypted );

            String decoratedEnryped = decorator.decorateText( encrypted );
            Assert.assertTrue( decorator.isDecoratedText( decoratedEnryped ) );

            String decryptedDecorated = crypto.decrypt( decoratedEnryped );
            Assert.assertEquals( pwd, decryptedDecorated );

        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }

    }

}
