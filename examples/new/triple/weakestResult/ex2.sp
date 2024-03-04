variables {
    int x;
    hidden int y;
}

signatures {
    prog(y, x);
}

language {
    boolean B -> false | AP | AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N;
    int N -> x | 0 | 1;
}

examples {
    int IEX -> ??(4) - 8;
    boolean BEX -> ??(1);
}


assumptions {
    _pre(y);
}