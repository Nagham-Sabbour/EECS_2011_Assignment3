import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Patrick Baciu - ; Nagham Sabbour - 217 354 416; Hashir Jamil - 217 452 954
 * Binary Search Tree Implementation EECS 2011z Assignment 3, Winter 2021
 * @param <E>, a comparable generic element to be included in a node-based-tree 
 */
public class A3BSTree <E extends Comparable<? super E>> implements Tree<E>{
	
	/**
	 * Static, nested node class for Binary Search Tree
	 * @param <E>, a comparable generic element to be included in a tree
	 */
	static class Node<E extends Comparable<? super E>> {
		E value;
		Node<E> left;
		Node<E> right;

		/**
		 * 
		 * @param value, a generic value for the node being instantiated
		 */
		Node(E value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}

	/**
	 * Root node of the binary search tree
	 */
	Node<E> root;
	/**
	 * Number of elements in the binary search tree
	 */
	int size = 0;
	/**
	 * Constructor that creates a
	 */
	public A3BSTree(){
		this.root = null;
	}

	/**
	 * @return Node<E>, the root of the instantiated tree
	 */
	public Node<E> 	getRoot() {
		return this.root;
	}
	/**
	 * Adds the specified element to this tree (duplicates are not allowed)
	 * @param e, the value to add
	 * @return true if the element was added (the tree was modified) 
	 */
	@Override
	public boolean add(E e) {
		
		//Instantiate a node that has the value e
		Node<E> newNode = new Node<>(e);
		
		//This block handles the case where original tree being added to was null
		//Set the new node as the root for this tree and increment its size
		if (this.root == null) {
			this.root = newNode;
			this.size++;
			return true;
		}
		
		//Instantiate a parent node as null and a current node as the root
	    //The current node will change as this will be the node the subsequent traversal while loop is at per iteration
		Node<E> parent = null;
		Node<E> curr = root;
		
		/*
		 * While the current node is not null the tree is traversed 
		 * The value of the node being added is checked against the left and right children nodes of the current node
		 * If the value of the current node is larger than the value that is being added,
		 *  then the traversal is to the left child of the current node
		 * If the value of current node is smaller than the value that is being added,
		 *  then the traversal is to the right child of the current node
		 * If the value of the current node is equal to the value that is being added, then return false
		 * The traversal ends when the current node is nullified (reaches last node in the tree)
		 */
		while (curr != null) {
			parent = curr;
			if (newNode.value.compareTo(curr.value) < 0) {
				curr = curr.left;
			}
			else if (newNode.value.compareTo(curr.value) > 0) {
				curr = curr.right;
			}
			else {
				curr.value = newNode.value;
				return false;
			}
		}
		
		/*
		 * Once the traversal is finished the location of insertion is determined
		 * If the value of the element being added is lesser than the lowest level parent in the tree,
		 *  then the new value is added to the left child of this parent
		 * If the value of the element being added is greater than the lowest level parent in the tree,
		 *  then the new value is added to the right child of this parent 
		 */
		if (newNode.value.compareTo(parent.value) < 0) {
			parent.left = newNode;
		}
		else {
			parent.right = newNode;
		}
		//Increase size by one and return true if the value was added
		this.size++;
		return true;
	}

	/**
	 * Adds all of the elements in the specified collection to this tree.
	 * (duplicates are not allowed)
	 * Calls the add(E e) method for every element in the collection that is not already present in the BST
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
	 * Uses helper method removeHelper(E e)
	 * @param o object to remove
	 * @return true if this tree contained the element 
	 */
	@Override
	public boolean remove(Object o) {
		return removeHelper((E)o);
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	private boolean removeHelper(E e) {
		Node<E> parent = null;
		Node<E> curr = this.root;
		boolean isLeft = false;
		while (curr != null && curr.value != e) {
			parent = curr;
			if (e.compareTo(curr.value) < 0) {
				curr = curr.left;
				isLeft = true;
			}
			else if (e.compareTo(curr.value) > 0){
				curr = curr.right;
				isLeft = false;
			}
		}
		if (curr == null) {
			return false;
		}
		if (curr.left == null && curr.right == null) {
			if (curr == this.root) {
				this.root = null;
			}
			else if(isLeft) {
				parent.left = null;
			}
			else {
				parent.right = null;
			}
		}
		else if (curr.left == null) {
			if (curr == this.root) {
				this.root = curr.right;
			}
			else if (isLeft) {
				parent.left = curr.right;
			}
			else {
				parent.right = curr.right;
			}

		}
		else if (curr.right == null){
			if (curr == this.root) {
				this.root = curr.left;
			}
			else if (isLeft) {
				parent.left = curr.left;
			}
			else {
				parent.right = curr.left;
			}
		}
		else {
			Node<E> succ = findSucc(curr);
			if (curr == root) {
				root = succ;
			}
			else if (isLeft) {
				parent.left = succ;
			}
			else {
				parent.right = succ;
			}
			succ.left = curr.left;
		}
		this.size--;
		return true;
	}
	/**
	 * Helper method for removeHelper(E e)
	 * @param node with values of type E
	 * 
	 * @return
	 */
	private Node<E> findSucc(Node<E> node) {
		Node<E> succ = node;
		Node<E> parent = node;
		Node<E> curr = node.right;

		while (curr != null) {
			parent = succ;
			succ = curr;
			curr = curr.left;
		}
		if (succ != node.right) {
			parent.left = succ.right;
			succ.right = node.right;
		}
		return succ;
	}

	/**
	 * Returns true if this tree contains the specified element. 
	 * Uses private helper method containsHelper(E e)
	 * @param o, an object that is checked for its presence in the BST
	 * @return
	 * @exception ClassCastException
	 */
	@Override
	public boolean contains(Object o) {
		try {
			return containsHelper((E)o);
		}catch(Exception e){
			throw new ClassCastException();
		}
	}
	/**
	 * Helper method for contains(Object o)
	 * Traverses the tree by checking whether the value of the parameter e is smaller or larger than the current node
	 * The traversal starts at the root 
	 * If the root is null the traversal does not get executed and the method returns false
	 * If value of e is smaller the value of the current node the traversal continues towards the left side
	 * If value of e is larger the value of the current node the traversal continues towards the right side
	 * Traversal proceeds until element is found or the whole tree has been searched
	 * @param e, a node that is checked for its presence in the BST
	 * @return true if the element is found and false if the element is not present
	 */
	private boolean containsHelper(E e) {
		Node<E> curr = this.root;
		while (curr != null) {
			if (e.compareTo(curr.value) < 0) {
				curr = curr.left;
			}
			else if (e.compareTo(curr.value) > 0) {
				curr = curr.right;
			}
			else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * 
	 */
	class A3BSTIterator implements Iterator<E>{
		E[] nodeValues;
		int index;

		public A3BSTIterator (Node<E> node) {
			this.nodeValues = (E[]) new Comparable[size()];
			this.index = -1;
			inOrderTrav(node);//remember to call root in iterator
		}

		private void inOrderTrav(Node<E> node) {
			Node<E> curr;
			Node<E> p;
			if (node == null) {
				return;
			}
			curr = node;
			int ctr = 0;
			while (curr != null) {

				if (curr.left == null) {
					nodeValues[ctr] = curr.value;
					ctr++;
					curr = curr.right;
				}
				else {

					p = curr.left;
					while (p.right != null && p.right != curr) {
						p = p.right;
					}

					if (p.right == null) {
						p.right = curr;
						curr = curr.left;

					}

					else {
						p.right = null;
						nodeValues[ctr] = curr.value;
						ctr++;
						curr = curr.right;
					}
				}

			}
		}

		@Override
		public boolean hasNext() {
			return this.index + 1 < this.nodeValues.length;
		}

		@Override
		public E next() {
			this.index++; 
			return this.nodeValues[index];
		}

	}

	/** 
	 * Returns an iterator over the elements of this tree in ascending order
	 * @return the iterator described above
	 */
	@Override
	public Iterator<E> iterator() {
		return new A3BSTIterator(this.root);
	}

	/**
	 * Uses private helper method 
	 * @return the height of the tree as an integer between -1 and positive infinity
	 */
	@Override
	public int height() {
		return this.heightHelper(this.root);
	}
	/**
	 * @param node
	 * @return
	 */
	private int heightHelper(Node<E> node) {
		if (node == null) {
			return -1;
		}
		CustomQueue<Node> queue = new CustomQueue<>();
		queue.enqueue(node);
		int height = -1;
		while (true) {
			int count = queue.size;
			if (count == 0) {
				return height;
			}
			height++;
			while (count > 0) {
				try {
					Node newNode = queue.first();
					queue.dequeue();
					if (newNode.left != null) {
						queue.enqueue(newNode.left);
					}
					if (newNode.right != null) {
						queue.enqueue(newNode.right);
					}
					count--;
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	/** 
	 * Returns the number of elements in the tree. 
	 * @return number of elements
	 */
	@Override
	public int size() {
		return this.size;
	}
	/**
	 * 
	 */
	ArrayList<E> array;
	/**
	 *
	 */
	public String toString() {
		array = new ArrayList<E>();
		toStringHelper(this.root); // IN ORDER TRAVERSAL
		return array.toString();
	}
	/**
	 * @param node
	 */
	public void toStringHelper(Node<E> node) {
		if(node != null) {
			toStringHelper(node.left);
			array.add(node.value);
			toStringHelper(node.right);
		}
	}
}

/**
 * Node based queue implementation to help calculate height
 * @param <E>, generic type for the elements of the queue
 */
class CustomQueue<E> {
	/**
	 * Size of the queue
	 */
	int size;
	/**
	 * The first and last nodes of the queue
	 */
	Node head, tail;

	/**
	 * Nested node class for queue implementation
	 */
	class Node  {
		E data;
		Node next;

		/**
		 * Node constructor
		 * @param data to be stored in the node
		 */
		public Node(E data) {
			this.data = data;
		}
	}

	/**
	 * Adds an element to the back of the queue
	 * @param data to be stored in the node of the queue 
	 */
	public void enqueue(E data) {
		Node newNode = new Node(data);
		if (this.size == 0) {
			head = newNode;
		}
		else {
			tail.next = newNode;
		}
		tail = newNode;
		size++;
	}
	/**
	 * Removes an element from the front of the queue
	 * @return E, the data from the node that was removed from the queue
	 */
	public E dequeue()  {
		E res = head.data;
		head = head.next;
		size--;
		if (this.size == 0) {
			tail = null;
		}
		return res;
	}
	/**
	 * @return E, the data stored in the first node (node that was added before all the other nodes) of the queue
	 */
	public E first() {
		return head.data;
	}

}
