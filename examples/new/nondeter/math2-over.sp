// M = 12

variables {
    hidden int x <- IEX;
    int a <- IEX;
    int b <- IEX;
    int o <- IEX;
}

signatures {
    fun_M12(a, b, x, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> AP1 | !AP1;
    boolean AP1 -> N == N | gcd(V, 12) % C == 0 | gcd(V, 12) == C;
    int N -> V | C;
    int V -> a | b | o;
    int C -> 0 | 1 | 2 | 3 | 6 | 12;
}

examples {
    int IEX -> ??(3) | 8 + ??(2);
    boolean BEX-> true | false;
}