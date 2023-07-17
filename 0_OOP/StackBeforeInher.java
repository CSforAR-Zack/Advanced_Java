import java.util.List;
import java.util.ArrayList;

public class StackBeforeInher {
    List<Integer> values = null;

    public StackBeforeInher() {
        values = new ArrayList<Integer>();
    }

    public void push(int value) {
        values.add(value);
    }

    public int pop() {
        int value = values.remove(values.size() - 1);
        return value;
    }

    public int peek() {
        int value = values.get(values.size() - 1);
        return value;
    }

    public boolean isEmpty() {
        return values.size() == 0;
    }

    public int size() {
        return values.size();
    }

    public void clear() {
        values.clear();
    }

    public void PrettyPrint() {
        System.out.println("Stack:");
        for (int i = values.size() - 1; i >= 0; i--) {
            System.out.println(values.get(i));
        }
    }
}
