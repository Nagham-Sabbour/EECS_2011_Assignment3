import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author Patrick Baciu - ; Nagham Sabbour - 217 354 416; Hashir Jamil - 217 452 954
 * AVL Tree Implementation EECS 2011z Assignment 3, Winter 2021
 * @param <E>, a comparable generic element to be included in a node-based-tree
 */
public class A3AVLTree <E extends Comparable<? super E>> implements Tree<E>{ //consider extending A3BSTree
	
	/**
	 * Static, nested node class for Binary Search Tree
	 * @param <E>, a comparable generic element that is the value of a node
	 */
	static class Node<E extends Comparable<? super E>> {
		E value;
		Node<E> left;
		Node<E> right;
		Node<E> parent;
		int balanceFactor;

		/**
		 * Node constructor that sets the value of the node as the argument,
		 *  sets the parent, left and right children of a node as null and balance factor as 0
		 * @param value, the value of the node being instantiated
		 */
		public Node(E value) {
			this.value = value;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.balanceFactor = 0;
		}
	}

	/**
	 * The type of nodes that will be present in the AVL tree
	 */
	private Node<E> root;
	
	/**
	 * AVL tree's constructor
	 * Sets the root as null by default (i.e. instantiates an empty tree)
	 */
	public A3AVLTree(){
		this.root = null;
	}

	/**
	 * Adds the specified element to this tree (duplicates are not allowed)
	 * @param e element to add
	 * @return true if the element was added (the tree was modified) 
	 */
	@Override
	public boolean add(E e) {
		
		//Instantiate three nodes:
		Node<E> newNode = new Node<E>(e); //First node is one with the value to be added
		Node<E> parent = this.root; //Second node is the root
		Node<E> temp = null; //Third node is a temporary node that is  throughout the method and instantiated null

		/*
		 * Traverse through the tree down the path where the value of nodes is greater than the value e
		 * 
		 */
		while (parent != null) {
			temp = parent;
			if (newNode.value.compareTo(parent.value) < 0) {
				parent = parent.left;
			}
			else {
				parent = parent.right;
			}
		}

		
		newNode.parent = temp;
		if (temp == null) {
			this.root = newNode;
		}
		else if (newNode.value.compareTo(temp.value) < 0) {
			temp.left = newNode;
		}
		else {
			temp.right = newNode;
		}
		updateBalanceFactorOrRebalance(newNode);
		return true;
	}

	/**
	 * 
	 * @param node
	 */
	private void updateBalanceFactorOrRebalance(Node<E> node) {
		if (node.balanceFactor < -1 || node.balanceFactor > 1) {
			rebalanceNode(node);
			return;
		}
		if (node.parent != null) {
			if (node == node.parent.left) {
				node.parent.balanceFactor --;
			}
			if (node == node.parent.right) {
				node.parent.balanceFactor ++;
			}
			if (node.parent.balanceFactor != 0) {
				//recurse up the tree
				updateBalanceFactorOrRebalance(node.parent);
			}

		}
	}
	
	/**
	 * 
	 * @param node
	 */
	private void rebalanceNode(Node<E> node) {
		//if node balance factor positive, rotate based on right child
		if (node.balanceFactor > 0) {
			//if right child balance factor negative, do right rotation on right child followed by left rotation on node
			if (node.right.balanceFactor < 0) {
				rotateRight(node.right);
				rotateLeft(node);
			}
			//if right child balance factor positive, do left rotation on node
			else {
				rotateLeft(node);
			}
		}
		//if node balance factor negative, rotate based on left child
		else if (node.balanceFactor < 0) {
			//if left child balance factor positive, do left rotation on left child followed by right rotation on node
			if (node.left.balanceFactor > 0) {
				rotateLeft(node.left);
				rotateRight(node);
			}
			//if left child balance factor negative, do right rotation on node
			else {
				rotateRight(node);
			}
		}
	}

	/**
	 * 
	 * @param node
	 */
	private void rotateLeft(Node<E> node) {

		Node<E> right = node.right;
		node.right = right.left;
		if (right.left != null) {
			right.left.parent = node;
		}
		right.parent = node.parent;
		if (node.parent == null) {
			this.root = right;
		}
		else if (node == node.parent.left) {
			node.parent.left = right;
		}
		else {
			node.parent.right = right;
		}
		right.left = node;
		node.parent = right;

		node.balanceFactor = node.balanceFactor - Math.max(0, right.balanceFactor) - 1;
		right.balanceFactor = right.balanceFactor + Math.min(0, node.balanceFactor) - 1;
	}

	/**
	 * 
	 * @param node
	 */
	private void rotateRight(Node<E> node) {
		Node<E> left = node.left;
		node.left = left.right;
		if (left.right != null) {
			left.right.parent = node;
		}
		left.parent = node.parent;
		if (node.parent == null) {
			this.root = left;
		}
		else if (node == node.parent.right) {
			node.parent.right = left;
		}
		else {
			node.parent.left = left;
		}
		left.right = node;
		node.parent = left;

		node.balanceFactor = node.balanceFactor - Math.min(0, left.balanceFactor) + 1;
		left.balanceFactor = left.balanceFactor + Math.max(0, node.balanceFactor) + 1;
	}
	
	/**
	 * Adds all of the elements in the specified collection to this tree.
	 * (duplicates are not allowed)
	 * @param c Collection containing the elements
	 * @return true if the tree was changed as a result of the call
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean flag = false;
		for (E e : c) {
			flag = flag || add(e);
		}
		return flag;
	}

	/**
	 * Removes the specified element from this tree, if it is present. 
	 * @param o object to remove
	 * @return true if this tree contained the element 
	 */
	@Override
	public boolean remove(Object o) {
		try {
			if (contains((E)o)) {
			removeHelper(this.root, (E)o);
			return true;
			}
			return false;
		}catch(Exception e){
			throw new ClassCastException();
		}
	}

	/**
	 * 
	 * @param node
	 * @param e
	 * @return
	 */
	private Node<E> removeHelper(Node<E> node, E e) {
		if (node == null) {
			return null;
		}
		if (e == node.value) {
			if (node.left == null && node.right == null) {
				return null;
			}
			if (node.right == null) {
				return node.left;
			}
			if (node.left == null) {
				return node.right;
			}
			E smallest = smallestValue(node.right);
			node.value = smallest;
			node.right = removeHelper(node.right, smallest);
			return node;
		}
		if (e.compareTo(node.value) < 0) {
			node.left = removeHelper(node.left, e);
			return node;
		}
		node.right = removeHelper(node.right, e);
		return node;

	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	private E smallestValue(Node<E> root) {
		return root.left == null ? root.value : smallestValue(root.left);
	}

	/**
	 * Returns true if this tree contains the specified element. 
	 * @param o
	 * @return
	 */
	@Override
	public boolean contains(Object o) {
		try {
			return containsHelper(this.root, (E)o);
		}catch(Exception e){
			throw new ClassCastException();
		}
	}
	
	/**
	 * 
	 * @param node
	 * @param e
	 * @return
	 */
	private boolean containsHelper(Node<E> node, E e) {
		if (node == null) {
			return false;
		}
		if (node.value == e) {
			return true;
		}
		return e.compareTo(node.value) < 0 ? containsHelper(node.left, e) : containsHelper(node.right, e);
	}

	/**
	 * 
	 *
	 */
	class A3AVLTreeIterator implements Iterator<E>{
		//TODO use array instead of array list
		E[] nodeValues;
		int index;
		
		/**
		 * 
		 * @param node
		 */
		public A3AVLTreeIterator (Node<E> node) {
			this.nodeValues = (E[]) new Comparable[size()];
			this.index = 0;
			inOrderTrav(node);//remember to call root in iterator
		}
		
		/**
		 * Recursive in order traversal of AVL tree
		 * @param node
		 */
		private void inOrderTrav(Node<E> node) {
			if (node == null) {
				return;
			}
			
			inOrderTrav(node.left);
			this.nodeValues[index] = (node.value);
			inOrderTrav(node.right);
			
		}
		
		/**
		 * 
		 */
		@Override
		public boolean hasNext() {
			return this.index + 1 < this.nodeValues.length;
		}

		/**
		 * 
		 */
		@Override
		public E next() {
			this.index++;
			return this.nodeValues[index - 1];

		}
		
	}
	
	/** Returns an iterator over the elements of this tree in ascending order
	 * @return the iterator described above
	 */
	@Override
	public Iterator<E> iterator() {
		return new A3AVLTreeIterator(this.root);
	}

	/** Returns the height of the tree. 
	 * For an empty tree should return -1. 
	 * For a tree of one node should return 0
	 * @return height of the tree
	 */
	@Override
	public int height() {
		return heightHelper(this.root);
	}

	/**
	 * 
	 * @param root
	 * @return
	 */
	private int heightHelper(Node<E> root) {
		if (root == null) {
			return -1;
		}
		else {
			int leftHeight = heightHelper(root.left);
			int rightHeight = heightHelper(root.right);
			return Math.max(leftHeight, rightHeight) + 1;
		}
	}

	/** Returns the number of elements in the tree. 
	 * @return number of elements
	 */
	@Override
	public int size() {
		return sizeHelper(this.root);
	}
	
	/**
	 * 
	 * @param root
	 * @return
	 */
	private int sizeHelper(Node<E> root) {
		if (root == null) {
			return 0;
		}
		else {
			return sizeHelper(root.left) + sizeHelper(root.right) + 1;
		}
	}

	public void prettyPrint() {
		printHelper(this.root, "", true);
	}
	private void printHelper(Node currPtr, String indent, boolean last) {
		// print the tree structure on the screen
		if (currPtr != null) {
			System.out.print(indent);
			if (last) {
				System.out.print("R----");
				indent += "     ";
			} else {
				System.out.print("L----");
				indent += "|    ";
			}

			System.out.println(currPtr.value + "(BF = " + currPtr.balanceFactor + ")");

			printHelper(currPtr.left, indent, false);
			printHelper(currPtr.right, indent, true);
		}
	}
}
