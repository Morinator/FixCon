import com.google.common.graph.MutableGraph;
import de.umr.fixcon.graphFunctions.Objective;
import de.umr.fixcon.graphFunctions.StandardObjectives;

import java.io.IOException;

import static de.umr.core.Factory.endpointPairs_from_NetworkRepo;
import static de.umr.core.Factory.graph_from_endpointPairs;

public class TestClass {

    public static void main(String[] args) throws IOException {
        MutableGraph<Integer> g = graph_from_endpointPairs(endpointPairs_from_NetworkRepo(".//graph_files//p-hat1500-3.mtx"));

        Objective maxFunc = new Objective(StandardObjectives.maxDegree);
        System.out.println(maxFunc.apply(g));

        Objective minFunc = new Objective(StandardObjectives.minDegree);
        System.out.println(minFunc.apply(g));

        Objective boundedFunc = new Objective(StandardObjectives.isDegreeConstrained);
        System.out.println(boundedFunc.apply(g, 912, 1330));
    }
}