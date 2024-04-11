//@Description

variables {
    int n;
    int val;
    list lout;
}

signatures {
    replicate(n, val, lout);
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
    int I -> val ;
    int S -> len(L) | n | 0 ;
    list L -> lout ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}
