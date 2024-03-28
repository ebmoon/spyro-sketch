variables {
    int x;
    int y;
    hidden int z;
    boolean ok;
}

signatures {
    test2(x, y, z, ok);
}

language {
    boolean B0 -> O && B;
    boolean O -> ok | !ok;
    boolean B -> false | AP | AP && AP | AP && AP && AP ;
    boolean AP -> N == N | N != N;
    int N -> x | y | ??(6);
}

examples {
    int IEX -> ??(6);
    boolean BEX -> ??(1);
}