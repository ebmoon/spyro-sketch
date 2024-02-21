variables {
    int amountA;
    int amountB;
    hidden RandomBits h;
    boolean deadlock;
}

signatures {
    scheduler(amountA, amountB, h, deadlock);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> N <= N | N == N | deadlock == 0 | deadlock == 1;
    int N -> amountA | amountB | ??(2);
}

examples {
    int IEX -> ??(2);
    boolean BEX -> true | false;
    RandomBits RandomBitsEX-> genRandomBits();
}


