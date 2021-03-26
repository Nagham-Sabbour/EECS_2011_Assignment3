import java.util.Arrays;
import java.util.Collections;

public class SortTester {

	public static void main(String[] args) {
		Tree <Integer> avl = new A3AVLTree<>();
		Tree <Integer> bst = new A3BSTree<>();

		long avlTime;
		long bstTime;
		System.out.println("|\tAVL Time\t|\tBST Time\t|");
		for (int i=10; i< 100000000; i*=2) {
			Integer a[] = new Integer[i];
			for (int j = 0; j < a.length; j++) a[j] = j;
			Collections.shuffle(Arrays.asList(a));
			Integer[] b = a.clone();

			long start;
			start = System.currentTimeMillis();
			TreeSort.sort(avl, a);
			avlTime = System.currentTimeMillis() - start;
			start = System.currentTimeMillis();
			TreeSort.sort(bst, b);
			bstTime = System.currentTimeMillis() - start;
			System.out.println("|\t"+avlTime+"\t|\t"+bstTime+"\t|");
		}
	}
}