variables {
    int M <- posIEX;
    hidden int x <- IEX;
    int a <- IEX;
    int y <- IEX;
}

signatures {
    modmul(a, x, M, y);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP | AP && AP && AP && AP && AP;
    boolean AP -> isPrime(M) | !isPrime(M) | N == N | N != N | N < N | N <= N ;
    int N -> a | y | M | 0 | -M ;
}

examples {
    int IEX -> ??(4) - 8;
    int posIEX-> ??(3) + 2;
    boolean BEX-> true | false;
}