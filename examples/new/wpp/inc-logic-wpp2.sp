variables {
    hidden int x;
    int y;
    hidden int z;
    boolean post;
}

signatures {
    client_post1(x, y, z, post);
}

language {
    boolean B0 -> B && post;
    boolean B -> false | AP | AP && AP | AP && AP && AP ;
    boolean AP -> N == N | N < N | N <= N;
    int N ->  y | ??(6);
}

examples {
    int IEX -> ??(6);
    boolean BEX -> ??(1);
}