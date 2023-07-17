import java.util.List;


public class Queue extends Base{

    public Queue(List<Integer> values) {
        super(values);
    }
    
    public void add(int value) {
        values.add(value);
    }

    public int remove() {
        int value = values.remove(0);
        return value;
    }

    @Override
    public int peek() {
        int value = values.get(0);
        return value;
    }

    @Override
    public void PrettyPrint() {
        System.out.println("Queue:");
        for (int i = 0; i < values.size(); i++) {
            System.out.print(values.get(i) + " ");
        }
    }
}
