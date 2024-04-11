variables {
    int x;
    int y;
    hidden int z_in;
    int z_out;
    boolean pre;
}

signatures {
    prog_pre1(x, y, z_in, z_out, pre);
}

language {
    boolean B0 -> pre && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP | AP && AP && AP && AP && AP;
    boolean AP -> x % 2 == 0 | x % 2 != 0 | y % 2 == 0 | y % 2 != 0 | z_out == ??(8) | true;
}

examples {
    int IEX -> ??(8);
    boolean BEX -> ??(1);
}