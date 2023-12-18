variables {
    int x1;
    int x2;
    int o;
}

signatures {
    max2(x1, x2, o);
}

language {
    boolean AP -> compare(I, I) ;
    int I -> x1 | x2 | o ;
}

examples {
    int -> ??(5) | -1 * ??(5) ;
}