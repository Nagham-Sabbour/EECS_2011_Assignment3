import java.util.Random;

public class Tester {
	
    public static A3BSTree<Integer> generateBSTree(int size) {
        //create a list
    	A3BSTree<Integer> tree = new A3BSTree<>();
        //total time
        double total = 0;
        //add a given number of items to the list
        for (int i = 0; i < size; i++) {
            //write down the start time
            double start = System.nanoTime();
            //add an item to a random place in the list
            tree.add(new Random().nextInt(tree.size() + 1));
            //add the time taken to add the item to the total time
            total += (System.nanoTime() - start);
        }
        //displaying the average time of element insertion
        System.out.println("List size is " + tree.size() + " average add time is " + total / size);
        //return the created list
        return tree;
    }
    
    public static A3AVLTree<Integer> generateAVLTree(int size) {
    	
    	A3AVLTree<Integer> tree = new A3AVLTree<>();
        double total = 0;
        
        for (int i = 0; i < size; i++) {
            double start = System.nanoTime();
            tree.add(new Random().nextInt(tree.size() + 1));
            //add the time taken to add the item to the total time
            total += (System.nanoTime() - start);
        }
        System.out.println("List size is " + tree.size() + " average add time is " + total / size);
        
        return tree;
    }
}
    

