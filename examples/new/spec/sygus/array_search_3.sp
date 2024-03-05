//@Description array_search_3.sl of SyGuS array track.
//recommend option --num-atoms-max 5

variables {
    int x1;
    int x2;
    int x3;
    int k;
    int o;
}

signatures {
    f(x1, x2, x3, k, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP | AP || AP || AP || AP;
    boolean AP -> I < I | I <= I | I == I | I != I | S < S | S <= S | S == S | S != S;
    int S -> 0 | 1 | 2 | 3 | o;
    int I -> x1 | x2 | x3 | k;
}

examples {
    int IEX -> ??(4) | -1 * ??(4) ;
}

