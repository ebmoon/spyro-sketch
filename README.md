# README

Aspire is a general framework for synthesizing provably sound and most precise specifications of given program. Aspire allows user to provide a DSL $\mathcal L$ to specify the syntax of specifications. 

In terms of precision, Aspire can synthesize both **strongest** overapproximation and **weakest** underapproxiamtion of the program behaviors. Moreover, Aspire supports non-deterministic choices in the program, which enables the reasoning of various kinds of programs, e.g. concurrent programs.

Aspire is built on Sketch synthesizer.

## Build the Project

## Running Aspire

### Understanding input format

Aspire takes 2 files as input (typically `.sp` and `.sk`). The `.sp` file declares the signature of the programs, the DSL $\mathcal L$. The example domains, and the `.sk` file provides implementations for function symbols used in the `.sp` file.
### Understanding output format  
