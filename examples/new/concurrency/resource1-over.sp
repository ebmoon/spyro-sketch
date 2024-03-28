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
    boolean AP -> N <= N | N == N | N != N | N < N | deadlock | !deadlock;
    int N -> amountA | amountB | ??(3);
}

examples {
    int IEX -> ??(2);
    boolean BEX -> true | false;
    RandomBits RandomBitsEX-> genRandomBits();
}


