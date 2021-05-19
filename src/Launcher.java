import java.util.Scanner;

public class Launcher {
    public static void main(String[] args)
    {
        System.out.println("Welcome !");
        Scanner scan = new Scanner(System.in);
        while (!scan.nextLine().equals("quit"))
            System.out.println("Unknown command");
    }
}
