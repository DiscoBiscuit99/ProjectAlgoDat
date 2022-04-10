import java.util.Scanner;

public class Treesort {

    public static void treesort() {

        DictBinTree dict = new DictBinTree();
        Scanner scanner = new Scanner(System.in);

        //Reads input and inserts in the DictBintree
        while (scanner.hasNextInt()) {
            dict.insert(scanner.nextInt());
        }

        //Prints the resulting DictBinTree in-order
        for (int n : dict.orderedTraversal())
            System.out.println(n);
        
        scanner.close();
    }
    
}
