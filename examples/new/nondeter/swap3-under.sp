variables {
    int a1;
    int a2;
    int a0;
    int n;
    hidden Array h1;
    hidden Array h2;
    boolean ok;
}

signatures {
    random_sort(a0, a1, a2, n, h1, h2, ok);
}

language {
    boolean B0 -> B && ok | B && !ok ;
    boolean B -> false | AP | AP && AP
                | AP && AP && AP
                | AP && AP && AP && AP
                | AP && AP && AP && AP && AP
                | AP && AP && AP && AP && AP && AP;
    boolean AP -> N < N | N <= N | N == N | N != N | n < ??(3) | n >= ??(3);
    int N -> a0 | a1 | a2;
}

examples {
    int IEX -> ??(3);
    Array ArrayEX -> genArray();
    boolean BEX -> ??(1);
}