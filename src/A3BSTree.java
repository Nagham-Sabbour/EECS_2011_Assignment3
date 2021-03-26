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
	int size = 0;
	public A3BSTree(){
		this.root = null;
	}

	public Node<E> 	getRoot() {
		return this.root;
	}
	@Override
	public boolean add(E e) {
		Node<E> newNode = new Node<>(e);
		if (this.root == null) {
			this.root = newNode;
			this.size++;
			return true;
		}
		Node<E> parent = null;
		Node<E> curr = root;
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
		if (newNode.value.compareTo(parent.value) < 0) {
			parent.left = newNode;
		}
		else {
			parent.right = newNode;
		}
		this.size++;
		return true;
	}

//	private Node<E> addHelper(Node<E> curr, E e) {
//		if (curr == null) {
//			return new Node<E>(e);
//		}
//		if (e.compareTo(curr.value) < 0) {
//			curr.left = addHelper(curr.left, e);
//		}
//		else if (e.compareTo(curr.value) > 0) {
//			curr.right = addHelper(curr.right, e);
//		}
//		else {
//			return curr;
//		}
//		return curr;
//	}

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
		return removeHelper((E)o);
	}

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

	@Override
	public boolean contains(Object o) {
		try {
			return containsHelper((E)o);
		}catch(Exception e){
			throw new ClassCastException();
		}
	}
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

	@Override
	public Iterator<E> iterator() {
		return new A3BSTIterator(this.root);
	}


	@Override
	public int height() {
		return this.heightHelper(this.root);
	}
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

	@Override
	public int size() {
		return this.size;
	}
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

class CustomQueue<E> {
	int size;
	Node head, tail;

	class Node  {
		E data;
		Node next;

		public Node(E data) {
			this.data = data;
		}
	}

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
	public E dequeue()  {
		E res = head.data;
		head = head.next;
		size--;
		if (this.size == 0) {
			tail = null;
		}
		return res;
	}
	public E first() {
		return head.data;
	}

}
