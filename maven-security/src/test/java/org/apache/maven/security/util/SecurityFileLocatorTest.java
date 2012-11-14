package org.apache.maven.security.util;

import java.io.File;
import junit.framework.Assert;

import org.apache.maven.security.util.FileLocator;
import org.apache.maven.security.util.SecurityFileLocator;
import org.codehaus.plexus.PlexusTestCase;

/**
 * Test the {@link #SecurityFileLocator} class.
 * 
 * @author Karin Karlsson
 */
public class SecurityFileLocatorTest
    extends PlexusTestCase
{

    // TODO: skip this test if settings-security.xml not present or generate a dummy one?

    private String pathToSecFile = null;

    /**
     * run the test if the security file exist on current work station if not, then it is no point in running the test
     * the security settings are optional
     */
    private boolean run = true;

    protected void setUp()
    {

        try
        {
            super.setUp();
            final String pathSep = System.getProperty( "file.separator" );
            StringBuilder builder = new StringBuilder();
            builder.append( System.getProperty( "user.home" ) ).append( pathSep ).append( ".m2" ).append( pathSep ).append( "settings-security.xml" );
            pathToSecFile = builder.toString();

            File f = new File( pathToSecFile );
            run = f.exists();
        }
        catch ( Exception e )
        {
            Assert.fail( e.getMessage() );
        }

    }

    /**
     * Test the {@link SecurityFileLocator#getLocation()} method. The test is successful if it finds the
     * {@code settings-security.xml} in the {@code user.home} directory.
     * <p/>
     * Note that this test will run if and only if current workstation has activated the security.
     * <p/>
     * Note again, this class is also tested in an integration test.
     */
    public void testGetLocation()
    {

        if ( run )
        {
            try
            {

                Assert.assertNotNull( getContainer().getComponentDescriptor( FileLocator.class.getName(),
                                                                             "settings-security" ) );
                FileLocator fileLocator = getContainer().lookup( FileLocator.class, "settings-security" );
                String path = fileLocator.getLocation();
                Assert.assertEquals( pathToSecFile, path );
            }
            catch ( Exception e )
            {
                Assert.fail( e.getMessage() );
            }
        }

    }

}
