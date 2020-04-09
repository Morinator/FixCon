package de.umr.fixcon;

import java.util.HashSet;

public class Solver {
    private final Problem problem;
    private final SubIter subIter;
    private final double globalOptimum;
    Solution solution;

    public Solver(Problem problem) {
        this.problem = problem;
        subIter = new SubIter(problem.graph, problem.subgraphSize);
        globalOptimum = problem.function.optimum(problem.subgraphSize, problem.parameters);
    }

    public Solution getSolution() {
        solution = new Solution(problem.graph);
        while (solutionNotOptimal()) {
            if (valueCurrentSubgraph() > solution.getValue()) {
                System.out.println("update solution " + valueCurrentSubgraph());
                solution.update(new HashSet<>(subIter.current().nodes()), valueCurrentSubgraph());
            }
            subIter.mutate();
        }
        return solution;
    }

    private double valueCurrentSubgraph() {
        return problem.function.apply(subIter.current(), problem.parameters);
    }

    private boolean solutionNotOptimal() {
        return solution.getValue() < globalOptimum && subIter.isValid();
    }
}
