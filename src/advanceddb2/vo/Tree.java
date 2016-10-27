package advanceddb2.vo;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.data = rootData;
        root.children = new ArrayList<Node<T>>();
    }

    public static class Node<T> {
        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
    }
    
    public Node<T> getNode() {
    	return this.root;
    }
    
    public T getName() {
    	return root.data;
    }
    
    public void addChildren(Node<T> child) {
    	this.root.children.add(child);
    }
    
    public List<Node<T>> getChildren() {
    	return this.root.children;
    }
    
    public void addParent(Node<T> p) {
    	this.root.parent = p;
    }
    
    public Node<T> getParent() {
    	return this.root.parent;
    }
}
