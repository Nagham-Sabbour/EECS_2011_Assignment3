import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        A3AVLTree<Integer> tree = new A3AVLTree<>();

        for (int i = 0; i < 1_000_000; i++) {
            tree.add(i);
        }
        tree.prettyPrint();
        //A3AVLTree<Integer> tree = new A3AVLTree<>();
//        Random rand = new Random();
//        int upperLimit = 10;
//        int adding;
//        for( int i = 0; i < 20; i++) {
//            adding = rand.nextInt(upperLimit);
//            tree.add(adding);
//            System.out.println("ADDED: "+ adding);
//            System.out.println(tree);
//            System.out.println("HEIGHT: "+tree.height());
//            if (tree.contains(i))
//                System.out.println("Contains these:" + i);
//        }
//
//        System.out.println(tree);
//
//        boolean check;
//        int r;
//        for(int i = 0; i < 3; i++) {
//            r = rand.nextInt(upperLimit);
//            check = tree.contains(r);
//
//            System.out.println("Does the tree contain " + Integer.toString(r) + ": "+check);
//        }
//
//        boolean removeIt;
//        for(int i = 0; i < 10; i++) {
//            r = rand.nextInt(upperLimit);
//            removeIt = tree.remove(r);
//            if(removeIt) {
//                System.out.println("REMOVED: "+r);
//                System.out.println(tree);
//            }
//            else
//                System.out.println("DOES NOT EXIST: "+r);
//        }
//
//
//        System.out.println(tree);
//
//        for( int i = 0; i < 20; i++)
//            tree.add(rand.nextInt(upperLimit));
//
//
//        System.out.println(tree);
    }

}
