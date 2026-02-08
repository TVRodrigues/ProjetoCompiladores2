package lang.ast.environment;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import lang.ast.types.Decl;

public class Env {

    private Stack<HashMap<String, Object>> scopes = new Stack<>();
    private Map<String, List<Decl>> dataTypes = new HashMap<>();
    private Map<String, Object> map;

    public Env() {
        scopes.push(new HashMap<>()); // Escopo global
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void storeDataType(String typeName, List<Decl> fields) {
        dataTypes.put(typeName, fields);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void exitScope() {
        if (scopes.size() > 1) { // Garante que o escopo global não seja removido
            scopes.pop();
        } else {
            throw new RuntimeException("Cannot exit global scope.");
        }
    }

    public void store(String vname, Object value) {
        scopes.peek().put(vname, value);
    }

    public Object read(String vname) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Object value = scopes.get(i).get(vname);
            if (value != null) {
                return value;
            }
        }
        throw new RuntimeException("Unknown variable " + vname);
    }

    public void dumpTable() {
        String title = "Variável";
        System.out.println("Scope Dump:");
        for (int i = 0; i < scopes.size(); i++) {
            System.out.println("Scope Level " + i);
            System.out.println(repeatStr(6 - title.length() / 2, " ") + title
                    + repeatStr(6 - title.length() / 2, " ") + "| Valor");
            System.out.println(repeatStr(22, "-"));
            for (Map.Entry<String, Object> e : scopes.get(i).entrySet()) {
                String value = (e.getValue() == null) ? "null" : e.getValue().toString();
                System.out.println(
                        e.getKey() + repeatStr(12 - e.getKey().length(), " ") + "| " + value);
                System.out.println(repeatStr(22, "-"));
            }
        }
    }

    private String repeatStr(int n, String c) {
        return c.repeat(Math.max(0, n));
    }
}
