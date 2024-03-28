// M = 12

variables {
    hidden int x <- IEX;
    int a <- IEX;
    int b <- IEX;
    int o <- IEX;
}

signatures {
    fun_M8(a, b, x, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> AP1 | !AP1;
    boolean AP1 -> N == N | gcd(V, 8) % C == 0 | gcd(V, 8) == C;
    int N -> V | C;
    int V -> a | b | o;
    int C -> 0 | 1 | 2 | 4 | 8;
}

examples {
    int IEX -> ??(3);
    boolean BEX-> true | false;
}