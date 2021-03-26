import java.util.Iterator;

public class TreeSort{
	/** Sorts an array using TreeSort with a balanced BST implementation 
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort( E[] a){
		Tree <E> tree = new A3AVLTree<>();
		sort(tree, a);
	}
	
	/**
	 * Sorts an array using TreeSort with a specified BST
	 * @param tree tree to use
	 * @param a an array to sort
	 */
	public static <E extends Comparable <? super E>> void sort(Tree <E> tree, E[] a){
		int index;
		for(index = 0 ; index < a.length; index++ ) {
			tree.add(a[index]);
		}
		
		index = 0;
		for (Iterator<E> it = tree.iterator(); it.hasNext(); ) {
			E e = it.next();
			a[index] = e;
			index++;
		}
	}
}
