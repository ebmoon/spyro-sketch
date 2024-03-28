variables {
    Strategy P0;
    hidden Strategy P1;
    boolean winner;
}

signatures {
    prog(P0, P1, winner);
}

language {
    boolean B -> L && R;
    boolean L -> false | true | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP ;
    boolean R -> true | winner == 0 | winner == 1;
    boolean AP -> decision(P0, ??(1) + ??(1), ??(1)) ;
}

examples {
    boolean BEX -> ??(1);
    Strategy StrategyEX -> genStrategy();
}