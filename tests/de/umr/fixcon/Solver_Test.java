package de.umr.fixcon;

import com.google.common.graph.MutableGraph;
import de.umr.core.Factory;
import de.umr.fixcon.graphFunctions.GraphFunction;
import de.umr.fixcon.graphFunctions.standardFunctions.*;
import de.umr.fixcon.wrappers.Problem;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Solver_Test {

    private double generateValue(String path, GraphFunction fu, int size, int... args) throws IOException {
        MutableGraph<Integer> g = Factory.graphFromNetworkRepo(path);
        Solver solver = new Solver(new Problem(g, size, fu, args));
        return solver.getSolution().getValue();
    }

    @Test
    void edgeCount_4_dmela() throws IOException {   //49
        assertEquals(6, generateValue(".//graph_files//bio-dmela.mtx", new EdgeCount(), 4));
    }

    @Test
    void edgeCount_6_infPower() throws IOException {    //4.45
        assertEquals(15, generateValue(".//graph_files//inf-power.mtx", new EdgeCount(), 6));
    }

    @Test
    void minDegree_6_infPower() throws IOException {    //5.84
        assertEquals(5, generateValue(".//graph_files//inf-power.mtx", new MinDegree(), 6));
    }

    @Test
    void negMaxDegree_6_infPower() throws IOException { //0.0
        assertEquals(-2, generateValue(".//graph_files//inf-power.mtx", new NegMaxDegree(), 5));
    }

    @Test
    void isTree_9_infPower() throws IOException {       //0.0
        assertEquals(1, generateValue(".//graph_files//inf-power.mtx", new IsTree(), 9));
    }

    @Test
    void edgeCount_5_usAir() throws IOException {       //0.08
        assertEquals(6, generateValue(".//graph_files//inf-USAir97.mtx", new EdgeCount(), 4));
    }

    @Test
    void degreeConstrained3_5_size5_brightkite() throws IOException {       //7.7
        assertEquals(1, generateValue(".//graph_files//soc-brightkite.mtx", new IsDegreeConstrained(), 7, 3, 5));
    }
}