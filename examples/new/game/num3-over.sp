variables {
    Strategy P0;
    hidden Strategy P1;
    int init;
    boolean win;
}

signatures {
    prog(P0, P1, init, win);
}

language {
    boolean B -> R | !G || R;
    boolean G -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP | AP && AP && AP && AP && AP;
    boolean AP -> decision(P0, ??(2) + ??(1), ??(1)) | init == ??(2) + ??(1);
    boolean R -> win | !win;
}

examples {
    boolean BEX -> ??(1);
    int IEX -> ??(2) + ??(1);
    Strategy StrategyEX -> genStrategy();
}