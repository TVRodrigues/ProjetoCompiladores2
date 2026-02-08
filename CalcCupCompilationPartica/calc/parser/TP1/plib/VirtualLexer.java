package plib;

/* This class implements a backtrack parser engine.
 * Elton M. Cardoso UFOP-DECSI  November 2024
 *
 *
 *
*/

import java.io.*;
import java.util.*;
import boolCalc.nodes.*;

public interface VirtualLexer{

     public VirtualToken nextToken()throws java.io.IOException;

}
