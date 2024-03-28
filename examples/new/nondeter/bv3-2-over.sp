variables {
    BV a;
    BV b;
    hidden BV h;
    BV o;
}

signatures {
    fun3(a, b, h, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP | AP || AP || AP || AP;
    boolean AP -> AP1 | !AP1;
    boolean AP1 -> BV_equal(N, N);
    BV N -> N1 | and(N1, N1) | or(N1, N1) | xor(N1, N1) | neg(N1);
    BV N1 -> N2 | and(N2, N2) | or(N2, N2) | xor(N2, N2) | neg(N2);
    BV N2 -> a | b | o;
}

examples {
    BV BVEX -> genBV();
}