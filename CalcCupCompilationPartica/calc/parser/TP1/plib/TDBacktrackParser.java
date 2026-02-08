package plib;

/* This class implements a backtrack parser engine.
 * Elton M. Cardoso UFOP-DECSI  November 2024
 *
 *
 *
*/

import java.io.*;
import java.util.*;


public abstract class TDBacktrackParser<T>{
     private VirtualToken EOFSymbol;
     private VirtualLexer lex;
     private ArrayList<VirtualToken> buffToken;
     private Stack<Integer> s;
     protected Stack<T> stree;
     private int pos, rpos, level, nback;
     private boolean eof, debug;

     public TDBacktrackParser( VirtualLexer lexer) throws IOException {
         //fd = new FileInputStream(path);
         lex = lexer;
         buffToken = new ArrayList<VirtualToken>(1500);
         s = new Stack<Integer>();
         stree = new Stack<T>();
         pos = 0;
         rpos =0;
         nback = 0;
         eof = false;
         debug = false;
         EOFSymbol = new EOFToken();
         nextToken();
     }

     public TDBacktrackParser(VirtualLexer lexer, boolean d) throws IOException {
         this(lexer);
         debug = d;
     }


     public void nextToken(){
         VirtualToken t = EOFSymbol;
         if(!eof){
             if(pos < rpos){
                 pos ++;
                 eof = buffToken.get(pos-1).isEOF();
             }else{
                try{
                   t = lex.nextToken();
                   if(t == null){
                       eof = true;
                       t = EOFSymbol;
                       buffToken.add(t);
                   }else{ buffToken.add(t);}
                   rpos++;
                   pos++;
                }catch(IOException e){
                   e.printStackTrace();
                   System.exit(1);
                }
             }
         }
     }

     public VirtualToken current(){return buffToken.get(pos-1); }

     public boolean match(int t){
           // Assume que o readToken já foi invocada. (O buffer contém pelo menos um token)
           if(buffToken.get(pos-1).getTag() == t){
                nextToken();
                return true;
           }
           return false;
     }

     private void d_msg(String s){
         for(int i = 0; i < level; i++){System.out.print(">> ");}
         System.out.println(s);
     }

     public void debug_startRule(String rname){
        if(debug){
          d_msg(" Entering " + rname + "["+  pos + "("+ rpos+  ") " + buffToken.get(pos-1).toString() + "]");
          level++;
        }
     }

     public void debug_endRuleOK(String rname){
          if(debug){
             level--;
             d_msg(" Leaving " + rname + " [ OK ]");
          }
     }

     public void debug_endRuleFAIL(String rname){
          if(debug){
             level--;
             d_msg(" Leaving " + rname + " [FAIL]");
          }
     }

     public void mark(){ s.push(stree.size()); s.push(pos); }

     public void commit(){ s.pop(); s.pop(); }

     public void backtrack(String rname){
          nback ++;
          pos = s.pop();
          int tsize = s.pop();
          eof =  buffToken.get(pos-1).isEOF();
          while(stree.size() > tsize){stree.pop();}
          if(debug){
               d_msg(" Retrying " + rname + "[" + pos + "("+ rpos+  ") " + buffToken.get(pos-1).toStr() + "]");
          }
     }

     public abstract boolean start();


     public T getTree(){
        return stree.peek();
     }

     public void printBuffuer(){
          for(int i = 0; i <  buffToken.size(); i++){
              System.out.print("[" + i + "]" + buffToken.get(i).toString() + " -> ");
          }
          System.out.println(" NIL ");
     }

     public void reportFullState(){
             for(int i = 0; i <  buffToken.size(); i++){
                 System.out.println("[" + i + "]" + buffToken.get(i).toString() + " -> ");
             }
             System.out.println(" NIL ");
             System.out.println(" pos " + pos + "; rpos " + rpos);
             System.out.println(" EOF? " + eof);
             System.out.print(" Stack> ");
             while(!s.empty()){
                 System.out.print(s.pop() + " -> ");
             }
             System.out.println("BP");
     }

     public void reportShortState(){
             System.out.println(" Stop at: " + pos + "; read until " + rpos);
             System.out.println(" Last token " + buffToken.get(pos-1).toString());
             System.out.println(" EOF? " + eof);
             System.out.println(" Number of backtracks : " + nback);
     }

     public void scanAll(){
          try{
            VirtualToken t = lex.nextToken();
            while(t != null){
               System.out.println(t.toString());
               t = lex.nextToken();
            }
          }catch(IOException e){
              e.printStackTrace();
          }
     }
}
