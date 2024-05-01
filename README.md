# README

Aspire is a general framework for synthesizing provably sound and most precise specifications of the given program. Aspire allows users to provide a DSL $\mathcal L$ to specify the syntax of specifications. 

In terms of precision, Aspire can synthesize both **strongest** overapproximation (a.k.a. $\mathcal L$-consequences) and **weakest** underapproxmation (a.k.a. $\mathcal L$-implicants) of the program behaviors. Moreover, Aspire supports non-deterministic choices in the program, which enables the reasoning of various kinds of programs, e.g. concurrent programs.

Aspire is built on Sketch synthesizer.

## Setup

The artifact requires dependencies if you try to run it on your local machine

* Maven (version >= 3.9.6)
* [Sketch](https://people.csail.mit.edu/asolar/) (version >= 1.7.6)

Run the following command in the directory with sketch jar file

`mvn install:install-file -Dfile="./sketch-1.7.6-noarch.jar" -DgroupId="edu.mit.csail.sketch" -DartifactId="sketch-frontend" -Dversion="1.7.6" -Dpackaging="jar" -DgeneratePom="true"`

Then run the command `make jar` to generate a jar file.

Now you can run the tool by the provided script `spyro`, e.g.
```
./spyro examples/toy/ex1-over.sp examples/toy/ex1.sk --synth-over
```

### Useful flags

- `--synth-over`: Synthesize strongest $\mathcal L$-consequences.
- `--synth-under`: Synthesize weakest $\mathcal L$-implicants.
- `--bnd-inline-amnt`: The inline amount for recursions [default 5].
- `--bnd-unroll-amnt`: The unroll amount for loops. [default 8].
- `-V`: Print descriptive messages.
- `--debug-dump-sketch`: Save every call to the sketch synthesizer.

## Running Aspire

### Understanding input format

Aspire takes 2 files as input (typically `.sp` and `.sk`). The `.sp` file declares the signature of the programs, the DSL $\mathcal L$, and the example domains. The `.sk` file provides implementations for function symbols used in the `.sp` file.

Take `examples/toy/ex1` as an example, it concerns a program that takes an integer as input and non-deterministically adds an even number to it. Formally, the program is defined as $x_{out} = f(x,\mathrm h)$, where $f(x,\mathrm h) := x + 2\mathrm h$. Here the integer $\mathrm h$ is regarded as a nondeterministic choice.

To synthesize $\mathcal L$-consequences for `ex1`, one can run 
`./spyro examples/toy/ex1-over.sp examples/toy/ex1.sk --synth-over`

To synthesize $\mathcal L$-implicants, one can run 
`./spyro examples/toy/ex1-over.sp examples/toy/ex1.sk --synth-under`

#### `.sp` input file

Each `.sp` file should exactly contain four parts `variables`, `signatures`, `language`, `examples`. The `.sp` file of `ex1` is shown below

```
variables {
    int x;
    hidden int h;
    int xout;
}
signatures {
    f(x, h, xout);
}
language {
    boolean AP -> N < C | N == C | N <= C | N != C | N > C | N >= C;
    int N -> I | I + I | I - I;
    int I -> x | xout;
    int C -> 0 | 1 | 2;
}
examples {
    int IEX -> ??(4);
    boolean BEX -> ??(1);
}

```
The `variables` part declares all variables used in the `signatures` part. The keyword `hidden` indicates that `h` is treated as an existentially quantified variable (i.e. nondeterministic choice). 

The `signatures` part defines the signatures of target functions. The last argument of the function is interpreted as output. Together with `variables`, it defines the query $\exists h.~x_{out} = f(x, h)$. The goal of Aspire is to synthesize a strongest overapproximating specification $\varphi_{over}(x, x_{out})$ that is implied by the query, or a weakest underapproximating specification $\varphi_{under}(x, x_{out})$ that implies the query.

The `language` part provides the DSL $\mathcal L$ for the property. In the case of overapproximation/underapproximation, each conjunct/disjunct is expressed in the grammar rooted in the top predicate. In particular, existentially quantified variables are **NOT** allowed to occur in $\mathcal L$.

The `examples` part defines the example domains for each type. `??(n)` denotes an arbitrary integer from $0$ to $2^n-1$.

#### `.sk` input file

<!-- In the `.sk` file, the user should provide implementation of function symbols  -->

Here is the `.sk` file of `ex1`

```
void f(int x, int h, ref int xout) {
    xout = x + 2 * h;
}
```
Note that the function `f` is defined in a way that the return variable is provided as a reference argument at the end of the argument lists, instead of the normal style `int f(int x, int h)`. This is for compatibility with Sketch synthesizer. The implementation of all function symbols used in `.sp` files should be provided in this style. However, auxiliary functions only used in `.sk` files do not need to follow this requirement.

### Understanding output format  

The synthesized properties are given as a code that returns a Boolean value, where the value is stored in the variable out. It means that the value stored in out must always be true.

For our example, if `--synth-over` is specified, Aspire will return the $\mathcal L$-consequences that consist of the following 2 conjuncts:
```
Property 0
out = (xout - x) != 1

Property 1
out = (x - xout) <= 0
```

Likewise, Aspire returns the $\mathcal L$-implicants that consist of the following 2 disjuncts when `--synth-under` is specified:
```
Property 0
out = (x - xout) == 0

Property 1
out = (xout - x) == 2
```

### Advanced Features

#### Query with multiple signatures

The `signatures` part can consist of multiple functions, which will be interpreted as a conjunction of them. For example, if one wants to reason about `y = f(g(x))`, the `signatures` part can be written as follows.
```
signatures {
    g(x, t);
    f(t, y);
}
```

#### User-defined datatypes

Aspire allows user to define datatypes with their example generator. For example, to define a list of integers, one should first declare a struct in `.sk` file.
```
struct list {
    int hd;
	list tl;
}
```

After that, the example domains of the new type should be supplied in `examples` part of `.sp` file. In terms of list, one may use 2 constructors `nil` and `cons` to define the example domains.
```
examples {
    int IEX -> ??(3);
    list LEX -> nil() | cons(IEX, LEX);
}
```
The implementation of `nil` and `cons` should also be provided in `.sk` file.
```
void nil(ref list ret) {
    ret = null;
}

void cons(int hd, list tl, ref list ret) {
    ret = new list();
    ret.hd = hd;
    ret.tl = tl;
}
```

Finally, since Aspire assumes that an equal operator is defined for each datatype, one should provide an equality checker called `<TYPE_NAME>_equal` in `.sk` file. For example,
```
void list_equal(list l1, list l2, ref boolean ret) {
    if (l1 == null || l2 == null) {
        ret = l1 == l2;
    } else {
        equal_list(l1.tl, l2.tl, ret);
        ret = l1.hd == l2.hd && ret;
    }
}
```

#### Specify example domains for each variable individually

In fact, each nonterminal in `examples` part such as `IEX`, `LEX` defines a grammar that is rooted in that nonterminal. Such grammar is translated to an example generator by Aspire.

Aspire allows users to define multiple example generators for the same type, and each variable of this type should be accompanied by an example generator. For example, if there are 2 integer generators `IEX1` and `IEX2`, one can write the `variables` part like the following.
```
variables {
    int a <- IEX1;
    int b <- IEX2;
}
```
