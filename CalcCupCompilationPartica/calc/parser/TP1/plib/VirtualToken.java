package plib;

/* This class implements a backtrack parser engine.
 * Elton M. Cardoso UFOP-DECSI  November 2024
 *
 *
 *
*/

import java.io.*;
import java.util.*;


public interface VirtualToken{

      public int getTag();
      public int line();
      public int col();
      public Object val();
      public boolean isEOF();
      public String toStr();
}
