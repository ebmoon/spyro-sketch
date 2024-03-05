//@Description Sketch to reverse a list.

variables {
    stack empty_out;
}

signatures {
    empty(empty_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty(ST) | !is_empty(ST)
                 | S < S + ??(1) | S <= S + ??(1)
                 | S > S + ??(1) | S >= S + ??(1)
                 | S == S + ??(1) | S != S + ??(1);
    int S -> stack_len(ST) | 0;
    stack ST -> empty_out;
}


examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    stack SEX -> empty() | push(SEX, IEX);
}