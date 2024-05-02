// Rewrite rules: IsPowerOf2(C) |= x:i4 * C == x:i4 << log2(C)

variables {
    int x;
    int C;
    boolean valid;
}

signatures {
    rewrite(x, C, valid);
}

language {
    boolean B0 -> valid && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> IsPowerOf2(N) | N == N;
    int N -> N1 | N1 * N1 | log2(N1);
    int N1 -> C | 1 | 0;
}

examples {
    int IEX -> ??(4);
    boolean BEX -> ??(1);
}
