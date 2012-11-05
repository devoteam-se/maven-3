package org.maven.security.util;

import java.io.IOException;
import java.io.InputStream;

//TODO: add copyright

/**
 * This class mainly supports access to files. 
 * The purpose is to provide an interface that treat files the same way whether they are 
 * in a file directory structure, inside an archive etc.
 * 
 * @author Karin Karlsson
 *
 */
public interface FileLocator 
{
	
	/**
	 * Locates the file.
	 * @return the path to located file.
	 * @throws IOException i/o error
	 */
	public abstract String getLocation() throws IOException;
}
