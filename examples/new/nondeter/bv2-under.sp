variables {
    BV a;
    BV b;
    hidden BV h;
    BV o;
}

signatures {
    fun2(a, b, h, o);
}

language {
    boolean B0 -> B;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> BV_equal(N, N);
    BV N -> N1 | and(N1, N1) | or(N1, N1) | xor(N1, N1) | neg(N1);
    BV N1 -> a | b | o | genBV();
}

examples {
    BV BVEX -> genBV();
}