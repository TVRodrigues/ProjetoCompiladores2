package calc.nodes.environment;
import java.util.HashMap;

public class Env {
    private HashMap<String, Object> table;

    public Env() {
        table = new HashMap<>();
    }

    public void put(String id, Object value) {
        table.put(id, value);
    }

    public Object get(String id) {
        return table.get(id);
    }
    
    @Override
    public String toString() {
        return table.toString();
    }
}