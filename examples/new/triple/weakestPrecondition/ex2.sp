variables {
    int x;
    int y;
}

signatures {
    prog(y, x);
}

language {
    boolean B -> !P || Q;
    boolean Q -> x > 0;
    boolean P -> false | AP | AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N;
    int N -> y | 0 | 1;
}

examples {
    int IEX -> ??(4) - 8;
    boolean BEX -> ??(1);
}
