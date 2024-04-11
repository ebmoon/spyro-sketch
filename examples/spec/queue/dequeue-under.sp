//@Description Sketch to reverse a list.

variables {
    queue q;
    int val_out;
    queue q_out;
}

signatures {
    dequeue(q, val_out, q_out);
}

language {
    boolean B -> false | AP | AP && AP | AP && AP && AP;
    boolean AP -> is_empty_list(L) | !is_empty_list(L)
                | equal_list(L, L) | !equal_list(L, L);
    int I -> val_out;
    list L -> AL | cons(I, AL) | snoc(AL, I);
    list AL -> queue2list(Q);
    queue Q -> q | q_out;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    queue QEX -> empty_queue() | enqueue(QEX, IEX) | dequeue(QEX, IEX) ;
    list LEX -> nil() | cons(IEX, LEX);
}
