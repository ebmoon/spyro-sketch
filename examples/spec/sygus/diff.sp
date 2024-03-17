//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x;
    int y;
    int o;
}

signatures {
    f(x, y, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> I < I + I | I <= I + I | I > I + I | I >= I + I | I == I + I | I != I + I ;
    int I -> x | y | o | 0 ;
}

examples {
    int IEX -> ??(4) | -1 * ??(4) ;
}
