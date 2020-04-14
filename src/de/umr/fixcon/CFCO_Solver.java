package de.umr.fixcon;

import de.umr.fixcon.itarators.SubIterator;
import de.umr.fixcon.wrappers.CFCO_Problem;
import de.umr.fixcon.wrappers.Solution;

import java.util.HashSet;
import java.util.Set;

public class CFCO_Solver {
    final Solution solution;
    private final CFCO_Problem problem;
    private final SubIterator subIterator;
    private final double globalOptimum;

    public CFCO_Solver(final CFCO_Problem problem) {
        this.problem = problem;
        subIterator = new SubIterator(problem.graph, problem.subgraphSize);
        globalOptimum = problem.function.optimum(problem.subgraphSize);
        solution = new Solution(problem.graph);
    }

    public Solution getSolution() {
        for (; notOptimalNorExhausted(); subIterator.mutate()) {
            if (currentIsBetter())
                solution.update(copyOfSubgraphVertices(), valueOfSubgraph());
        }
        System.out.println(subIterator.searchTreeCounter);
        System.out.println(subIterator.sizeKSubgraphCount);
        return solution;
    }

    public int getSearchTreeNodes() {
        return subIterator.searchTreeCounter;
    }

    private boolean notOptimalNorExhausted() {
        return solution.getValue() < globalOptimum && subIterator.isValid();
    }

    private double valueOfSubgraph() {
        return problem.function.apply(subIterator.current(), problem.parameters);
    }

    private boolean currentIsBetter() {
        return valueOfSubgraph() > solution.getValue();
    }

    private Set<Integer> copyOfSubgraphVertices() {
        return new HashSet<>(subIterator.current().nodes());
    }
}