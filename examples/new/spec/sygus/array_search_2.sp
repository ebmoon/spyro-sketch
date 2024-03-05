//@Description array_search_2.sl of SyGuS array track.

variables {
    int x1;
    int x2;
    int k;
    int o;
}

signatures {
    f(x1, x2, k, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> I < I | I <= I | I == I | I != I | S < S | S <= S | S == S | S != S;
    int S -> 0 | 1 | 2 | o;
    int I -> x1 | x2 | k;
}

examples {
    int IEX -> ??(4) | -1 * ??(4) ;
}

