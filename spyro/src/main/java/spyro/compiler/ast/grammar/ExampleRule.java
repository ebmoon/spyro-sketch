package spyro.compiler.ast.grammar;

import java.util.List;

import spyro.compiler.ast.SpyroNode;
import spyro.compiler.ast.SpyroNodeVisitor;
import spyro.compiler.ast.expr.*;
import spyro.compiler.ast.type.*;

public class ExampleRule extends SpyroNode {

	Type ty;
	List<Expression> rules;

	
	public ExampleRule(Type ty, List<Expression> rules) {
		this.ty = ty;
		this.rules = rules;
	}
	
	@Override
	public void accept(SpyroNodeVisitor v) {
		v.visitExampleRule(this);
	}
	
}
