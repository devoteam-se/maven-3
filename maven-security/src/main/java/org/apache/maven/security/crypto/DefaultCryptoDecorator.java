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

/**
 * Decorates and undecorates text.
 * 
 * @author Karin Karlsson
 */
@Component( role = CryptoDecorator.class )
public final class DefaultCryptoDecorator
    extends CryptoDecorator
{

    @Requirement
    private PlexusCipher cipher;

    public DefaultCryptoDecorator()
    {

    }

    /**
     * {@inheritDoc}
     * <p/>
     * Decorates the text according to the <code>PlexusChiper</code> implementation.
     */
    @Override
    public String decorateText( final String text )
    {

        return cipher.decorate( text );
    }

    /**
     * {@inheritDoc}
     * 
     * @see #decorateText(String)
     */
    @Override
    public String unDecorateText( final String text )
        throws CryptoException
    {
        try
        {
            return cipher.unDecorate( text );
        }
        catch ( PlexusCipherException e )
        {
            throw new CryptoException( e );
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see #decorateText(String)
     */
    @Override
    public boolean isDecoratedText( String text )
        throws CryptoException
    {
        // PlexusCipher seams to equalise encryption and decoration.
        return cipher.isEncryptedString( text );
    }

}
