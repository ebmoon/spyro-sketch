variables {
    Order ord;
    hidden RandomBits h;
    boolean deadlock;
}

signatures {
    controller(ord, h, deadlock);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP ->  deadlock | !deadlock | LR(ord, N) | RL(ord, N);
    int N -> ??(2) + ??(1);
}

examples {
    Order OEX -> genOrder();
    RandomBits RBEX -> genRandomBits();
    boolean BEX -> ??(1);
}