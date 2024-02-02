variables {
    int x;
    int y;
}

signatures {
    square(x, y);
}

language {
    boolean INEQ -> bvadd3(CX, CY, C) <= bvadd3(CX, CY, C);
    int CX -> bvmul(C, x);
    int CY -> bvmul(C, y);
    int C -> ??(4);
}

examples {
    int IEX -> ??(4);
}