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
 * This class handles cryptographic details.
 * 
 * @author Karin Karlsson
 */
public interface Crypto
{
    /**
     * Encrypt plain text string and return an output cipher text.
     * 
     * @param plaintext the string to encrypt
     * @return the cipher text
     * @throws CryptoException if encryption fails
     */
    public String encrypt( final String plaintext )
        throws CryptoException;

    /**
     * Decrypt cipher text and return a plain text output.
     * 
     * @param ciphertext the string to decrypt
     * @return the decrypted string
     * @throws CryptoException if decryption fails
     */
    public String decrypt( final String ciphertext )
        throws CryptoException;

}
