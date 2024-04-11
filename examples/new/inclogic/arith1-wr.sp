 variables {
    hidden int x;
    int a;
    int b;
    int x_out;
    hidden boolean h;
    boolean pre;
}

signatures {
    flip_pre(x, a, b, h, x_out, pre);
}

language {
    boolean B0 -> pre && B;
    boolean B -> false | AP | AP && AP
                | AP && AP && AP
                | AP && AP && AP && AP
                | AP && AP && AP && AP && AP
                | AP && AP && AP && AP && AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N | is_pos(N);
    int N -> N0 | -N0;
    int N0 -> x_out | a | b | 0 ;
}

examples {
    int IEX -> ??(3) | -??(3);
    boolean BEX -> ??(1);
}