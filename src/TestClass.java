import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.io.IOException;

public class TestClass {

    public static void main(String[] args) throws IOException {
        MutableGraph<Integer> g = GraphBuilder.undirected().build();
        g.putEdge(1, 2);
        g.putEdge(2, 1);
        System.out.println(g.degree(1));
    }
}