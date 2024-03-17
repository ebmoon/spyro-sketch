variables {
    int x;
    hidden int y;
    boolean ok;
}

signatures {
    prog(y, x, ok);
}

language {
    boolean B0 -> (ok == 1) && B;
    boolean B -> false | AP | AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N;
    int N -> x | 0 | 1;
}

examples {
    int IEX -> ??(4) - 8;
    boolean BEX -> ??(1);
}