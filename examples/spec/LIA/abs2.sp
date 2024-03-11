//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x;
    int o;
}

signatures {
    abs(x, o);
}

language {
    boolean D -> true | AP | AP || AP | AP || AP || AP ;
    boolean AP -> LHS < 0 | LHS <= 0 | LHS > 0 | LHS >= 0 | LHS == 0 | LHS != 0;
    int LHS -> C * x + C * o + C;
    int C -> ??(3) - 3 ;
}

examples {
    int IEX -> ?? | -1 * ??;
}