package org.codehaus.mojo.tidy;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.WriterFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * Tidy up the <code>pom.xml</code> into the canonical order.
 */
@Mojo( name = "pom", requiresProject = true, requiresDirectInvocation = true )
public class PomMojo
    extends TidyMojo
{
    @Override
    protected void executeForPom( String pom )
        throws MojoExecutionException, MojoFailureException
    {
        try
        {
            String tidyPom = tidy( pom );
            writePom( tidyPom );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( e.getMessage(), e );
        }
    }

    /**
     * Replaces the current POM with a new one.
     */
    private void writePom( String pom )
        throws IOException
    {
        Writer writer = WriterFactory.newXmlWriter( project.getFile() );
        try
        {
            IOUtil.copy( pom, writer );
        }
        finally
        {
            IOUtil.close( writer );
        }
    }
}
