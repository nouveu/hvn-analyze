package pl.kurczewski.factory.pojo;

public class Dependency extends Artifact {

    private final String scope;

    public Dependency(String groupId, String artifactId, String version, String scope) {
        super(groupId, artifactId, version);
        this.scope = scope;
    }

    public Dependency(Artifact artifact, String scope) {
        super(artifact);
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

}
