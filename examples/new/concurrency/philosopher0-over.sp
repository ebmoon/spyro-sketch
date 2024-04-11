variables {
    Order ord;
    hidden RandomBits h;
    boolean deadlock;
}

signatures {
    controller(ord, h, deadlock);
}

language {
    boolean B0 -> !B || D;
    boolean D -> deadlock | !deadlock;
    boolean B -> false | true | AP | AP && AP | AP && AP && AP;
    boolean AP ->  LR(ord, N) | RL(ord, N);
    int N -> ??(2) + ??(1);
}

examples {
    Order OEX -> genOrder();
    RandomBits RBEX -> genRandomBits();
    boolean BEX -> ??(1);
}