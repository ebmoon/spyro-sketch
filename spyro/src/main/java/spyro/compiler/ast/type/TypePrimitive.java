package spyro.compiler.ast.type;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for primitive types
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class TypePrimitive extends Type {

	public TypePrimitive(String id) {
		super(id);
	}

	@Override
	public void accept(SpyroNodeVisitor v) { 
		v.visitTypePrimitive(this); 
	}
}
