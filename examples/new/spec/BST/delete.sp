//@Description Binary tree

var {
    tree_node bst;
    int target;
    tree_node bst_out;
}

relation {
    bst_delete(bst, target, bst_out);
}

generator {
    boolean AP -> compare(S, S + ??(1))
                | tree_equal(T, T) | !tree_equal(T, T)
                | is_empty(T) | !is_empty(T)
                | forall((x) -> compare(x, I), T)
                | exists((x) -> compare(x, I), T);
    int I -> target;
    int S -> tree_size(T) | 0;
    tree_node T -> bst | bst_out;
}

example {
    int -> ??(2) | -1 * ??(2) ;
    tree_node(4) -> bst_empty() | bst_insert(tree_node, int);
}

