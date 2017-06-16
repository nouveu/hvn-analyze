package pl.kurczewski.report.test;

import pl.kurczewski.factory.pojo.ArtifactDefinition;
import pl.kurczewski.factory.pojo.Dependency;

import java.util.List;

public class DependencyConflict {

    private final Dependency dependency;
    private final ArtifactDefinition parentUser;
    private final List<ArtifactDefinition> childUsers;

    public DependencyConflict(Dependency dependency, ArtifactDefinition parentUser, List<ArtifactDefinition> childUsers) {
        this.dependency = dependency;
        this.parentUser = parentUser;
        this.childUsers = childUsers;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public ArtifactDefinition getParentUser() {
        return parentUser;
    }

    public List<ArtifactDefinition> getChildUsers() {
        return childUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DependencyConflict that = (DependencyConflict) o;

        if (!dependency.equals(that.dependency)) return false;
        if (!parentUser.equals(that.parentUser)) return false;
        return childUsers.equals(that.childUsers);
    }

    @Override
    public int hashCode() {
        int result = dependency.hashCode();
        result = 31 * result + parentUser.hashCode();
        result = 31 * result + childUsers.hashCode();
        return result;
    }
}
