//@Description Binary tree

variables {
    int val;
    tree_node t_left;
    tree_node t_right;
    tree_node t_out;

    tree_node left_in;
    tree_node left_out;
}

signatures {
    branch(val, t_left, t_right, t_out);
    left(left_in, left_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty(T) | !is_empty(T)
                | tree_equal(T, T) | !tree_equal(T, T);
    tree_node T -> t_left | t_right | t_out | left_in | left_out;
}

examples {
    int IEX -> ??(2) | -1 * ??(2) ;
    tree_node TEX -> empty() | branch(IEX, TEX, TEX);
}