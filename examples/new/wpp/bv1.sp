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
    boolean B0 -> B;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> subset(N, N) | N == N | And(op) | Or(op) | Xor(op) | BV_equal(N, N);
    BV N -> a | o | genBV();
}

examples {
    Op OpEX -> genOp();
    BV BVEX -> genBV();
}