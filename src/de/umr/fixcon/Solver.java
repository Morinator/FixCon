package de.umr.fixcon;

import com.google.common.graph.Graphs;

public class Solver {
    Problem problem;
    SubIter iter;
    double globalOptimum;

    public Solver(Problem problem) {
        this.problem = problem;
        iter = new SubIter(problem.graph, problem.subgraphSize);
        globalOptimum = problem.function.optimum(problem.subgraphSize, problem.parameters);
    }

    public Solution getSolution() {
        Solution solution = new Solution(null, Integer.MIN_VALUE);
        while (solution.value < globalOptimum && iter.isValid()) {
            if (problem.function.apply(iter.current(), problem.parameters) > solution.value) {
                solution = new Solution(Graphs.copyOf(iter.current()), problem.function.apply(iter.current(), problem.parameters));
            }
            iter.mutate();
        }
        return solution;
    }
}
