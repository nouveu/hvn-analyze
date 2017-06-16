package pl.kurczewski.report.test;

import pl.kurczewski.factory.pojo.ArtifactDefinition;
import pl.kurczewski.factory.pojo.Dependency;

public class DependencyUser {

    private final Dependency dependency;
    private final ArtifactDefinition artifact;

    public DependencyUser(Dependency dependency, ArtifactDefinition artifact) {
        this.dependency = dependency;
        this.artifact = artifact;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public ArtifactDefinition getArtifact() {
        return artifact;
    }

}
