//@Description 

variables {
    list l1;
    list lout1;
    list l2;
    list lout2;
}

signatures {
    reverse(l1, lout1);
    reverse(l2, lout2);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> is_empty(L) | !is_empty(L) 
                | equal_list(L, L) | !equal_list(L, L);
    int S -> len(L) | 0 ;
    list L -> l1 | l2 | lout1 | lout2 ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    list LEX -> nil() | cons(IEX, LEX);
}
