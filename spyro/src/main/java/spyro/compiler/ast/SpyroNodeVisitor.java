package spyro.compiler.ast;

import spyro.compiler.ast.expr.*;

/**
 * Visitor interface for SpyroNode nodes. 
 * This class implements visitor pattern for SpyroNode class.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public interface SpyroNodeVisitor {
	public Object visitQuery(Query q);
	
	public Object visitVariable(Variable decl);
	public Object visitHole(Hole hole);
	public Object visitExprFuncCall(ExprFuncCall fc);
	public Object visitExprUnary(ExprUnary e);
	public Object visitExprBinary(ExprBinary e);
	
	public Object visitConstInt(ConstInt n);
	public Object visitConstBool(ConstBool b);
	public Object visitConstNull(ConstNull nullptr);
}
