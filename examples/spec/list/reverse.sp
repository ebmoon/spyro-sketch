// @Description Synthesize the property of list reverse function.
// Run with --inline-bnd-amnt 10 --num-atom-max 3

// Input and output variables
variables {
    list l;
    list lout;
}

// Target functions that we aim to synthesize specifications
// The last argument to the function is output
signatures {
    reverse(l, lout);
}

// The DSL for the specifictions.
// It uses the top predicate as a grammar for each disjunct.
// The maximum number of disjuncts are provided by the option "--num-atom-max"
// compare is macro for { == | != | <= | >= | < | > }
// ??(n) denotes arbitrary positive integer of n bits
// Provide only input arguments to function call
language {
    boolean AP -> is_empty(L) | !is_empty(L) 
                | equal_list(L, L) | !equal_list(L, L)
                | compare(S, S + ??(1));            
    int S -> len(L) | 0 ;
    list L -> l | lout ;
}

// recursive constructor for each type
// Provide only input arguments to function call
// integer is chosen from arbitrary positive or negative 3-bits integer
example {
    int -> ??(3) | -1 * ??(3) ;
    list -> nil() | cons(int, list);
}

