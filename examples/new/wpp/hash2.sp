variables {
    PosInt M;
    hidden int x;
    int a;
    int b;
    hidden int a2;
    hidden int b2;
    boolean ok1;
    boolean ok2;
    boolean collide;
}

signatures {
    h(a, x, M, ok1, a2);
    h(b, x, M, ok2, b2);
    eq(a2, b2, collide);
}

language {
    boolean B0 -> ok1 && ok2 && B && collide;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> isPrime(M) | !isPrime(M) | N == N | N != N | N > N;
    int N -> N0 | mod(N0, N0) | gcd(N0, N0);
    int N0 -> a | b | getData(M) | 0 | 1;
}

examples {
    int IEX -> ??(4) - 8;
    PosInt PosIntEX-> genPosInt();
    boolean BEX-> true | false;
}