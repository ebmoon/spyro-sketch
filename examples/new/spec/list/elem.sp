//@Description need inline bnd 10

var {
    list l;
    int val;
    boolean elem_out;
}

relation {
    elem(l, val, elem_out);
}

generator {
    boolean AP -> is_empty(L) | !is_empty(L)
                | elem_out | !elem_out
                | forall((x) -> compare(x, I), L)
                | exists((x) -> compare(x, I), L);
    list L -> l ;
    int I -> val ;
}

example {
    int -> ??(3) | -1 * ??(3) ;
    boolean -> ?? ;
    list -> nil() | cons(int, list);
}
