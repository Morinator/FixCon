# FixCon

## Introduction
This project is a Generic Solver for Fixed-Cardinality Subgraph Problems.  

This means that for a given target-function *f* that maps graphs to the real numbers, a fixed natural number *k* and an undirected graph *G*, it finds a connected subgraph of *G* with *k* vertices that maximizes the target-function *f*.  
As *f* may also be an indicator-function for a desired property of the subgraph (for example being a tree, containing no triangles or being n-regular), the question of existance of subgraphs with this property can also be answered.  

This is project of the [Algorithmics Research Group](https://www.uni-marburg.de/en/fb12/research-groups/algorith) of the Philipps-Universität Marburg.  

The paper the main algorithm is based off can be found [here](https://www.uni-marburg.de/de/fb12/arbeitsgruppen/algorith/paper/alenex20-de.umr.fixcon.pdf).

