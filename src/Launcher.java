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
        boolean ok = true;
        List<Command> cmds = List.of(new Quit(), new Fibo(), new Freq());
        while (ok) {
            System.out.println("Enter a command :");
            entry = scan.nextLine();
            for (Command cmd : cmds)
                if (entry.equals(cmd.name()))
                    ok = cmd.run(scan);
        }
    }

    public interface Command {
        String name();
        boolean run(Scanner scan);
    }

    public static class Quit implements Command {
        @Override
        public String name() {
            return "quit";
        }
        @Override
        public boolean run(Scanner scan) {
            System.out.println("Goodbye !");
            return false;
        }
    }

    public static class Fibo implements Command {
        @Override
        public String name() {
            return "fibo";
        }
        @Override
        public boolean run(Scanner scan) throws ArithmeticException {
            long n = Long.parseLong(scan.nextLine());
            // Handle negative number
            if (n < 0) {
                System.err.println("This number is not positive...");
                return false;
            }
            System.out.println(fibo(n));
            return true;
        }
    }

    public static class Freq implements Command {
        @Override
        public String name() {
            return "freq";
        }
        @Override
        public boolean run(Scanner scan) {
            try {
                System.out.println("Enter the file path :");
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
                return true;
            }
            catch (InvalidPathException e) {
                System.err.println("This path is not valid...");
            }
            catch (IOException e) {
                System.err.println("Unreadable file: " + e.getClass() + e.getMessage());
            }
            return false;
        }
    }

    private static long fibo(long n)
    {
        return n == 0 || n == 1 ? n : fibo(n + 1) + fibo(n + 2);
    }
}
