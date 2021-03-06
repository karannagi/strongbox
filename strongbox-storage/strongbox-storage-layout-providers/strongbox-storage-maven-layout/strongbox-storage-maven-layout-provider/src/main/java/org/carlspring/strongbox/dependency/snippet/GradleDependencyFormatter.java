package org.carlspring.strongbox.dependency.snippet;

import org.carlspring.strongbox.artifact.coordinates.ArtifactCoordinates;
import org.carlspring.strongbox.artifact.coordinates.MavenArtifactCoordinates;
import org.carlspring.strongbox.providers.layout.AbstractLayoutProvider;
import org.carlspring.strongbox.providers.layout.Maven2LayoutProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author carlspring
 */
@Component
public class GradleDependencyFormatter
        implements DependencySynonymFormatter
{

    private static final Logger logger = LoggerFactory.getLogger(AbstractLayoutProvider.class);

    public static final String ALIAS = "Gradle";

    @Inject
    private CompatibleDependencyFormatRegistry compatibleDependencyFormatRegistry;


    @PostConstruct
    @Override
    public void register()
    {
        compatibleDependencyFormatRegistry.addProviderImplementation(getLayout(), getFormatAlias(), this);

        logger.debug("Initialized the Gradle dependency formatter.");
    }

    @Override
    public String getLayout()
    {
        return Maven2LayoutProvider.ALIAS;
    }

    @Override
    public String getFormatAlias()
    {
        return ALIAS;
    }

    @Override
    public String getDependencySnippet(ArtifactCoordinates artifactCoordinates)
    {
        MavenArtifactCoordinates coordinates = (MavenArtifactCoordinates) artifactCoordinates;

        return "compile \"" +
               coordinates.getGroupId() + ":" +
               coordinates.getArtifactId() + ":" +
               coordinates.getVersion() +
               (coordinates.getClassifier() != null ? ":" + coordinates.getClassifier() : "") +
               (coordinates.getExtension() != null && !coordinates.getExtension().equals("jar") ?
                "@" + coordinates.getExtension() : "") +
               "\"\n";
    }

}
