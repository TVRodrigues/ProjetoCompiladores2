package calc.nodes.visitors.tychkvisitor;

import calc.nodes.decl.*;
import calc.nodes.expr.*;
import calc.nodes.command.*;
import calc.nodes.types.*;
import calc.nodes.*;
import calc.nodes.CalcVisitor;
import calc.nodes.dotutils.DotFile;

public abstract  class VType {
     public short type;
     protected VType(short type){ this.type = type;}
     public abstract boolean match(VType t);
     public short getTypeValue(){ return type;}
}
