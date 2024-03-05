//@Description Sketch to reverse a list.

variables {
    stack s;
    int val;
    stack s_out;
}

signatures {
    push(s, val, s_out);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
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