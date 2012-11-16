package org.apache.maven.security.crypto;

import junit.framework.Assert;

import org.apache.maven.security.crypto.CryptoDecorator;
import org.codehaus.plexus.PlexusTestCase;

/**
 * Tests the {@link CryptoDecorator} class.
 * 
 * @author Karin Karlsson
 *
 */
public class CryptoDecoratorTest
    extends PlexusTestCase
{
    protected void setUp()
    {
        try
        {
            super.setUp();
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
        }
        catch ( Exception e )
        {
            // do nothing
        }
    }

    public void testDecoration()
    {
        try
        {
            CryptoDecorator decorator = lookup( CryptoDecorator.class );
            String decoratedText = decorator.decorateText( "bla" );
            Assert.assertTrue( decorator.isDecoratedText( decoratedText ) );
        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }
    }

}
