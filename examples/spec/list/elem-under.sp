//@Description need inline bnd 10

variables {
    list l;
    int val;
    boolean elem_out;
}

signatures {
    elem(l, val, elem_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty(L) | !is_empty(L)
                | elem_out | !elem_out
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
    int I -> val ;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    boolean BEX -> ?? ;
    list LEX -> nil() | cons(IEX, LEX);
}
