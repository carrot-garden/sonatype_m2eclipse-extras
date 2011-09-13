package org.sonatype.m2e.buildhelper.mojohandler;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.sonatype.m2e.buildhelper.ConfigureRawClasspathContext;
import org.sonatype.m2e.buildhelper.util.ClasspathUtils;

/**
 * {@link BuildhelperMojoHandler} that supports the <tt>add-test-source</tt> goal.
 * 
 * @author Phillip Webb
 */
public class AddTestSourceBuildhelperMojoHandler
    extends AbstractBuildhelperMojoHandler
{
    @Override
    protected String getGoal()
    {
        return "add-test-source";
    }

    @Override
    public void configureRawClasspath( ConfigureRawClasspathContext context )
        throws CoreException
    {
        File[] paths = context.getParameterValue( "sources", File[].class );
        IPath outputLocation = context.getTestOutputLocation();
        ClasspathUtils.addSourcePaths( context, paths, outputLocation );
    }
}