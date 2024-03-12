//@Description Binary tree

variables {
    tree_node empty_out;
}

signatures {
    empty(empty_out);
}

language {
    boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> S < S + ??(1) | S > S + ??(1)
                | S <= S + ??(1) | S >= S + ??(1)
                | S == S + ??(1) | S != S + ??(1)
                | is_empty(T) | !is_empty(T)
                | tree_equal(T, T) | !tree_equal(T, T);
    int S -> tree_size(T) | 0;
    tree_node T -> empty_out;
}

examples {
    int IEX -> ??(2) | -1 * ??(2) ;
    tree_node TEX -> empty() | branch(IEX, TEX, TEX);
}