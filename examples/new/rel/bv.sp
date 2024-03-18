variables {
    BV a;
    BV b;
    hidden int h;
}

signatures {
    flip_1(a, h, b);
}

language {
    boolean B0 -> B;
    boolean B -> BV_equal(b, E);
    BV E -> E2 | and(E1, E1) | or(E1, E1) | xor(E1, E1) | neg(E1) | add1(E1) | sub1(E1);
    BV E1 -> E2 | and(E2, E2) | or(E2, E2) | xor(E2, E2) | neg(E1) | add1(E2) | sub1(E2);
    BV E2 -> a;
}

examples {
    BV BVEX -> genBV();
    int IEX -> ??(2);
}