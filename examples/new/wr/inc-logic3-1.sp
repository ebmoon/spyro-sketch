variables {
    int x;
    hidden boolean nd;
    boolean ok;
}

signatures {
    flaky_client(x, nd, ok);
}

language {
    boolean B0 -> O && B;
    boolean O -> ok | !ok;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> x % 2 == 0 | x % 2 != 0 | x == ??(4) | true;
}

examples {
    int IEX -> ??(4);
    boolean BEX -> ??(1);
}