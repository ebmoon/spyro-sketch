//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x1;
    int y1;
    int o1;

    int x2;
    int y2;
    int o2;
}

signatures {
    f(x1, y1, o1);
    f(x2, y2, o2);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> I == I | I != I;
    int I -> x1 | x2 | y1 | y2 | o1 | o2;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}

