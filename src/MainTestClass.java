import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

public class MainTestClass {

    public static void main(String[] args) {
        MutableGraph<Integer> g = GraphBuilder.undirected().allowsSelfLoops(false).build();
        g.putEdge(1, 2);
        System.out.println(g.hasEdgeConnecting(1, 2));
    }
}
