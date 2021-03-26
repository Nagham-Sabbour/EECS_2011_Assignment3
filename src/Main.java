import java.sql.SQLOutput;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        //A3AVLTree<Integer> AVL = new A3AVLTree<>();
        Tree<Integer> BST = new A3BSTree<>();
        for (int i = 0; i < 102; i++) {
            BST.add(i);
        }
        BST.remove(100);
        System.out.println(BST.height());
//        Iterator<Integer> iter = BST.iterator();
//        while (iter.hasNext()) {
//            System.out.println(iter.next());
//        }
    }
}