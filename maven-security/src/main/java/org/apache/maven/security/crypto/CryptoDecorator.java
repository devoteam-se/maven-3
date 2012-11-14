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

import org.codehaus.plexus.component.annotations.Requirement;

/**
 * Decorates ciphered text.
 * 
 * @author Karin Karlsson
 */
public abstract class CryptoDecorator
    implements Crypto
{

    /**
     * The crypto to decorate.
     */
    @Requirement
    protected Crypto decoratedCrypto;

    public CryptoDecorator()
    {
    }

    /**
     * Encloses the text with a prefix and a postfix.
     * 
     * @param text
     * @return the text with a prefix and a postfix.
     * @throws CryptoException
     */
    public abstract String decorateText( final String text )
        throws CryptoException;

    /**
     * Removes the decoration from the text.
     * 
     * @param text the text to undecorate
     * @return the text without decoration.
     * @throws CryptoException
     * @see #decorateText(String)
     */
    public abstract String unDecorateText( final String text )
        throws CryptoException;

    /**
     * Checks if the text is decorated or not.
     * 
     * @param text
     * @return true if the text is decorated, false if not
     * @throws CryptoException
     */
    public abstract boolean isDecoratedText( final String text )
        throws CryptoException;

    /**
     * {@inheritDoc}
     */
    public String encrypt( String plaintext )
        throws CryptoException
    {
        return decoratedCrypto.encrypt( plaintext );
    }

    /**
     * {@inheritDoc}
     */
    public String decrypt( String ciphertext )
        throws CryptoException
    {
        return decoratedCrypto.decrypt( ciphertext );
    }

}
