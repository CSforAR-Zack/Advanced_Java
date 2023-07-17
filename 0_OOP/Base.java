import java.util.ArrayList;
import java.util.List;

abstract public class Base {
    protected List<Integer> values = null;
    
    public Base() {
        this.values = new ArrayList<Integer>();
    }

    public Base(List<Integer> values) {
        this.values = values;
    }

    public abstract int peek();

    public abstract void PrettyPrint();

    public boolean isEmpty() {
        return this.values.size() == 0;
    }

    public int size() {
        return this.values.size();
    }

    public void clear() {
        values.clear();
    }
}
