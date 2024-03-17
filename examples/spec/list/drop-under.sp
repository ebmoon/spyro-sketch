//@Description 

variables {
    list l;
    int n;
    list lout;
}

signatures {
    drop(l, n, lout);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> S == S + S + ??(1) | S != S + S + ??(1) | S <= S + S + ??(1)
                | S >= S + S + ??(1) | S < S + S + ??(1) | S > S + S + ??(1)
                | is_empty(L) | !is_empty(L) 
                | equal_list(L, L) | !equal_list(L, L);
    int S -> len(L) | n | 0 ;
    list L -> l | lout ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}

