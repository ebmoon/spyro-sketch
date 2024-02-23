variables {
    PosInt M;
    hidden int h;
    int a;
    int b;
    hidden int a2;
    hidden int b2;
    boolean collide;
}

signatures {
    hashfunc(a, h, M, a2);
    hashfunc(b, h, M, b2);
    eq(a2, b2, collide);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> isPrime(M) | !isPrime(M) | N == N | N != N | N < N | N <= N | collide == true | collide == false;
    int N -> a | b | mod(a, M) | mod(b, M) | getData(M) ;
}

examples {
    int IEX  -> ??(4) - 8;
    PosInt PosIntEX-> genPosInt();
    boolean BEX-> true | false;
}