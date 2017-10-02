import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class PM {
    public static final int maxPeople = 10;
    public static Person allPeople[];
    public static int peopleCount;
    
    public PM() {
        allPeople = new Person[maxPeople];
        peopleCount = 0;
    }
    
    public Person findPerson(String name) {
        for (Person p : allPeople) {
            if (p != null && p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }
    
    public void readLine(String s) {
        System.out.print(s + ":    ");
        StringTokenizer st = new StringTokenizer(s);
        String command = st.nextToken();
        Stack<String> arguments = new Stack<>();
        // Used a stack because it allows for variable number of arguments
        while (st.hasMoreTokens()) {
            arguments.push(st.nextToken());
        }
        executeCommand(command, arguments);
    }
    
    public String createUser(Stack<String> args) {
        String errors = "";
        String name = args.pop();
        if (findPerson(name) != null) {
            errors = errors + "Person already exists \n";
        } else if (peopleCount >= maxPeople) {
            errors = errors + "Reached maximum number of people \n";
        } else {
            Person p = new Person(name);
            allPeople[peopleCount] = p;
            peopleCount++;
        }
        return errors;
    }
    
    public String callPair(Stack<String> args, BiConsumer<Person, Person> lambda) {
        String errors = "";
        String name1 = args.pop();
        String name2 = args.pop();
        Person p1 = findPerson(name1);
        Person p2 = findPerson(name2);
        if (p1 == null) {
            errors = errors + "There is no person named " + name1 + "\n";
        } else if (p2 == null) {
            errors = errors + "There is no person named " + name2 + "\n";
        } else {
            lambda.accept(p1, p2);
        }
        return errors;
    }
    
    public String callPerson(Stack<String> args, Consumer<Person> lambda) {
        String errors = "";
        String name = args.pop();
        Person p = findPerson(name);
        if (p != null) {
            lambda.accept(p);
        } else {
            errors = errors + "There is no person named" + p + "\n";
        }
        return errors;
    }
    
    public void executeCommand(String command, Stack<String> args) {
        String errors = "";
        switch(command) {
            case "O":
                errors = errors + createUser(args);
                break;
            case "P":
                errors = errors + callPair(args, Person::setParent);
                break;
            case "M":
                errors = errors + callPair(args, Person::marry);
                break;
            case "D":
                errors = errors + callPerson(args, Person::divorce);
                break;
            case "S":
                errors = errors + callPerson(args, Person::printSpouse);
                break;
            case "C":
                errors = errors + callPerson(args, Person::printChildren);
                break;
            case "X":
                System.exit(1);
        }
        System.out.println(errors);
    }
    
    public BufferedReader openFile() {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader("input.txt"));
        } catch (Exception e) {
            System.out.println("File could not be read");
            System.out.println(e);
        }
        return input;
    }
    
    public void readFile(BufferedReader input) {
        Stream<String> inputStream = input.lines();
        inputStream.forEach(this::readLine);
    }
    
    public static void main(String[] args) {
        PM pm = new PM();
        BufferedReader input = pm.openFile();
        pm.readFile(input);
    }
}
