import java.util.List;
import java.util.ArrayList;

/**
 * This is a doc comment for a Java class.
 * You can have multiple lines of comments.
 * You can use HTML tags, such as <b>bold</b>.
 */

public class Main {
    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.PrettyPrint();
        
        Queue queue = new Queue(GenerateValues());
        queue.add(6);
        queue.add(7);
        queue.PrettyPrint();
    }

    private static List<Integer> GenerateValues(){
        List<Integer> values = new ArrayList<Integer>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);
        return values;
    }
}
