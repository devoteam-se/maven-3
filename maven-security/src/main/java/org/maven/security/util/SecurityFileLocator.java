package org.maven.security.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.codehaus.plexus.component.annotations.Component;

//TODO: add copyright

/**
 * Locates the {@code settings-security.xml} file.
 * 
 * @author Karin Karlsson
 *
 */
@Component(role = FileLocator.class, hint = "settings-security")
public class SecurityFileLocator implements FileLocator 
{
	
	private File secFile;
	
	public SecurityFileLocator() {
		secFile = new File(System.getProperty("user.home") + "/.m2/settings-security.xml");
	}
	
	/**
	 * {@inheritDoc}
	 * @throws FileNotFoundException unable to locate the {@code settings-security.xml} file
	 */
	public String getLocation() throws IOException 
	{
		if (secFile.exists()) {
			return secFile.getCanonicalPath();
		}
		throw new FileNotFoundException(secFile.getPath());
		
	}
	
	

}
