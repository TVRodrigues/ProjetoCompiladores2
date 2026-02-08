package boolCalc.nodes.environment;

import java.util.HashMap;

public class Env {

     private HashMap<String, Boolean> m;

     public Env(){
         m = new HashMap<String,Boolean>(100);
     }

     public void store(String vname, Boolean value){
          m.put(vname,value);
     }

     public Boolean read(String vname){
         Boolean i = m.get(vname);
         if(i == null){
            throw new RuntimeException("Unknow variable " + vname);
         }
         return i;
     }

}
