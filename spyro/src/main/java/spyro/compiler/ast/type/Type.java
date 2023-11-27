package spyro.compiler.ast.type;

import spyro.compiler.ast.SpyroNode;

/**
 * Abstract class for variable data types.
 *
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public abstract class Type extends SpyroNode {

	private String id;
	
	public Type(String id) {
		this.id = String.valueOf(id);
	}
	
    public boolean isStruct () { return false; }
    public boolean isArray () { return false; }
    
    public String toString() { return id; }
}
