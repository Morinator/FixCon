package benchmarks;

import com.google.common.graph.MutableGraph;
import de.umr.fixcon.CFCOSolver;
import de.umr.fixcon.graphFunctions.GraphFunction;
import de.umr.fixcon.graphFunctions.standardFunctions.*;
import de.umr.fixcon.wrappers.CFCO_Problem;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.umr.core.GraphFileReaderKt.graphFromNetworkRepo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CFCOSolver_Test {

    private double generateValue(String path, GraphFunction fu, int size, int... args) throws IOException {
        MutableGraph<Integer> g = graphFromNetworkRepo(path);
        CFCOSolver solver = new CFCOSolver(new CFCO_Problem(g, size, fu, args));
        return solver.getSolution().getValue();
    }

    @Test
    void edgeCount_4_dmela() throws IOException {   //1 vs. 49
        assertEquals(6, generateValue(".//graph_files//bio-dmela.mtx", new EdgeCountFunction(), 4));
    }

    @Test
    void edgeCount_6_infPower() throws IOException {    //4.45
        assertEquals(15, generateValue(".//graph_files//inf-power.mtx", new EdgeCountFunction(), 6));
    }

    @Test
    void minDegree_6_infPower() throws IOException {    //5.84
        assertEquals(5, generateValue(".//graph_files//inf-power.mtx", new MinDegreeFunction(), 6));
    }

    @Test
    void negMaxDegree_6_infPower() throws IOException { //0.0
        assertEquals(-2, generateValue(".//graph_files//inf-power.mtx", new NegMaxDegreeFunction(), 5));
    }

    @Test
    void isTree_9_infPower() throws IOException {       //0.0
        assertEquals(1, generateValue(".//graph_files//inf-power.mtx", new IsTreeFunction(), 9));
    }

    @Test
    void edgeCount_4_usAir() throws IOException {       //0.08
        assertEquals(6, generateValue(".//graph_files//inf-USAir97.mtx", new EdgeCountFunction(), 4));
    }

    @Test
    void degreeConstrained3_5_size7_brightkite() throws IOException {       //7.7
        assertEquals(1, generateValue(".//graph_files//soc-brightkite.mtx", new IsDegreeConstrainedFunction(), 7, 3, 5));
    }

    @Test
    void edgeCount_9_dolphins() throws IOException {       //332
        assertEquals(23, generateValue(".//graph_files//out.dolphins", new EdgeCountFunction(), 9));
    }

    @Test
    void minDegree_9_dolphins() throws IOException {       //45 vs. 458
        assertEquals(4, generateValue(".//graph_files//out.dolphins", new MinDegreeFunction(), 9));
    }

    @Test
    void edgeCount_10_euroRoad() throws IOException {       //64 vs. 461
        assertEquals(14, generateValue(".//graph_files//inf-euroroad.edges", new EdgeCountFunction(), 10));
    }
}