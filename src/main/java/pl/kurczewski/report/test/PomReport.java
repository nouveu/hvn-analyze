package pl.kurczewski.report.test;

import pl.kurczewski.factory.pojo.Artifact;
import pl.kurczewski.factory.pojo.ArtifactDefinition;
import pl.kurczewski.factory.pojo.Dependency;
import pl.kurczewski.factory.pojo.Pom;
import pl.kurczewski.tree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Deprecated
public class PomReport {

    private final Tree<Pom> tree;
    private List<String> conflictsList = new ArrayList<>();

    private Map<String, List<DependencyConflict>> conflictsMap;
    private Map<String, List<DependencyUser>> usersMap;
    private List<String> modules;
    private List<String> dependencies;

    public PomReport(Tree<Pom> tree) {
        this.tree = tree;

        buildUsersList();
        buildConflictList();
    }

    public List<DependencyConflict> getConflictsOf(ArtifactDefinition artifact) {
        return conflictsMap.getOrDefault(artifact, new ArrayList<>());
    }

    public List<DependencyUser> getUsersOf(ArtifactDefinition artifact) {
        return usersMap.get(artifact);
    }

    public List<String> getModules() {
        if (modules == null) {
            modules = tree.getElements().stream()
                    .map(Pom::getArtifact)
                    .map(Artifact::toString)
                    .sorted()
                    .collect(Collectors.toList());
        }
        return modules;
    }

    public List<String> getDependencies() {
        if (dependencies == null) {
            dependencies = tree.getElements().stream()
                    .flatMap(pom -> pom.getDependencies().stream())
                    .map(Dependency::toString)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            getModules().forEach(dependencies::remove);
        }
        return dependencies;
    }

    public Tree<Pom> getTree() {
        return tree;
    }

    private void buildUsersList() {
//        List<Pom> poms = tree.getElements();
//        List<DependencyUser> usersList = new ArrayList<>();
//        for (Pom pom : poms) {
//            pom.getDependencies().stream()
//                    .map(dependency -> new DependencyUser(dependency, pom.getArtifact().getDefinition()))
//                    .distinct().forEach(usersList::add);
//        }
//        usersMap = usersList.stream()
//                .collect(Collectors.groupingBy(dependencyUser -> new ArtifactDefinition(dependencyUser.getDependency())));
    }

    private void buildConflictList() {
//        this.tree.getElements().stream()
//                .flatMap(pom -> pom.getDependencies().stream())
//                .distinct()
//                .forEach(dependency -> conflictsList.addAll(findConflicts(dependency)));

//        conflictsMap = conflictsList.stream()
//                .collect(Collectors.groupingBy(dependencyConflict ->
//                        new ArtifactDefinition(dependencyConflict.getDependency())));
    }

    private List<DependencyConflict> findConflicts(Dependency dependency) {
//        List<List<Node<Pom>>> branches = tree.getBranches();
//        List<DependencyConflict> conflictsList = new ArrayList<>();
////        ArtifactDefinition dependencyDef = new ArtifactDefinition(dependency);
////        List<DependencyUser> dependencyUsers = getUsersOf(dependencyDef);
//
//        Map<ArtifactDefinition, Set<ArtifactDefinition>> conflictList = new HashMap<>();
//
//        List<ArtifactDefinition> parentUsers = tree.getElements().stream()
//                .filter(pomNode -> pomNode.hasDependency(dependencyDef))
//                .map(Pom::getArtifact)
//                .map(ArtifactDefinition::new)
//                .distinct()
//                .collect(Collectors.toList());
//
//        for (List<Node<Pom>> branch : branches) {
//            List<ArtifactDefinition> usersInBranch = branch.stream()
//                    // TODO remove unnecessary wrapping classes in tree.branch()
//                    .map(Node::getValue)
//                    .map(Pom::getArtifact)
//                    .map(ArtifactDefinition::new)
//                    .filter(definition -> parentUsers.stream().anyMatch(definition::equals))
//                    .collect(Collectors.toList());
//
//            if (usersInBranch.size() <= 1) continue;
//
//            ArtifactDefinition parentUser = usersInBranch.remove(0);
//            conflictList.putIfAbsent(parentUser, new HashSet<>());
//            conflictList.get(parentUser).addAll(usersInBranch);
//        }
//
//        for(ArtifactDefinition parentUser : conflictList.keySet()) {
//            List<ArtifactDefinition> childUsers = conflictList.get(parentUser).stream()
//                    .sorted()
//                    .collect(Collectors.toList());
//
//            if (!childUsers.isEmpty()) {
//                conflictsList.add(new DependencyConflict(dependency, parentUser, childUsers));
//            }
//        }
//
//        return conflictsList;
        return null;
    }
}
