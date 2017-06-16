package pl.kurczewski.tree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<T> {

    private Node<T> root;

    public Tree(Node<T> root) {
        this.root = root;
    }

    public Node<T> getRoot() {
        return root;
    }

    public List<Node<T>> getUpwardNodes() {
        List<Node<T>> items = new ArrayList<>();
        Stack<Node<T>> recursion = new Stack<>();
        recursion.push(root);

        while(!recursion.isEmpty()) {
            Node<T> head = recursion.pop();
            items.add(head);

            for(Node<T> node : head.getParents()) {
                recursion.push(node);
            }
        }
        return items.stream().distinct().collect(Collectors.toList());
    }

    public List<Node<T>> getNodes() {
        List<Node<T>> items = new ArrayList<>();
        Stack<Node<T>> recursion = new Stack<>();
        recursion.push(root);

        while(!recursion.isEmpty()) {
            Node<T> head = recursion.pop();
            items.add(head);

            for(Node<T> node : head.getChildrens()) {
                recursion.push(node);
            }
        }
        return items.stream().distinct().collect(Collectors.toList());
    }

    @Deprecated
    public List<T> getElements() {
        Set<T> items = new LinkedHashSet<>();
        Stack<Node<T>> recursion = new Stack<>();
        recursion.push(root);

        while(!recursion.isEmpty()) {
            Node<T> head = recursion.pop();
            items.add(head.getValue());

            for(Node<T> node : head.getChildrens()) {
                recursion.push(node);
            }
        }
        return new ArrayList<>(items);
    }

    public List<List<Node<T>>> getBranches() {
        List<List<Node<T>>> branches = new ArrayList<>();

        Stack<Node<T>> tempBranch = new Stack<>();
        Stack<Node<T>> recursion = new Stack<>();
        recursion.push(root);

        while(!recursion.isEmpty()) {
            Node<T> head = recursion.pop();
            tempBranch.add(head);

            for(Node<T> node : head.getChildrens()) {
                recursion.push(node);
            }

            if(head.getChildrens().isEmpty()) {
                branches.add(new ArrayList<>(tempBranch));
                while(!recursion.isEmpty() && !recursion.peek().isChildOf(tempBranch.peek())) {
                    tempBranch.pop();
                }
            }
        }
        return branches;
    }
}
