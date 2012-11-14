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

/**
 * Offers a symmetric-key cryptography where the encryption of plain text and the decryption of cipher text use the same
 * key. The key represents a shared secret between two (or more) parties. Both parties must have access to the secret
 * key.
 * 
 * @author Karin Karlsson
 */
public interface SecretKeyCrypto
    extends Crypto
{

    /**
     * Encrypts the secret key.
     * <p/>
     * A fundamental problem with secret key encryption is that somehow the secret key has to be delivered to the
     * recipient of the message in a secure way. A encrypted secret key will at least complicate the possibility for any
     * unauthorized user to access a message encrypted with a secret key.
     * <p/>
     * Note that the encryption of the secret key must have a secret key itself. This particular secret key should not
     * be visible at all.
     * 
     * @param plainkey the secret key to encrypt
     * @return the encrypted secret key
     * @throws CryptoException encryption error
     */
    public String encryptSecretKey( final String plainkey )
        throws CryptoException;

    /**
     * Returns the secret key to use when encrypting plain text and decrypting cipher text
     * 
     * @return the secret key
     */
    public SecretKey getKey();
}
