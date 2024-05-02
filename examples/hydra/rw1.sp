// Rewrite rules: C == C & KnownBits(x).ones |= v | C == v

variables {
    BV v;
    BV C;
    Abs a;
    boolean valid;
}

signatures {
    rewrite(v, C, valid);
}

language {
    boolean B0 -> valid && belong(v, a) && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> BV_equal(N, N);
    BV N -> N1 | and(N1, N1) | or(N1, N1) | xor(N1, N1) | neg(N1);
    BV N1 -> getOnes(a) | getZeros(a)| C;
}

examples {
    BV BVEX -> genBV();
    Abs AbsEX -> consAbs(BVEX, BVEX);
    boolean BEX -> ??(1);
}
