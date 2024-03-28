 variables {
    int x <- IEX1;
    int a <- IEX2;
    int b <- IEX2;
    hidden int h0 <- IEX3;
    hidden int h1 <- IEX3;
    boolean post;
}

signatures {
    prog(x, a, b, h0, h1, post);
}

language {
    boolean B0 -> post && (a <= b) && B ;
    boolean B -> false | AP | AP && AP
                | AP && AP && AP
                | AP && AP && AP && AP;
    boolean AP -> N < N | N <= N | N == N;
    int N -> N0 + N0 | N0 - N0;
    int N0 -> x | a | b | 0 | 1 ;
}

examples {
    int IEX1 -> ??(3) | -??(3);
    int IEX2 -> ??(4) | -??(4);
    int IEX3 -> -1 | 0 | 1;
    boolean BEX -> ??(1);
}