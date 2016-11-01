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
       
        public Node() {
        	children = new ArrayList<Node<T>>();
        }
       
        public Node(T d) {
           this.data = d;
           children = new ArrayList<Node<T>>();
        }
       
        public T getName() {
               return data;
        }
       
        public void addChildren(Node<T> child) {
               children.add(child);
        }
       
        public List<Node<T>> getChildren() {
               return children;
        }
       
        public void addParent(Node<T> p) {
               parent = p;
        }
       
        public Node<T> getParent() {
               return parent;
        }
        public boolean hasChildren() {
            int countChildren = children.size();
            if(countChildren > 0){
            	return true;
            }
            else{
            	return false;
            }
        }
    }
   
    public Node<T> getNode() {
               return this.root;
    }
   
    public void addChildren(Node<T> child) {
               this.root.children.add(child);
    }
   
}