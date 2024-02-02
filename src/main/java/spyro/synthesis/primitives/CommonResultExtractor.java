package spyro.synthesis.primitives;

import sketch.compiler.ast.core.Function;
import sketch.compiler.ast.core.Program;
import sketch.compiler.ast.core.stmts.Statement;
import spyro.synthesis.Property;

import java.util.List;

public class CommonResultExtractor {

    Program result;

    public CommonResultExtractor(Program result) {
        this.result = result;
    }

    // For debug
    void enumerateStatements(List<Statement> stmts) {
        int idx = 0;
        for(Statement stmt : stmts) {
            System.out.println(idx++);
            System.out.println(stmt.getClass());
            System.out.println(stmt.toString());
        }
    }

    static Function findFunction(Program prog, String name) {
        List<Function> funcs = prog.getPackages().get(0).getFuncs();
        for (Function func : funcs)
            if (func.getName().equals(name))
                return func;

        return null;
    }
}
