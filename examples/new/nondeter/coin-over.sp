variables {
    int a <- IEX1;
    int b <- IEX1;
    hidden int x <- IEX2;
    hidden int y <- IEX2;
    int r <- IEX2;
}

signatures {
    coin(a, b, x, y, r);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> AP1 | !AP1;
    boolean AP1 -> gcd(a, b) == 1 | gcd(a, b) > 1 | r > N ;
    int N -> N2 | N2 + N2 | N2 + N2 + N2 | N2 - N2 | N2 + N2 - N2 | N2 - N2 - N2;
    int N2 -> N3 | N3 * N3;
    int N3 -> a | b | 1 | 0;
}

examples {
    int IEX1 -> ??(3) + 1;
    int IEX2 -> ??(5);
    boolean BEX -> ??(1);
}