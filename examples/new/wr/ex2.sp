variables {
    int x;
    hidden int y;
    boolean pre;
}

signatures {
    prog(y, x, pre);
}

language {
    boolean B0 -> pre && B;
    boolean B -> false | AP | AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N;
    int N -> x | 0 | 1;
}

examples {
    int IEX -> ??(4) - 8;
    boolean BEX -> ??(1);
}