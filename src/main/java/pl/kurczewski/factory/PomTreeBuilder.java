package pl.kurczewski.factory;

import pl.kurczewski.factory.pojo.Pom;
import pl.kurczewski.tree.Node;
import pl.kurczewski.tree.Tree;

import java.util.List;
import java.util.stream.Collectors;

public class PomTreeBuilder {

    public Tree<Pom> build(Pom root, List<Pom> poms) {

        List<Node<Pom>> nodes = poms.stream()
                .map(Node::new)
                .collect(Collectors.toList());

        for (Node<Pom> parentNode : nodes) {
            for (Node<Pom> childNode : nodes) {
                if (childNode.getValue().hasDependency(parentNode.getValue().getArtifact())) {
                    parentNode.addChildren(childNode);
                }
            }
        }

        Node<Pom> rootNode = new Node<>(root);
        nodes.stream().filter(node -> node.getParents().isEmpty()).forEach(rootNode::addChildren);

        return new Tree<>(rootNode);
    }

}
