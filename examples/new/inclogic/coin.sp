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
    boolean B0 -> gcd(a, b) == 1 && B;
    boolean B -> false | AP ;
    boolean AP ->  r > N;
    int N -> N1 | N1 + N1 | N1 - N1;
    int N1 -> N2 | N2 + N2 | N2 - N2;
    int N2 -> a | b | a * b | a * a | b * b | 1 | 0;
}

examples {
    int IEX1 -> ??(3) + 1;
    int IEX2 -> ??(5);
    boolean BEX -> ??(1);
}