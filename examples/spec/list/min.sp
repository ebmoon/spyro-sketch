variables {
    list l;
    int min_out;
}

signatures {
    min(l, min_out);
}

language {
    boolean B -> true
                | AP
                | AP || AP
                | AP || AP || AP;
    boolean AP -> S <= S + ??(1) | S < S + ??(1)
                | S >= S + ??(1) | S > S + ??(1)
                | S == S + ??(1) | S != S + ??(1)
                | is_empty(L) | !is_empty(L)
                | forall((x) -> (x <= I), L)
                | forall((x) -> (x < I), L)
                | forall((x) -> (x >= I), L)
                | forall((x) -> (x > I), L)
                | forall((x) -> (x == I), L)
                | forall((x) -> (x != I), L)
                | exists((x) -> (x <= I), L)
                | exists((x) -> (x < I), L)
                | exists((x) -> (x >= I), L)
                | exists((x) -> (x > I), L)
                | exists((x) -> (x == I), L)
                | exists((x) -> (x != I), L);
    int S -> 0 | len(L) ;
    list L -> l ;
    int I -> min_out ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}