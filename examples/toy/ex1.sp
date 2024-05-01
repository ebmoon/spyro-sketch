variables {
    int x;
    hidden int h;
    int xout;
}

signatures {
    f(x, h, xout);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> I < I | I == I | I <= I | I != I;
    int I -> x | y | 0;
}

examples {
    int IEX -> ??(4);
    boolean BEX -> ??(1);
}
