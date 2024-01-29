package spyro.compiler.ast;

/**
 * Node in Spyro AST. 
 * Derived classes include expressions, signatures, grammar and example constructor.
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class SpyroNode {
	public abstract Object accept(SpyroNodeVisitor v);
}
