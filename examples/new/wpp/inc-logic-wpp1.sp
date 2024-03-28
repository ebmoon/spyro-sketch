variables {
    int x;
    int y;
    int z_in;
    hidden int z_out;
    boolean post;
}

signatures {
    prog_post1(x, y, z_in, z_out, post);
}

language {
    boolean B0 -> B && post;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP | AP && AP && AP && AP && AP;
    boolean AP -> x % 2 == 0 | x % 2 != 0 | y % 2 == 0 | y % 2 != 0 | z_in == ??(8) | true;
}

examples {
    int IEX -> ??(8);
    boolean BEX -> ??(1);
}