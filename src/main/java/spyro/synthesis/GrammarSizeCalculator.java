package spyro.synthesis;

import spyro.compiler.ast.Query;
import spyro.compiler.ast.grammar.*;
import spyro.util.exceptions.SketchConversionException;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

public class GrammarSizeCalculator {
    HashMap<Nonterminal, BigInteger> nonterminalSize;
    HashMap<Nonterminal, List<RHSTerm>> productions;

    public GrammarSizeCalculator() {
        nonterminalSize = new HashMap<>();
        productions = new HashMap<>();
    }

    public BigInteger computeRHSTermSize(RHSTerm t) {
        if (t instanceof Nonterminal)
            return computeNonterminalSize((Nonterminal) t);
        else if (t instanceof RHSBinary)
            return computeRHSTermSize(((RHSBinary) t).getLeft()).multiply(computeRHSTermSize(((RHSBinary) t).getRight()));
        else if (t instanceof RHSUnary)
            return computeRHSTermSize(((RHSUnary) t).getExpr());
        else if (t instanceof RHSConstInt)
            return BigInteger.ONE;
        else if (t instanceof RHSConstBool)
            return BigInteger.ONE;
        else if (t instanceof RHSConstNull)
            return BigInteger.ONE;
        else if (t instanceof RHSFuncCall) {
            BigInteger ret = BigInteger.ONE;
            for (RHSTerm args : ((RHSFuncCall) t).getArgs())
                ret = ret.multiply(computeRHSTermSize(args));
            return ret;
        } else if (t instanceof RHSHole) {
            if (((RHSHole) t).getSize() == 0)
                throw new SketchConversionException("size of hole is 0");
            else return BigInteger.valueOf(1L << ((RHSHole) t).getSize());
        } else if (t instanceof RHSLambda) {
            return computeRHSTermSize(((RHSLambda) t).getBody());
        } else if (t instanceof RHSVariable) {
            return BigInteger.ONE;
        } else
            throw new SketchConversionException("Unsupported case in grammar: " + t.toString());
    }

    public BigInteger computeNonterminalSize(Nonterminal nonterminal) {
        if (nonterminalSize.containsKey(nonterminal))
            return nonterminalSize.get(nonterminal);
        BigInteger tot = BigInteger.ZERO;
        for (RHSTerm t : productions.get(nonterminal))
            tot = tot.add(computeRHSTermSize(t));
        nonterminalSize.put(nonterminal, tot);
        return tot;
    }


    public BigInteger computeSize(Query q) {
//        long tot = 0;
        for (GrammarRule rule : q.getGrammar())
            productions.put(rule.getNonterminal(), rule.getRules());
        return computeNonterminalSize(q.getGrammar().get(0).getNonterminal());
    }


}
