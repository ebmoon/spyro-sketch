variables {
    Strategy P0;
    hidden Strategy P1;
    boolean winner;
}

signatures {
    prog(P0, P1, winner);
}

language {
    boolean B -> R | !G || R;
    boolean G -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> decision(P0, ??(1) + ??(1), ??(1));
    boolean R -> winner == 0 | winner == 1;
}

examples {
    boolean BEX -> ??(1);
    Strategy StrategyEX -> genStrategy();
}