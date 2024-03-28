//@Description Toy benchmarks to show complex recursive generators.

variables {
    int x;
    int o;
}

signatures {
    abs(x, o);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> C * x + C * o + C < 0
     | C * x + C * o + C > 0
     | C * x + C * o + C <= 0
     | C * x + C * o + C >= 0
     | C * x + C * o + C == 0
     | C * x + C * o + C != 0;
    int C -> ??(3) - 3 ;
}

examples {
    int IEX -> ??(5) | -1 * ??(5) ;
}

