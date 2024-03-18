variables {
    int M <- PosIntEX;
    hidden int h <- IEX;
    ArrayList S;
    boolean collide;
}

signatures {
    prog(S, M, h, collide);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> isPrime(M) | !isPrime(M) | N == N | N != N | N < N | N <= N | collide == true | collide == false;
    int N -> setsize(S) | setsizemod(S, M) | 0 | 1 | M;
}

examples {
    int IEX -> ??(4) - 8;
    int PosIntEX-> ??(3) + 2;
    ArrayList ALEX-> newArrayList() | add(ALEX, IEX);
    boolean BEX-> true | false;
}