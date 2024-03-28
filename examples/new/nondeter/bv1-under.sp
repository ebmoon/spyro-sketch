variables {
    BV a;
    Op op;
    hidden BV b;
    BV o;
}

signatures {
    eval(op, a, b, o);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> isAnd(op) | isOr(op) | isXor(op) | BV_equal(N, N);
    BV N -> N1 | and(N1, N1) | or(N1, N1) | xor(N1, N1) | neg(N1);
    BV N1 -> a | o | genBV();
}

examples {
    Op OpEX -> genOp();
    BV BVEX -> genBV();
}