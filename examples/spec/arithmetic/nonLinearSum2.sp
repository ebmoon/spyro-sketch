//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x;
    int o;
}

signatures {
    sum(x, o);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> C * x + C * o + C * x * x + C < 0
     | C * x + C * o + C * x * x + C > 0
     | C * x + C * o + C * x * x + C <= 0
     | C * x + C * o + C * x * x + C >= 0
     | C * x + C * o + C * x * x + C == 0
     | C * x + C * o + C * x * x + C != 0;
    int C -> ??(3) - 4  ;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}

