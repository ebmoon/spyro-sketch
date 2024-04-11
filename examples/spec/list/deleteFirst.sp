//@Description delete the first occurence of the input value

variables {
    list l;
    int target;
    list lout;
}

signatures {
    delete(l, target, lout);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> is_empty(L) | !is_empty(L) 
                | equal_list(L, L) | !equal_list(L, L) 
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
    int I -> target ;
    int S -> len(L) | 0 ;
    list L -> l | lout ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}

