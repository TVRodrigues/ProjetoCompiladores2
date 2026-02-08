 package lang.ast.expr;

 import lang.ast.NodeVisitor;

public class CharLit extends Exp {

     private char value;

//    public CharLit(int line, int col, char value) {
//            super(line, col);
//           this.value = value;
//     }
   
    public CharLit(int left, int right, char charText) {
       super(left, right);
       this.value = charText;
       }

    public char getValue() {
           return value;
    }

    public void accept(NodeVisitor v) {
           v.visit(this);
     }
 }