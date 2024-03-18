 variables {
    int x;
    int a;
    int b;
    hidden boolean h;
    boolean ok;
}

signatures {
    prog(x, a, b, h, ok);
}

language {
    boolean B0 -> (ok == 1) && B ;
    boolean B -> false | AP | AP && AP
                | AP && AP && AP
                | AP && AP && AP && AP
                | AP && AP && AP && AP && AP
                | AP && AP && AP && AP && AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N | is_pos(N);
    int N -> N0 | -N0;
    int N0 -> x | max(abs(a), abs(b)) | a | b | 0 ;
}

examples {
    int IEX -> ??(3) - 4;
    boolean BEX -> ??(1);
}