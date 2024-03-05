variables {
    int x1;
    int x2;
    int o;
}

signatures {
    max2(x1, x2, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> I < I | I <= I | I > I | I >= I | I == I | I != I ;
    int I -> x1 | x2 | o ;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}