package spyro.compiler.ast.type;

import spyro.compiler.ast.SpyroNodeVisitor;

/**
 * Class for struct types
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class TypeStruct extends Type {
	public TypeStruct(String id) {
		super(id);
	}

	@Override
	public void accept(SpyroNodeVisitor v) { 
		v.visitTypeStruct(this); 
	}
	
	@Override
	public boolean isStruct() { return true; }

}
