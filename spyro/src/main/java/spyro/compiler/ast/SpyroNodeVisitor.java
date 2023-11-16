package spyro.compiler.ast;

public interface SpyroNodeVisitor {
	public Object visitQuery(Query q);
	
	public Object visitVariable(Variable decl);
}
