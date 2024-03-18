// --bnd-unroll-amnt 16

variables {
    int amountA;
    int amountB;
    int amountC;
    hidden RandomBits h;
    boolean deadlock;
    boolean ok;
}

signatures {
    scheduler(amountA, amountB, amountC, h, ok, deadlock);
}

language {
    boolean B0 -> ok && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP;
    boolean AP -> N <= N | N == N | deadlock == 0 | deadlock == 1;
    int N -> amountA | amountB | amountC | ??(4);
}

examples {
    int IEX -> ??(4);
    boolean BEX -> true | false;
    RandomBits RandomBitsEX-> genRandomBits();
}


