//@Description Binary tree

variables {
    tree_node empty_out;
}

signatures {
    bst_empty(empty_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> S < S + ??(1) | S > S + ??(1)
                | S <= S + ??(1) | S >= S + ??(1)
                | S == S + ??(1) | S != S + ??(1)
                | tree_equal(T, T) | !tree_equal(T, T)
                | is_empty(T) | !is_empty(T);
    int S -> tree_size(T) | 0;
    tree_node T -> empty_out;
}

examples {
    int IEX -> ??(2) | -1 * ??(2) ;
    tree_node TEX -> bst_empty() | bst_insert(TEX, IEX);
}
