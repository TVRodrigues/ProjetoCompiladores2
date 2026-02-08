
/* 21/11/2024 - Lang_2 lexer
 * Thiago V. Rodrigues
 * 15.2.8140
 */
import plib.VirtualToken;

public class Token implements VirtualToken{

   public Object value;
   public TK tk;
   public int line;
   public int column;

   public Token(int line, int column, TK tk, String text) {
        this.line = line;
        this.column = column; 
        this.tk = tk;
        this.value = text;
   }

   public Token(int line, int column, TK t, Object v){
       this(line,column,t, null);
       value = v;
   }

   public String toString(){
       return "(" + line + "," + column+ ") TK: " + tk +  (value == null ? "" : "  val: " + value.toString());
   }


   public int getTag(){ return tk.ordinal(); }
   public int line(){return line;}
   public int col(){return column;}
   public Object val(){return value;}
   public boolean isEOF(){return tk == TK.EOF;}
   public String toStr(){return toString();}

}
