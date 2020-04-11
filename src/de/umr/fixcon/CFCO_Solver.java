package de.umr.fixcon;

import de.umr.fixcon.itarators.SubgraphMutator;
import de.umr.fixcon.wrappers.CFCO_Problem;
import de.umr.fixcon.wrappers.Solution;

import java.util.HashSet;
import java.util.Set;

public class CFCO_Solver {
    final Solution solution;
    private final CFCO_Problem problem;
    private final SubgraphMutator subgraphMutator;
    private final double globalOptimum;

    public CFCO_Solver(final CFCO_Problem problem) {
        this.problem = problem;
        subgraphMutator = new SubgraphMutator(problem.graph, problem.subgraphSize);
        globalOptimum = problem.function.optimum(problem.subgraphSize);
        solution = new Solution(problem.graph);
    }

    public Solution getSolution() {
        for (; notOptimalNorExhausted(); subgraphMutator.mutate()) {
            if (currentIsBetter())
                solution.update(copyOfSubgraphVertices(), valueOfSubgraph());
        }
        return solution;
    }

    private boolean notOptimalNorExhausted() {
        return solution.getValue() < globalOptimum && subgraphMutator.isValid();
    }

    private double valueOfSubgraph() {
        return problem.function.apply(subgraphMutator.current(), problem.parameters);
    }

    private boolean currentIsBetter() {
        return valueOfSubgraph() > solution.getValue();
    }

    private Set<Integer> copyOfSubgraphVertices() {
        return new HashSet<>(subgraphMutator.current().nodes());
    }
}