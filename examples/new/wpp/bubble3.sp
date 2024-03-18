variables {
    int a1;
    int a2;
    int a0;
    int n;
    hidden Array h;
    boolean ok;
}

signatures {
    random_sort(a0, a1, a2, n, h, ok);
}

language {
    boolean B0 -> (ok == 1) && B ;
    boolean B -> false | AP | AP && AP
                | AP && AP && AP
                | AP && AP && AP && AP
                | AP && AP && AP && AP && AP
                | AP && AP && AP && AP && AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N;
    int N -> a0 | a1 | a2 | n | ??(3);
}

examples {
    int IEX -> ??(3);
    Array ArrayEX -> genArray();
    boolean BEX -> ??(1);
}