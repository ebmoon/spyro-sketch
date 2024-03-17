//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x1;
    int x2;
    int x3;
    int x4;
    int o;
}

signatures {
    max4(x1, x2, x3, x4, o);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> I < I | I <= I | I > I | I >= I | I == I | I != I ;
    int I -> x1 | x2 | x3 | x4 | o ;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}

