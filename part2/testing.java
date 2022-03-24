import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class testing {
    public static void main(String[] args) {
        DictBinTree tree = new DictBinTree();
        // Adds number from file to tree
        try {
            Scanner scanner = new Scanner(new File("mixed.txt"));
            while(scanner.hasNextInt()){
                tree.insert(scanner.nextInt());
            }
            System.out.println(tree.search(-117));
        } catch (IOException e) {
           e.printStackTrace();
        }
        ArrayList<Integer> list = tree.orderedTraversal();
        System.out.println(list);
    }
}
