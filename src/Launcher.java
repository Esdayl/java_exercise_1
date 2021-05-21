import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.lang.Long;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Launcher {
    public static void main(String[] args)
    {
        System.out.println("Welcome !");
        Scanner scan = new Scanner(System.in);
        String entry;
        while (!(entry = scan.nextLine()).equals("quit")) {
            switch(entry)
            {
                case "fibo":
                {
                    System.out.println("Enter a positive number :");
                    long n = Long.parseLong(scan.nextLine());
                    // Handle negative number
                    if (n < 0) {
                        System.err.println("This number is not positive...");
                        continue;
                    }
                    System.out.println(fibo(n));
                }
                case "freq":
                {
                    System.out.println("Enter a path");
                    try {
                        Path path = Paths.get(scan.nextLine());
                        String text = Files.readString(path);
                        String[] words = text.split(" ");

                        Map<String, Long> countsByWord = Arrays.stream(words)
                                .filter(s -> !s.isBlank())
                                .map(s->s.toLowerCase())
                                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

                        Function<Map.Entry<String, Long>, Long> extractor = e->e.getValue();
                        String w = countsByWord.entrySet().stream()
                                .sorted(Comparator.comparing(extractor).reversed())
                                .limit(3).map(e->e.getKey())
                                .collect(Collectors.joining(" "));
                        System.out.println(w);
                    }
                    catch (InvalidPathException e) {
                        System.err.println("This path is not valid...");
                    }
                    catch (IOException e) {
                        System.err.println("Unreadable file: " + e.getClass() + e.getMessage());
                    }
                }
                default:
                    System.out.println("Unknown command");
            }
        }
    }
    static long fibo(long n)
    {
        if (n < 2)
            return n;
        long u0 = 0;
        long u1 = 1;
        long tmp;
        for (long i = 1; i < n; i++)
        {
            tmp = u0;
            u0 = u1;
            u1 += tmp;
        }
        return u1;
    }
}
