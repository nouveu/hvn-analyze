package pl.kurczewski.factory.pojo;

import java.util.ArrayList;
import java.util.List;

public class Pom {

    private final Artifact artifact;
    private final String packaging;

    private Artifact parent;

    private final List<Dependency> dependencies = new ArrayList<>();
    private final List<Dependency> managedDependencies = new ArrayList<>();

    private final List<Plugin> plugins = new ArrayList<>();
    private final List<Plugin> managedPlugins = new ArrayList<>();

    public Pom(Artifact artifact, String packaging) {
        this.artifact = artifact;
        this.packaging = packaging;
    }

    public void setParent(Artifact parent) {
        this.parent = parent;
    }

    public void addDependency(Dependency dependency) {
        dependencies.add(dependency);
    }

    public void addManagedDependency(Dependency dependency) {
        managedDependencies.add(dependency);
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List<Dependency> getManagedDependencies() {
        return managedDependencies;
    }

    public void addPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    public void addManagedPlugin(Plugin plugin) {
        managedPlugins.add(plugin);
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public List<Plugin> getManagedPlugins() {
        return managedPlugins;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public Artifact getParent() {
        return parent;
    }

    public boolean hasDependency(ArtifactDefinition artifactDefinition) {
        return getDependencies().stream().map(Dependency::toString).anyMatch(a -> a.equals(artifactDefinition.toString()));
    }

    @Deprecated
    public boolean hasDependency(Artifact artifact, String wildcard) {
        for (Dependency pomDependency : dependencies) {
            if (!pomDependency.getArtifactId().equals(wildcard) && !artifact.getArtifactId().equals(wildcard)) {
                if (!pomDependency.getArtifactId().equals(artifact.getArtifactId())) continue;
            }
            if (!pomDependency.getGroupId().equals(wildcard) && !artifact.getGroupId().equals(wildcard)) {
                if (!pomDependency.getGroupId().equals(artifact.getGroupId())) continue;
            }
            if (!pomDependency.getVersion().equals(wildcard) && !artifact.getVersion().equals(wildcard)) {
                if (!pomDependency.getVersion().equals(artifact.getVersion())) continue;
            }
            return true;
        }
        return false;
    }

    public String getPackaging() {
        return packaging;
    }

    @Override
    public String toString() {
        return artifact.toString();
    }
}
