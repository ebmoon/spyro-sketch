variables {
    int amountA;
    int amountB;
    hidden RandomBits h;
    boolean deadlock;
    boolean ok;
}

signatures {
    scheduler(amountA, amountB, h, ok, deadlock);
}

language {
    boolean B0 -> ok && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> N <= N | N == N | deadlock == 0 | deadlock == 1;
    int N -> amountA | amountB | ??(3);
}

examples {
    int IEX -> ??(3);
    boolean BEX -> true | false;
    RandomBits RandomBitsEX-> genRandomBits();
}


