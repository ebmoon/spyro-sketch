//@Description

variables {
    list l;
    int idx;
    int val;
}

signatures {
    ith(l, idx, val);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> S < S + ??(1) | S > S + ??(1) | S <= S + ??(1)
                | S >= S + ??(1) | S == S + ??(1) | S != S + ??(1)
                | is_empty(L) | !is_empty(L)
                | equal_list(L, L) | !equal_list(L, L)
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
    int I -> val ;
    int S -> len(L) | idx | 0 ;
    list L -> l ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}