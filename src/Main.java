import java.sql.SQLOutput;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Tree<Integer> tree = new A3BSTree<>();
        tree.add(30);
        tree.add(20);
        tree.add(40);
        tree.add(10);
        tree.add(35);
        tree.add(15);
        tree.add(45);
        tree.remove(30);
//        tree.remove(15);
//        tree.remove(50);
        System.out.println(tree.size());
        System.out.println(tree.toString());
    }
}