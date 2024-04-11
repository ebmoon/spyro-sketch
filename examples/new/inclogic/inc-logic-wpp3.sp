variables {
    int x;
    hidden boolean nd;
    hidden boolean ok;
    boolean post
}

signatures {
    foo_post1(x, nd, ok, post);
}

language {
    boolean B0 -> B && post;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> x % 2 == 0 | x % 2 != 0 | x == ??(4) | true;
}

examples {
    int IEX -> ??(4);
    boolean BEX -> ??(1);
}