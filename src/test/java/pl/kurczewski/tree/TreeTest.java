package pl.kurczewski.tree;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeTest {

    private Tree<String> tree;

    @Before
    public void setUp() throws Exception {
        Node<String> zero = new Node<>("0");
        Node<String> one = new Node<>("1");
        Node<String> two = new Node<>("2");
        Node<String> three = new Node<>("3");
        Node<String> four = new Node<>("4");
        Node<String> five = new Node<>("5");
        Node<String> six = new Node<>("6");

        zero.addChildren(one);
        zero.addChildren(two);

        one.addChildren(three);
        one.addChildren(four);
        one.addChildren(five);

        two.addChildren(four);
        two.addChildren(six);

        four.addChildren(five);
        four.addChildren(six);

        tree = new Tree<>(zero);
    }

    @Test
    public void should_return_all_elements_of_tree() throws Exception {
        String actualElements = tree.getElements().stream().sorted().collect(Collectors.toList()).toString();
        Assert.assertEquals("[0, 1, 2, 3, 4, 5, 6]", actualElements);
    }

    @Test
    public void should_return_all_elements_of_sub_tree() throws Exception {
        Node<String> childrenNode = tree.getRoot().getChildrens().get(0);

        String actualElements = new Tree<>(childrenNode).getElements().stream().sorted().collect(Collectors.toList()).toString();
        Assert.assertEquals("[1, 3, 4, 5, 6]", actualElements);
    }

    @Test
    public void should_return_upward_nodes() {
        // given
        Node<String> middleNode = tree.getNodes().get(3);

        // when
        List<Node<String>> nodes = new Tree<>(middleNode).getUpwardNodes();

        // then
        Assert.assertEquals("4", middleNode.getValue());
        String actualElements = nodes.stream().map(Node::getValue).sorted().collect(Collectors.toList()).toString();
        Assert.assertEquals("[0, 1, 2, 4]", actualElements);
    }

    @Test
    public void should_return_self_when_upward_nodes_of_root() {
        String actualElements = tree.getUpwardNodes().stream().map(Node::getValue).collect(Collectors.toList()).toString();
        Assert.assertEquals("[0]", actualElements);
    }

    @Test
    public void should_return_all_branches_in_tree() throws Exception {
        // given
        List<List<Node<String>>> tempBranches = tree.getBranches();

        // when
        List<List<String>> branches = new ArrayList<>();
        for(List<Node<String>> branch : tempBranches) {
            branches.add(branch.stream().map(Node::getValue).collect(Collectors.toList()));
        }

        // then
        String expected = "[[0, 2, 6], [0, 2, 4, 6], [0, 2, 4, 5], [0, 1, 5], [0, 1, 4, 6], [0, 1, 4, 5], [0, 1, 3]]";
        Assert.assertEquals(expected, branches.toString());
        Assert.assertEquals(7, branches.size());
    }

    @Test
    public void should_return_all_branches_in_sub_tree() throws Exception {
        // given
        Node<String> childrenNode = tree.getRoot().getChildrens().get(0);
        List<List<Node<String>>> tempBranches = new Tree<>(childrenNode).getBranches();

        // when
        List<List<String>> branches = new ArrayList<>();
        for(List<Node<String>> branch : tempBranches) {
            branches.add(branch.stream().map(Node::getValue).collect(Collectors.toList()));
        }

        // then
        Assert.assertEquals("[[1, 5], [1, 4, 6], [1, 4, 5], [1, 3]]", branches.toString());
        Assert.assertEquals(4, branches.size());
    }
}