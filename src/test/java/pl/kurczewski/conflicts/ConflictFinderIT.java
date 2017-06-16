package pl.kurczewski.conflicts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.kurczewski.factory.pojo.Artifact;
import pl.kurczewski.factory.pojo.Pom;
import pl.kurczewski.factory.PomTreeBuilder;
import pl.kurczewski.tree.Tree;

import java.util.Arrays;
import java.util.List;

public class ConflictFinderIT {

    private Tree<Pom> pomTree;
    private Pom node1;
    private Pom node2;
    private Pom node3;
    private Pom node4;
    private Pom node5;

    @Before
    public void setUp() throws Exception {

        Pom root = new Pom(new Artifact("[]", "root", "[]"), "jar");
        node1 = new Pom(new Artifact("[]", "node-1", "[]"), "jar");
        node2 = new Pom(new Artifact("[]", "node-2", "[]"), "jar");
        node3 = new Pom(new Artifact("[]", "node-3", "[]"), "jar");
        node4 = new Pom(new Artifact("[]", "node-4", "[]"), "jar");
        node5 = new Pom(new Artifact("[]", "node-5", "[]"), "jar");

        List<Pom> poms = Arrays.asList(node1, node2, node3, node4, node5);

//        node1.addDependency(new Dependency(root.getArtifact(), "[]"));
//        node2.addDependency(new Dependency(root.getArtifact(), "[]"));
//
//        node3.addDependency(new Dependency(node1.getArtifact(), "[]"));
//
//        node4.addDependency(new Dependency(node1.getArtifact(), "[]"));
//        node4.addDependency(new Dependency(node2.getArtifact(), "[]"));
//
//        node5.addDependency(new Dependency(node1.getArtifact(), "[]"));
//        node5.addDependency(new Dependency(node4.getArtifact(), "[]"));

        PomTreeBuilder builder = new PomTreeBuilder();
        pomTree = builder.build(root, poms);
    }

    @Test
    public void name() {
        Assert.fail();
    }


    //    @Test
//    public void should_find_conflicts_for_node1() throws Exception {
//        DependencyConflicts dependencyConflicts = new DependencyConflicts(pomTree);
//        List<DependencyConflict> conflicts = dependencyConflicts.getUsersOf(new ArtifactDefinition(node1.getArtifact()));
//
//        Assert.assertEquals(new ArtifactDefinition(node4.getArtifact()), conflicts.get(0).getParentUser());
//        Assert.assertEquals(new ArtifactDefinition(node5.getArtifact()), conflicts.get(0).getChildUsers().get(0));
//        Assert.assertEquals(new ArtifactDefinition(node1.getArtifact()), new ArtifactDefinition(conflicts.get(0).getDependency()));
//        Assert.assertEquals(1, conflicts.get(0).getChildUsers().size());
//        Assert.assertEquals(1, conflicts.size());
//    }
//
//    @Test
//    public void should_find_conflicts_for_node2() throws Exception {
//        DependencyConflicts dependencyConflicts = new DependencyConflicts(pomTree);
//        List<DependencyConflict> conflicts = dependencyConflicts.getUsersOf(new ArtifactDefinition(node2.getArtifact()));
//
//        Assert.assertEquals(0, conflicts.size());
//    }
//
//    @Test
//    public void should_find_conflicts_for_node3() throws Exception {
//        DependencyConflicts dependencyConflicts = new DependencyConflicts(pomTree);
//        List<DependencyConflict> conflicts = dependencyConflicts.getUsersOf(new ArtifactDefinition(node3.getArtifact()));
//
//        Assert.assertEquals(0, conflicts.size());
//    }
//
//    @Test
//    public void should_find_conflicts_for_node4() throws Exception {
//        DependencyConflicts dependencyConflicts = new DependencyConflicts(pomTree);
//        List<DependencyConflict> conflicts = dependencyConflicts.getUsersOf(new ArtifactDefinition(node4.getArtifact()));
//
//        Assert.assertEquals(0, conflicts.size());
//    }
//
//    @Test
//    public void should_find_conflicts_for_node5() throws Exception {
//        DependencyConflicts dependencyConflicts = new DependencyConflicts(pomTree);
//        List<DependencyConflict> conflicts = dependencyConflicts.getUsersOf(new ArtifactDefinition(node5.getArtifact()));
//
//        Assert.assertEquals(0, conflicts.size());
//    }
}