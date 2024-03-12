//@Description Sketch to reverse a list.

variables {
    stack s;
    stack s_out;
    int val_out;
}

signatures {
    pop(s, s_out, val_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty(ST) | !is_empty(ST)
                | stack_equal(ST, ST) | !stack_equal(ST, ST)
                | S < S + ??(1) | S <= S + ??(1)
                | S > S + ??(1) | S >= S + ??(1)
                | S == S + ??(1) | S != S + ??(1);
    int S -> stack_len(ST) | 0;
    stack ST -> s | s_out;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    stack SEX -> empty() | push(SEX, IEX);
}