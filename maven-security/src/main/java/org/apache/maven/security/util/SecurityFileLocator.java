package org.apache.maven.security.util;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.codehaus.plexus.component.annotations.Component;

/**
 * Locates the {@code settings-security.xml} file.
 * 
 * @author Karin Karlsson
 */
@Component( role = FileLocator.class, hint = "settings-security" )
public class SecurityFileLocator
    implements FileLocator
{

    private File secFile;

    public SecurityFileLocator()
    {
        secFile = new File( System.getProperty( "user.home" ) + "/.m2/settings-security.xml" );
    }

    /**
     * {@inheritDoc}
     * 
     * @throws FileNotFoundException unable to locate the {@code settings-security.xml} file
     */
    public String getLocation()
        throws IOException
    {
        if ( secFile.exists() )
        {
            return secFile.getCanonicalPath();
        }
        throw new FileNotFoundException( secFile.getPath() );

    }

}
