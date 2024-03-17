//@Description

variables {
    list l;
    list lout;
}

signatures {
    stutter(l, lout);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty(L) | !is_empty(L)
                | equal_list(L, L) | !equal_list(L, L)
                | S < S + ??(1) | S <= S + ??(1)
                | S > S + ??(1) | S >= S + ??(1)
                | S == S + ??(1) | S != S + ??(1);
    int S -> len(L) | 0 ;
    list L -> l | lout ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}
