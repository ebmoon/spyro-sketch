variables {
    PosInt M;
    hidden int h;
    ArrayList S;
    boolean collide;
    boolean ok;
}

signatures {
    prog(S, M, h, ok, collide);
}

language {
    boolean B0 -> (ok == 1) && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> isPrime(M) | !isPrime(M) | N == N | N != N | N < N | N <= N | collide == true | collide == false;
    int N -> setsize(S) | setsizemod(S, M) | 0 | 1 | getData(M) ;
}

examples {
    int IEX  -> ??(4) - 8;
    PosInt PosIntEX-> genPosInt();
    ArrayList ALEX-> newArrayList() | add(ALEX, IEX);
    boolean BEX-> true | false;
}