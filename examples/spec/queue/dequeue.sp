variables {
    queue q;
    int val_out;
    queue q_out;
}

signatures {
    dequeue(q, val_out, q_out);
}

language {
    boolean AP -> is_empty_list(L) | !is_empty_list(L)
                | equal_list(L, L) | !equal_list(L, L);
    int I -> val_out;
    list L -> AL | cons(I, AL) | snoc(AL, I);
    list AL -> queue2list(Q);
    queue Q -> q | q_out;
}

examples {
    int IEX -> ??(3) | -1 * ??(3) ;
    int IDUMMY -> 0;
    queue QEX -> QEX2 | enqueue(QEX2, IEX) | dequeue(QEX2, IDUMMY);
    queue QEX2 -> QEX1 | enqueue(QEX1, IEX) | dequeue(QEX1, IDUMMY);
    queue QEX1 -> QEX0 | enqueue(QEX0, IEX) | dequeue(QEX1, IDUMMY);
    queue QEX0 -> empty_queue();
}
