variables {
    BV a;
    BV b1;
    BV b2;
    hidden BV h;
    BV o;
}

signatures {
    fun4(a, b1, b2, h, o);
}

language {
    boolean B0 -> B;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> BV_equal(N, N);
    BV N -> N1 | and(N1, N1) | or(N1, N1) | xor(N1, N1) | neg(N1);
    BV N1 -> N2 | and(N2, N2) | or(N2, N2) | xor(N2, N2) | neg(N2);
    BV N2 -> a | b1 | b2 | o;
}

examples {
    BV BVEX -> genBV();
}