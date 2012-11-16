package org.apache.maven.security.crypto;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.plexus.components.cipher.PlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcher;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcherException;

/**
 * This class handles encryption and decryption details.
 * 
 * @author Karin Karlsson
 */
@Component( role = Crypto.class )
public class DefaultCrypto
    implements SecretKeyCrypto
{

    @Requirement
    private SecretKey secretKey;

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
     * This secret key is used when encrypting (by this implementation) and decrypting (by the securityDispatcher) dthe
     * master password.
     */
    private static final String DEFAULT_SECRET_KEY = "settings.security";

    /**
     * {@inheritDoc}
     */
    public String encrypt( String plaintext )
        throws CryptoException
    {
        String key = getKey().getValue();
        if ( isDecorated( key ) )
        {
            try
            {
                key = cipher.unDecorate( key );
            }
            catch ( PlexusCipherException e )
            {
                throw new CryptoException( e );
            }
        }

        try
        {
            String decryptedMasterKey = cipher.decrypt( key, DEFAULT_SECRET_KEY );
            return cipher.encrypt( plaintext, decryptedMasterKey );
        }
        catch ( PlexusCipherException e )
        {
            throw new CryptoException( e );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String decrypt( String ciphertext )
        throws CryptoException
    {
        try
        {
            return securityDispatcher.decrypt( ciphertext );
        }
        catch ( SecDispatcherException e )
        {
            throw new CryptoException( e );
        }
    }

    /**
     * {@inheritDoc}
     */
    public SecretKey getKey()
    {
        return secretKey;
    }

    /**
     * {@inheritDoc}
     */
    public String encryptSecretKey( String plainkey )
        throws CryptoException
    {
        try
        {
            return cipher.encrypt( plainkey, DEFAULT_SECRET_KEY );
        }
        catch ( PlexusCipherException e )
        {
            throw new CryptoException( e );
        }
    }

    /**
     * Test if the text is decorated or not.
     * 
     * @param text the text to test
     * @return true if the text is decorated, false otherwise
     */
    private boolean isDecorated( final String text )
    {
        // PlexusChiper.isEncryptedString method checks if given string is decorated
        return cipher.isEncryptedString( text );
    }

}
