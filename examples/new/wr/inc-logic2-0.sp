variables {
    int x;
    int y;
    hidden int z;
}

signatures {
    client(x, y, z);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP ;
    boolean AP -> N == N;
    int N -> x | y | ??(6);
}

examples {
    int IEX -> ??(6);
}