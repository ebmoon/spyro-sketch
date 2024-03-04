variables {
    int x;
    int y;
}

signatures {
    prog(y, x);
}

language {
    boolean B -> true | AP | AP || AP;
    boolean AP -> N < N | N <= N | N == N | N != N;
    int N -> x | y | 0 | 1;
}

examples {
    int IEX -> ??(4) - 8;
    boolean BEX -> ??(1);
}