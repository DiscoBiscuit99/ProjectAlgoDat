import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        DictBinTree dict = new DictBinTree();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) {
            dict.insert(scanner.nextInt());
        }

        for (int n : dict.orderedTraversal())
            System.out.println(n);
    }
}

