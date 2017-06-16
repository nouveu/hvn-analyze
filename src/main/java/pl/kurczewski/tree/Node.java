package pl.kurczewski.tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T value;
    private List<Node<T>> parents = new ArrayList<>();
    private List<Node<T>> childrens = new ArrayList<>();

    public Node(T value) {
        this.value = value;
    }

    public void addChildren(Node<T> node) {
        node.addParent(this);
        childrens.add(node);
    }

    public List<Node<T>> getChildrens() {
        return childrens;
    }

    public T getValue() {
        return value;
    }

    private void addParent(Node<T> parent) {
        parents.add(parent);
    }

    public List<Node<T>> getParents() {
        return parents;
    }

    public boolean isChildOf(Node<T> node) {
        return parents.stream().anyMatch(parent -> parent.equals(node));
    }

    @Override
    public String toString() {
        return "Node{value=" + value + "}";
    }
}
