variables {
    int x;
    int o;
}

signatures {
    abs(x, o);
}

language {
    boolean D -> true | AP | AP || AP | AP || AP || AP ;
    boolean AP -> I < I | I <= I | I != I | I == I ;
    int I -> x | -x | o | -o | 0 ;
}

examples {
    int IEX -> ?? | -1 * ?? ;
}