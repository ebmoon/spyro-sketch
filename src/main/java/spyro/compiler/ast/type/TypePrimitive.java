package spyro.compiler.ast.type;

import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.util.exceptions.ParseException;

/**
 * Class for primitive types
 * 
 * @author Kanghee Park &lt;khpark@cs.wisc.edu&gt;
 */
public class TypePrimitive extends Type {

	private PredefinedType ty;
	
	public enum PredefinedType {
		TYPE_INT,
		TYPE_BOOLEAN
	}
	
	public TypePrimitive(String id) {
		super(id);
		if (id.equals("int")) {
			ty = PredefinedType.TYPE_INT;
		} else if (id.equals("boolean")) {
			ty = PredefinedType.TYPE_BOOLEAN;
		} else {
			throw new ParseException("Unknown primitive type");
		}
	}

	@Override
	public Object accept(SpyroNodeVisitor v) {
		return v.visitTypePrimitive(this);
	}
	
	public PredefinedType getPredefinedType() { return ty; }
}
