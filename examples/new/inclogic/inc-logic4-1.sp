variables {
    hidden int x;
    int m;
    int x_out;
    boolean pre;
}

signatures {
    client_pre1(x, m, x_out, pre);
}

language {
    boolean B0 -> pre && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> N0 < N0 | N0 <= N0 | N0 == N0;
    int N0 -> N | N + N | N - N;
    int N -> x_out | m | ??(4);
}

examples {
    int IEX -> ??(4);
    boolean BEX -> ??(1);
}