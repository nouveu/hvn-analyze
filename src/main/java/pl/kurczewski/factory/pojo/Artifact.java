package pl.kurczewski.factory.pojo;

public class Artifact extends ArtifactDefinition {

    protected final String version;

    public Artifact(String groupId, String artifactId, String version) {
        super(groupId, artifactId);
        this.version = version;
    }

    public Artifact(Artifact artifact) {
        this(artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion());
    }

    public String getVersion() {
        return version;
    }

}
