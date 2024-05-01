# README

Aspire is a general framework for synthesizing provably sound and most precise specifications of given program. Aspire allows user to provide a DSL $\mathcal L$ to specify the syntax of specifications. 

In terms of precision, Aspire can synthesize both **strongest** overapproximation (a.k.a. $\mathcal L$-consequences) and **weakest** underapproxiamtion (a.k.a. $\mathcal L$-implicants) of the program behaviors. Moreover, Aspire supports non-deterministic choices in the program, which enables the reasoning of various kinds of programs, e.g. concurrent programs.

Aspire is built on Sketch synthesizer.

## Setup

The artifact requires dependencies if you try to run on your local machine

* Maven (version >= 3.9.6)
* [Sketch](https://people.csail.mit.edu/asolar/) (version >= 1.7.6)

Run following command in the directory with sketch jar file

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

Aspire takes 2 files as input (typically `.sp` and `.sk`). The `.sp` file declares the signature of the programs, the DSL $\mathcal L$. The example domains, and the `.sk` file provides implementations for function symbols used in the `.sp` file.

Take `examples/toy/ex1` as an example, it concerns a program that takes an integer as input and non-deterministically add an even number to it. Formally, the program is defined as $x_{out} = f(x,\mathrm h)$, where $f(x,\mathrm h) := x + 2\mathrm h$. Here the integer $\mathrm h$ is regarded as a nondeterministic choice.

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
The `variables` part declares all variables used in the `signatures` part. The keyword `hidden` indicates that `h` is treated as an existential quantified variable (i.e. nondeterministic choice). 

The `signatures` part defines the signatures of target functions. The last argument of the function is interpreted as output. Together with `variables`, it defines the query $\exists h.~x_{out} = f(x, h)$. The goal of Aspire is to synthesize a strongest overapproximating specification $\varphi_{over}(x, x_{out})$ that is implied by the query, or a weakest underapproximating specification $\varphi_{under}(x, x_{out})$ that imples the query.

The `language` part provides the DSL $\mathcal L$ for the property. In the case of overapproximation/underapproximation, each conjunct/disjunct is expressed in the grammar rooted in the top predicate. In particular, existential quantified variables are **NOT** allowed to occured in $\mathcal L$.

The `examples` defines the example domains for each types. `??(n)` denotes arbitrary integer from $0$ to $2^n-1$.

#### `.sk` input file

<!-- In the `.sk` file, the user should provide implementation of function symbols  -->

Here is the `.sk` file of `ex1`

```
void f(int x, int h, ref int xout) {
    xout = x + 2 * h;
}
```
Note that the function `f` is defined in a way that return variable is provided as an reference argument at the end of the argument lists, instead of normal style `int f(int x, int h)`. This is for compatability with Sketch synthesizer. The implemantion of all function symbols used in `.sp` files should be provided in this style. However, auxilary functions only used in `.sk` files do not need to follow this requirement.

### Understanding output format  

The synthesize properties are given as a code that returns a Boolean value, where the value is stored in the variable out. It means that the value stored in out must always be true.

For our example, if `--synth-over` is specified, Aspire will return the following 2 conjuncts:
```
Property 0
out = (xout - x) != 1

Property 1
out = (x - xout) <= 0
```

Likewise, Aspire returns the following 2 disjuncts when `--synth-under` is specified:
```
Property 0
out = (x - xout) == 0

Property 1
out = (xout - x) == 2
```

### Advanced Features

#### Query with multiple signatures

#### User-defined datatypes

#### Specify example domains for each variables individually

