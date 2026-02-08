package plib;

/* This class implements a backtrack parser engine.
 * Elton M. Cardoso UFOP-DECSI  November 2024
 *
 *
 *
*/

import java.io.*;
import java.util.*;

public class EOFToken implements VirtualToken{

      public int getTag(){return -1;}
      public int line(){return -1;}
      public int col(){return -1;}
      public Object val(){return null;}
      public boolean isEOF(){return true;}
      public String toStr(){return "(-1,-1) TK: EOF";}
}
