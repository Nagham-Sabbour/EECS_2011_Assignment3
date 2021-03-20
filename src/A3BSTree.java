import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class A3BSTree <E extends Comparable<? super E>> implements Tree<E>{

	static class Node<E extends Comparable<? super E>> {
		E value;
		Node<E> left;
		Node<E> right;

		Node(E value) {
			this.value = value;
			this.left = null;
			this.right = null;
		}
	}

	Node<E> root;
	public A3BSTree(){
		this.root = null;
	}

	@Override
	public boolean add(E e) {
		this.root = addHelper(this.root, e);
		return false;
	}

	private Node<E> addHelper(Node<E> curr, E e) {
		if (curr == null) {
			return new Node<E>(e);
		}
		if (e.compareTo(curr.value) < 0) {
			curr.left = addHelper(curr.left, e);
		}
		else if (e.compareTo(curr.value) > 0) {
			curr.right = addHelper(curr.right, e);
		}
		else {
			return curr;
		}
		return curr;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		this.root = removeHelper(this.root, (E)o);
		return false;
	}

	private Node<E> removeHelper(Node<E> curr, E e) {
		if (curr == null) {
			return null;
		}
		if (e == curr.value) {
			if (curr.left == null && curr.right == null) {
				return null;
			}
			if (curr.right == null) {
				return curr.left;
			}
			if (curr.left == null) {
				return curr.right;
			}
			E smallest = smallestValue(curr.right);
			curr.value = smallest;
			curr.right = removeHelper(curr.right, smallest);
			return curr;
		}
		if (e.compareTo(curr.value) < 0) {
			curr.left = removeHelper(curr.left, e);
			return curr;
		}
		curr.right = removeHelper(curr.right, e);
		return curr;
	}

	private E smallestValue(Node<E> root) {
		return root.left == null ? root.value : smallestValue(root.left);
	}

	@Override
	public boolean contains(Object o) {
		return containsHelper(this.root, (E)o);
	}
	private boolean containsHelper(Node<E> curr, E e) {
		if (curr == null) {
			return false;
		}
		if (e == curr.value) {
			return true;
		}
		return e.compareTo(curr.value) < 0 ? containsHelper(curr.left, e) : containsHelper(curr.right, e);

	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public int height() {
		return heightHelper(this.root);
	}
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

	@Override
	public int size() {
		return sizeHelper(this.root);
	}
	private int sizeHelper(Node<E> root) {
		if (root == null) {
			return 0;
		}
		else {
			return sizeHelper(root.left) + sizeHelper(root.right) + 1;
		}
	}

	//TODO delete this
	ArrayList<E> array;
	public String toString() {
		array = new ArrayList<E>();
		toStringHelper(this.root); // IN ORDER TRAVERSAL
		return array.toString();
	}
	public void toStringHelper(Node<E> node) {
		if(node != null) {
			toStringHelper(node.left);
			array.add(node.value);
			toStringHelper(node.right);
		}
	}
}
