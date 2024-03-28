variables {
    int x;
    int y;
    hidden int z_in;
    int z_out;
    boolean ok;
}

signatures {
    prog(x, y, z_in, z_out, ok);
}

language {
    boolean B0 -> B && (ok == 1);
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP | AP && AP && AP && AP && AP;
    boolean AP -> x % 2 == 0 | x % 2 != 0 | y % 2 == 0 | y % 2 != 0 | z_out == ??(8) | true;
}

examples {
    int IEX -> ??(8);
    boolean BEX -> ??(1);
}