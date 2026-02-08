package lang.ast.visitors;

// import lang.ast.decl.*;
import lang.ast.expr.*;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lang.ast.environment.*;
import lang.ast.command.*;
import lang.ast.types.*;
import lang.ast.NodeVisitor;


public class Interp extends NodeVisitor {
    public Stack<Object> stk;
    private boolean retMode;
    private Env env;

    public void printEnv() {
        env.dumpTable();
    }

    public Interp() {
        stk = new Stack<Object>();
        env = new Env();
    }

    public Object getStackTop() { // Alterado para retornar Object
        if (stk.isEmpty()) {
            return null;
        }
        return stk.peek();

    }

    public void visit(MainBlock node) {
        // Interpreta declarações de dados
        for (DataDecl d : node.getDataDecls()) {
            d.accept(this);
        }

        // Interpreta funções (adiciona no ambiente)
        for (Func f : node.getFuncs()) {
            env.put(f.getId(), f);
        }

        // Interpreta comandos principais
        for (Cmd cmd : node.getCmds()) {
            cmd.accept(this);
            if (retMode) { // Se houver retorno, interrompe a execução
                break;
            }
        }
    }

    public void visit(Func func) {
        env.enterScope(); // Cria um novo escopo

        // Itera sobre a lista de parâmetros
        for (Param param : func.getParams()) {
            System.out.println("Processando parâmetro: " + param.getId());
            env.store(param.getId(), null); // Inicializa o parâmetro com null
        }

        // Interpreta o corpo da função
        for (Cmd cmd : func.getBody()) {
            cmd.accept(this);
        }

        env.exitScope(); // Sai do escopo
    }

    public void visit(Param param) {
        // Este método pode ser simplificado ou usado para validar o parâmetro, se necessário
        System.out.println("Visitando parâmetro: " + param.getId());
        env.store(param.getId(), null); // Armazena o parâmetro com valor inicial null, se
                                        // necessário
    }

    public void visit(Cmd e) {
        stk.push(e);
    }

    public void visit(Sub e) {}


    public void visit(Plus e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer) stk.pop() + (Integer) stk.pop();
        stk.push(r);
    }

    public void visit(Minus e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer) stk.pop() - (Integer) stk.pop();
        stk.push(r);
    }

    public void visit(Times e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        if (stk.peek() instanceof Integer) {
            Integer id = (Integer) stk.pop();
            if (stk.peek() instanceof Integer) {
                Integer ie = (Integer) stk.pop();
                stk.push(ie * id);
            }
        } else if (stk.peek() instanceof Float) {
            Float id = (Float) stk.pop();
            if (stk.peek() instanceof Float) {
                Float ie = (Float) stk.pop();
                stk.push(ie * id);
            }
        } else {
            throw new RuntimeException("Operação não permitida entre os tipos " + e.getLine() + ", "
                    + e.getCol() + ".");
        }
    }

    public void visit(Mod e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        if (stk.peek() instanceof Integer) {
            Integer id = (Integer) stk.pop();
            if (stk.peek() instanceof Integer) {
                Integer ie = (Integer) stk.pop();
                stk.push(ie % id);
            }
        } else {
            throw new RuntimeException("Operação não permitida entre os tipos " + e.getLine() + ", "
                    + e.getCol() + ".");
        }
    }

    public void visit(MenorQ e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        if (stk.peek() instanceof Integer) {
            Integer right = (Integer) stk.pop();
            if (stk.peek() instanceof Integer) {
                Integer left = (Integer) stk.pop();
                stk.push(left < right);
            }
        } else {
            throw new RuntimeException("Operação não permitida entre os tipos " + e.getLine() + ", "
                    + e.getCol() + ".");
        }
    }

    public void visit(MaiorQ e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        if (stk.peek() instanceof Integer) {
            Integer right = (Integer) stk.pop();
            if (stk.peek() instanceof Integer) {
                Integer left = (Integer) stk.pop();
                stk.push(left > right);
            }
        } else {
            throw new RuntimeException("Operação não permitida entre os tipos " + e.getLine() + ", "
                    + e.getCol() + ".");
        }
    }

    public void visit(Div e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        if (stk.peek() instanceof Integer) {
            Integer id = (Integer) stk.pop();
            if (stk.peek() instanceof Integer) {
                Integer ie = (Integer) stk.pop();
                stk.push(ie / id);
            }
        } else if (stk.peek() instanceof Float) {
            Float id = (Float) stk.pop();
            if (stk.peek() instanceof Float) {
                Float ie = (Float) stk.pop();
                stk.push(ie / id);
            }
        } else {
            throw new RuntimeException("Operação não permitida entre os tipos " + e.getLine() + ", "
                    + e.getCol() + ".");
        }
    }



    public void visit(Igual e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Object right = stk.pop();
        Object left = stk.pop();
        stk.push(left.equals(right));
    }

    public void visit(Diferente e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Object right = stk.pop();
        Object left = stk.pop();
        stk.push(!left.equals(right));
    }


    public void visit(And e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Object right = stk.pop();
        Object left = stk.pop();

        if (left instanceof Integer && right instanceof Integer) {
            Integer leftInt = (Integer) left;
            Integer rightInt = (Integer) right;
            stk.push(leftInt != 0 && rightInt != 0);
        } else if (left instanceof Boolean && right instanceof Boolean) {
            Boolean leftBool = (Boolean) left;
            Boolean rightBool = (Boolean) right;
            stk.push(leftBool && rightBool);
        } else {
            throw new RuntimeException("Operação '&&' não permitida entre os tipos " + e.getLine()
                    + ", " + e.getCol() + ".");
        }
    }

    public void visit(Not e) {
        e.getExpr().accept(this);
        Object value = stk.pop();

        if (value instanceof Boolean) {
            Boolean boolValue = (Boolean) value;
            stk.push(!boolValue);
        } else if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            stk.push(intValue == 0);
        } else {
            throw new RuntimeException("Operação '!' não permitida entre os tipos " + e.getLine()
                    + ", " + e.getCol() + ".");
        }
    }


    public void visit(Attrib e) {
        System.out.println("Atribuindo: " + e.getVar().getName());
        e.getExpr().accept(this); // Avalia a expressão antes de armazenar
        Object value = stk.pop(); // Obtém o valor correto da pilha
        System.out.println("Valor atribuído: " + value);

        env.store(e.getVar().getName(), value); // Armazena no ambiente

    }

    public void visit(ReturnExpr e) {
        e.getExpr().accept(this);
        Object result = stk.pop();
        stk.push(result);
        retMode = true; // Sinaliza que encontramos um return
    }

    public void visit(Comma e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
    }

    public void visit(Var e) {
        Object value = env.read(e.getName());
        stk.push(value);
    }

    public void visit(IntLit e) {
        stk.push(e.getValue());
    }

    public void visit(BoolLit e) {
        stk.push(e.getValue());
    }

    public void visit(TyBool t) {}

    public void visit(TyInt t) {}

    public void visit(TyChar t) {}

    public void visit(TyFloat t) {}

    public void visit(UserType t) {
        stk.push(t.getTypeName()); // Empurra o nome do tipo definido pelo usuário para a pilha.
    }

    public void visit(TyVoid t) {
        stk.push(null);
    }


    @Override
    public void visit(Print e) {
        // Avalia a expressão associada ao comando Print
        e.getExpr().accept(this);

        // Recupera o resultado da pilha
        Object result = stk.pop();

        // Exibe o resultado no console
        System.out.println(result);
    }

    @Override
    public void visit(StringLit e) {
        stk.push(e.getValue());
    }

    @Override
    public void visit(CharLit e) {
        stk.push(e.getValue()); // Empurra a string literal diretamente
    }

    @Override
    public void visit(NullLit e) {
        stk.push(null); // Empilha o valor null
    }


    public void visit(IfCmd var1) {
        var1.getCondition().accept(this);
        Boolean var2 = (Boolean) this.stk.pop();

        if (var2) {
            var1.getCommand().accept(this);
        } else if (var1.getElseCmd() != null) {
            var1.getElseCmd().accept(this);
        }
    }



    @Override
    public void visit(FloatLit e) {
        // Obter o valor do literal float
        Float value = e.getValue();
        // Empurrar o valor na pilha
        stk.push(value);
    }

    public void visit(IterateCmd cmd) {
        // System.out.println("Visitando IterateCmd");

        // Avaliar a condição e obter o número de iterações
        cmd.getCondition().accept(this);
        int times = (Integer) stk.pop(); // Número de iterações
        // System.out.println("Número de iterações avaliadas: " + times);

        // Executar o corpo para cada iteração
        for (int i = 0; i < times; i++) {
            // System.out.println("Executando iteração: " + (i + 1));
            cmd.getBody().accept(this); // Avaliar o corpo
        }

        System.out.println("Finalizando IterateCmd");
    }


    public void visit(DataDecl e) {
        String dataTypeName = e.getTypeId(); // Obtém o nome do tipo
        List<Decl> fields = e.getDecls(); // Obtém a lista de campos

        // Armazena a definição do tipo de dado no ambiente
        env.storeDataType(dataTypeName, fields);
    }
   
    public void visit(ArrayInit e) {
        // Avalia a expressão que representa o tamanho do array
        e.getSize().accept(this);
        Object sizeValue = stk.pop();

        // Verifica se o tamanho é um inteiro
        if (!(sizeValue instanceof Integer)) {
            throw new RuntimeException("O tamanho do array deve ser um inteiro. Encontrado: " + sizeValue);
        }

        int size = (Integer) sizeValue;

        // Verifica se o tamanho do array é válido
        if (size < 0) {
            throw new RuntimeException("O tamanho do array deve ser maior ou igual a zero.");
        }

        // Cria o array baseado no tipo (LType)
        Object array;
        if (e.getType() instanceof TyInt) {
            array = new Integer[size];
        } else if (e.getType() instanceof TyFloat) {
            array = new Float[size];
        } else if (e.getType() instanceof TyChar) {
            array = new Character[size];
        } else if (e.getType() instanceof UserType) {
            // Verifica o nome do UserType
            UserType userType = (UserType) e.getType();
            String typeName = userType.getTypeName();
            if (typeName.equals("Int")) {
                array = new Integer[size];
            } else if (typeName.equals("Float")) {
                array = new Float[size];
            } else if (typeName.equals("Char")) {
                array = new Character[size];
            } else {
                throw new RuntimeException("Tipo personalizado não suportado: " + typeName);
            }
        } else {
            throw new RuntimeException("Tipo não suportado para inicialização de array: " + e.getType());
        }

        // Empilha o array na pilha
        stk.push(array);
    }

    public void visit(IterateCmdWithVar cmd) {
        // Avalia a condição do loop
        cmd.getCondition().accept(this);
        Object limitValue = stk.pop();
    
        // Verifica se o limite é um inteiro
        if (!(limitValue instanceof Integer)) {
            throw new RuntimeException("O limite do loop deve ser um inteiro. Encontrado: " + limitValue);
        }
    
        int limit = (Integer) limitValue;
        String iteratorName = cmd.getIterator().getName();
    
        // Executa o corpo para cada iteração
        for (int i = 0; i < limit; i++) {
            env.store(iteratorName, i); // Armazena o valor atual do iterador
            cmd.getBody().accept(this); // Executa o corpo do loop
        }
    
        System.out.println("Finalizando IterateCmdWithVar");
    }


    public void visit(Decl e) {
        String varName = e.getId();
        LType varType = e.getType();

        // Inicializa a variável com valor padrão dependendo do tipo
        Object defaultValue;
        if (varType instanceof TyInt) {
            defaultValue = 0;
        } else if (varType instanceof TyFloat) {
            defaultValue = 0.0f;
        } else if (varType instanceof TyBool) {
            defaultValue = false;
        } else if (varType instanceof TyChar) {
            defaultValue = '\0';
        } else if (varType instanceof TyVoid) {
            defaultValue = null;
        } else if (varType instanceof UserType) {
            // Pode inicializar com null ou outra lógica específica
            defaultValue = null;
        } else {
            throw new RuntimeException("Tipo não suportado para variável: " + varName);
        }

        env.store(varName, defaultValue);
    }

}


