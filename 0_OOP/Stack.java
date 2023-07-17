import java.util.List;

public class Stack extends Base{
    public Stack() {
        super();
    }

    public Stack(List<Integer> values) {
        super(values);
    }

    public void push(int value) {
        values.add(value);
    }

    public int pop() {
        int value = values.remove(values.size() - 1);
        return value;
    }

    @Override
    public int peek() {
        int value = values.get(values.size() - 1);
        return value;
    }

    @Override
    public void PrettyPrint() {
        System.out.println("Stack:");
        for (int i = values.size() - 1; i >= 0; i--) {
            System.out.println(values.get(i));
        }
    }
}
