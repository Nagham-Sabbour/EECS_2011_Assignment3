import java.util.Collection;
import java.util.Iterator;

public class A3AVLTree <E extends Comparable<? super E>> implements Tree<E>{ //consider extending A3BSTree

	static class Node<E extends Comparable<? super E>> {
		E value;
		Node<E> left;
		Node<E> right;
		Node<E> parent;
		int balanceFactor;

		public Node(E value) {
			this.value = value;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.balanceFactor = 0;
		}
	}

	private Node<E> root;
	public A3AVLTree(){
		this.root = null;
	}

	@Override
	public boolean add(E e) {

		Node<E> newNode = new Node<E>(e);
		Node<E> parent = this.root;
		Node<E> temp = null;

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
		//TODO check return value of this method
		return true;
	}

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

	private void rotateRight(Node<E> node) {
		Node<E> left = node.left;
		node.left = left.right;
		if (left.right != null) {
			left.left.parent = node;
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
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
		//TODO check return value
		return true;
	}

	@Override
	public boolean remove(Object o) {

		removeHelper(this.root, (E)o);
		return false;
	}

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
	private E smallestValue(Node<E> root) {
		return root.left == null ? root.value : smallestValue(root.left);
	}

	@Override
	public boolean contains(Object o) {
		return containsHelper(this.root, (E)o);
	}

	private boolean containsHelper(Node<E> node, E e) {
		if (node == null) {
			return false;
		}
		if (node.value == e) {
			return true;
		}
		return e.compareTo(node.value) < 0 ? containsHelper(node.left, e) : containsHelper(node.right, e);
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
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
