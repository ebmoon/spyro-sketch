//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x;
    int o;
}

signatures {
    abs(x, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> I < I | I <= I | I == I | I != I ;
    int I -> x | -x | o | -o | 0 ;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}

