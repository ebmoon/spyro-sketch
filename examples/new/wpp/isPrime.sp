variables {
    int x;
    hidden boolean b1;
    hidden boolean b2;
    boolean correct;
}

signatures {
    is_prime_buggy(x, b1);
    is_prime_correct(x, b2);
    eq(b1, b2, correct);
}

language {
    boolean B0 -> (correct == false) && B;
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> x == genInt();
}

examples {
    int IEX -> ??(5);
    boolean BEX -> ??(1);
}