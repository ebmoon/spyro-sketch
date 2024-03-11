//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x1;
    int x2;
    int x3;
    int o;
}

signatures {
    max3(x1, x2, x3, o);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> I < I | I <= I | I > I | I >= I | I == I | I != I ;
    int I -> x1 | x2 | x3 | o ;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}
