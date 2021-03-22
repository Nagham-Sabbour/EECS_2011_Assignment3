import java.sql.SQLOutput;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        A3AVLTree<Integer> tree = new A3AVLTree<>();

        tree.add(10);
        tree.prettyPrint();
        System.out.println();
        tree.add(15);
        tree.prettyPrint();
        System.out.println();
        tree.add(20);
        tree.prettyPrint();
        System.out.println();
        tree.add(30);
        tree.prettyPrint();
        System.out.println();
        tree.add(35);
        tree.prettyPrint();
        System.out.println();
        tree.add(40);
        tree.prettyPrint();
        System.out.println();
        tree.add(45);
        tree.prettyPrint();
        tree.remove(30);
        tree.prettyPrint();
        tree.remove(45);
        tree.prettyPrint();
        tree.remove(40);
        tree.prettyPrint();
        tree.add(40);
        tree.prettyPrint();
        System.out.println();
        System.out.println(tree.size());

    }
}