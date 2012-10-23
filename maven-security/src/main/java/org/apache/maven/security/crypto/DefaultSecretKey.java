package org.apache.maven.security.crypto;

import org.codehaus.plexus.component.annotations.Component;

/**
 * The default secret key.
 * 
 * @author Karin Karlsson
 *
 */
@Component(role = SecretKey.class)
public class DefaultSecretKey implements SecretKey 
{
	public String getValue() 
	{
		//TODO: implement
		return null;
	}
}
