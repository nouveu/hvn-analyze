package pl.kurczewski.factory.pojo;

public class ArtifactDefinition implements Comparable<ArtifactDefinition> {

    private final String groupId;
    private final String artifactId;

    public ArtifactDefinition(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public String toString() {
        return groupId + ':' + artifactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtifactDefinition that = (ArtifactDefinition) o;

        return (groupId != null ? groupId.equals(that.groupId) : that.groupId == null) &&
                (artifactId != null ? artifactId.equals(that.artifactId) : that.artifactId == null);
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (artifactId != null ? artifactId.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ArtifactDefinition definition) {
        return toString().compareTo(definition.toString());
    }
}
