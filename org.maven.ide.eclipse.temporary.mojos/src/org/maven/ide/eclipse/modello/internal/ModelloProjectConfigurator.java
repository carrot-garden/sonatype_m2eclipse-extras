/*******************************************************************************
 * Copyright (c) 2008 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.maven.ide.eclipse.modello.internal;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.plugin.MojoExecution;
import org.eclipse.m2e.core.project.configurator.AbstractBuildParticipant;
import org.eclipse.m2e.core.project.configurator.MojoExecutionBuildParticipant;
import org.maven.ide.eclipse.mojos.internal.AbstractJavaProjectConfigurator;

public class ModelloProjectConfigurator
    extends AbstractJavaProjectConfigurator
{

    @Override
    protected MojoExecutionKey getMojoExecutionKey()
    {
        return new MojoExecutionKey( "org.codehaus.modello", "modello-maven-plugin", "[1.0.1,)", null );
    }

    @Override
    protected boolean isSupportedExecution( MojoExecution mojoExecution )
    {
        boolean supported = super.isSupportedExecution( mojoExecution );

        if ( supported )
        {
            return true;
        }

        if ( !"org.sonatype.plugins".equals( mojoExecution.getGroupId() )
            || !"modello-plugin-upgrade".equals( mojoExecution.getArtifactId() ) )
        {
            return false;
        }

        VersionRange range;
        try
        {
            range = VersionRange.createFromVersionSpec( "[0.0.2-SNAPSHOT,)" );
        }
        catch ( InvalidVersionSpecificationException e )
        {
            throw new IllegalStateException( "Can't parse version range", e );
        }

        DefaultArtifactVersion version = new DefaultArtifactVersion( mojoExecution.getVersion() );

        return range.containsVersion( version );
    }

    @Override
    public AbstractBuildParticipant doGetBuildParticipant( MojoExecution execution )
    {
        return new MojoExecutionBuildParticipant( execution, true );
    }

}
