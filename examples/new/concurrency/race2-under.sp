variables {
    AtomCons ac0;
    AtomCons ac1;
    hidden RandomBits h;
    boolean race;
}

signatures {
    controller(ac0, ac1, h, race);
}

language {
    boolean B0 -> B && O;
    boolean O -> race | !race;
    boolean B -> false | AP | AP && AP | AP && AP && AP | AP && AP && AP && AP | true;
    boolean AP -> atom(AC, L, R) | !atom(AC, L, R);
    int L -> 0 | 1;
    int R -> 1 | 2;
    AtomCons AC -> ac0 | ac1;
}

examples {
    boolean BEX -> ??(1);
    RandomBits RBEX -> genRandomBits();
    AtomCons ACEX -> genAtomCons();
}