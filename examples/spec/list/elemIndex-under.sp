//@Description need inline bnd 10

variables {
    list l;
    int val;
    int idx_out;
}

signatures {
    find(l, val, idx_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty(L) | !is_empty(L)
                | S < S + ??(1) | S > S + ??(1) | S <= S + ??(1)
                | S >= S + ??(1) | S == S + ??(1) | S != S + ??(1)
                | forall((x) -> (x == I), L)
                | forall((x) -> (x != I), L)
                | forall((x) -> (x >= I), L)
                | forall((x) -> (x <= I), L)
                | forall((x) -> (x < I), L)
                | forall((x) -> (x > I), L)
                | exists((x) -> (x == I), L)
                | exists((x) -> (x != I), L)
                | exists((x) -> (x >= I), L)
                | exists((x) -> (x <= I), L)
                | exists((x) -> (x < I), L)
                | exists((x) -> (x > I), L);
    list L -> l ;
    int S -> len(L) | 0 | -1 | idx_out ;
    int I -> val ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}
