variables {
    int n0 <- IEX0;
    int n1 <- IEX0;
    hidden RandomBits h;
    int t <- IEX1;
}

signatures {
    scheduler(n0, n1, h, t);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP ;
    boolean AP -> N0 < N0 | N0 <= N0 | N0 == N0 |  n0 > 0 | n1 > 0;
    int N0 ->  n0 | n1 | -n0 | -n1 | t ;
}

examples {
    boolean BEX -> ??(1);
    RandomBits RBEX -> genRandomBits();
    int IEX0 -> ??(2);
    int IEX1 -> ??(4) - 8;
}