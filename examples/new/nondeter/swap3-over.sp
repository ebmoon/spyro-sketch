variables {
    int a1 <- IEX1;
    int a2 <- IEX1;
    int a0 <- IEX1;
    int n <- IEX2;
    hidden Array h1;
    hidden Array h2;
    boolean ok;
}

signatures {
    random_sort(a0, a1, a2, n, h1, h2, ok);
}

language {
    boolean B0 -> !B || ok | !B || !ok ;
    boolean B -> false | AP | AP && AP
                | AP && AP && AP
                | AP && AP && AP && AP
                | AP && AP && AP && AP && AP
                | AP && AP && AP && AP && AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N | n < ??(2) | n >= ??(2);
    int N -> a0 | a1 | a2;
}

examples {
    int IEX1 -> ??(3);
    int IEX2 -> ??(2);
    Array ArrayEX -> genArray();
    boolean BEX -> ??(1);
}