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
		if (this.contains(e)) {
			return false;
		}
		this.root = addHelper(this.root, e);
		return true;
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
		boolean flag = false;
		for (E e : c) {
			flag = flag || add(e);
		}
		return flag;
	}

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
		try {
			return containsHelper(this.root, (E)o);
		}catch(Exception e){
			throw new ClassCastException();
		}
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

	class A3BSTIterator implements Iterator<E>{
		ArrayList<E> nodeValues;
		int index;

		public A3BSTIterator (Node<E> node) {
			this.nodeValues = new ArrayList<>();
			this.index = -1;
			inOrderTrav(node);//remember to call root in iterator
		}

		//recursive 
		private void inOrderTrav(Node<E> node) {
			if (node == null) {
				return;
			}

			inOrderTrav(node.left);
			this.nodeValues.add(node.value);
			inOrderTrav(node.right);

		}

		@Override
		public boolean hasNext() {
			return this.index + 1 < this.nodeValues.size();
		}

		@Override
		public E next() {
			this.index++; 
			return this.nodeValues.get(index);
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new A3BSTIterator(this.root);
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
