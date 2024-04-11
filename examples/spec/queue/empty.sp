//@Description Sketch to reverse a list.

variables {
    queue empty_queue_out;
}

signatures {
    empty_queue(empty_queue_out);
}

language {
        boolean B -> true | AP | AP || AP | AP || AP || AP;
    boolean AP -> is_empty_list(L) | !is_empty_list(L)
                | equal_list(L, L) | !equal_list(L, L);
    int S -> list_len(L) | 0;
    list L -> queue2list(Q);
    queue Q -> empty_queue_out;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    queue QEX -> empty_queue() | enqueue(QEX, IEX) | dequeue(QEX, IEX) ;
    list LEX -> nil() | cons(IEX, LEX);
}