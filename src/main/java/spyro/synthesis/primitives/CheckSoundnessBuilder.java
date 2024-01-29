package spyro.synthesis.primitives;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Function.FunctionCreator;
import sketch.compiler.ast.core.Program;
import spyro.compiler.ast.Query;
import spyro.compiler.ast.expr.ConstBool;
import spyro.compiler.ast.expr.ConstInt;
import spyro.compiler.ast.expr.ConstNull;
import spyro.compiler.ast.expr.ExprBinary;
import spyro.compiler.ast.expr.ExprFuncCall;
import spyro.compiler.ast.expr.ExprUnary;
import spyro.compiler.ast.expr.Hole;
import spyro.compiler.ast.expr.Variable;
import spyro.compiler.ast.grammar.ExampleRule;
import spyro.compiler.ast.grammar.GrammarRule;
import spyro.compiler.ast.type.TypePrimitive;
import spyro.compiler.ast.type.TypeStruct;

public class CheckSoundnessBuilder extends CommonSketchBuilder {

	private Property phi;
	
	public CheckSoundnessBuilder(Program prog, Property phi) {
		super(prog);
		this.phi = phi;
	}
	
	private Function soundnessCheckFunction() {
		FunctionCreator fc = Function.creator(prog, "soundness", Function.FcnType.Harness);
		
		// TODO Fill implementation
		fc.returnType(sketch.compiler.ast.core.typs.TypePrimitive.voidtype);
		
		return fc.create();
	}

}
