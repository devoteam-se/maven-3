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

import java.io.IOException;

import org.apache.maven.security.util.FileLocator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.sonatype.plexus.components.sec.dispatcher.SecDispatcherException;
import org.sonatype.plexus.components.sec.dispatcher.SecUtil;
import org.sonatype.plexus.components.sec.dispatcher.model.SettingsSecurity;

@Component( role = SecretKey.class )
public final class DefaultSecretKey
    implements SecretKey
{
    
    /**
     * Locates the {@code settings-security.xml}
     */
    @Requirement
    FileLocator settingsSecurityLocator;
    
    /**
     * {@inheritDoc}
     */
    public String getValue()
        throws CryptoException
    {
        {

            try
            {
                // parse the settings-security.xml file
                SettingsSecurity sec = SecUtil.read( settingsSecurityLocator.getLocation(), true );
                if ( sec != null && sec.getMaster() != null )
                {
                    return sec.getMaster();
                }

            }
            catch ( SecDispatcherException e )
            {
                throw new CryptoException( e );
            }
            catch ( IOException e )
            {
                throw new CryptoException( e );
            }

            throw new CryptoException( "Master password is not set" );
        }
    }

}
