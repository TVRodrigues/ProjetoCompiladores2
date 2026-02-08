package calc.nodes.visitors.tychkvisitor;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import java.util.Hashtable;

public class TypeEntry {
     public String sym;
     public VType ty;
     public Hashtable<String,VType> localCtx;

}
