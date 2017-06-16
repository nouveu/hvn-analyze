package pl.kurczewski.factory.pojo;

public class Plugin extends ArtifactDefinition {

    private final String version;

    public Plugin(String groupId, String artifactId, String version) {
        super(groupId, artifactId);
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

}
