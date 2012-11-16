package org.apache.maven.security.util;

import java.io.File;
import java.io.IOException;

import org.apache.maven.security.util.FileLocator;

public class TstSettingsSecurityFileLocator
    implements FileLocator

{
    private final String pathSep = System.getProperty( "path.separator" );

    public String getLocation()
        throws IOException
    {

        final String clsPath = System.getProperty( "java.class.path" );
        String[] paths = clsPath.split( pathSep );

        for ( String path : paths )
        {
            if ( path.indexOf( "maven-security" ) > 0 && path.indexOf( "test" ) > 0 )
            {
                File settingsFile = new File( path, "security-settings.xml" );
                return settingsFile.getCanonicalPath();
            }
        }

        throw new IOException( "Unable to locate security-settings.xml" );

    }

}
