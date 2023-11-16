package spyro.compiler.ast;

/**
 * Node in Spyro AST. 
 * Derived classes include expressions, signatures, grammar and example constructor.
 * 
 * @author Kanghee Park &lt;kpark247@wisc.edu&gt;
 */
public abstract class SpyroNode {
	public abstract void accept(SpyroNodeVisitor v);
}
