import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.Iterator;

public class MainTestClass {

    public static void main(String[] args) {

        MutableGraph<Integer> g = GraphBuilder.undirected().nodeOrder(ElementOrder.sorted((x, y) -> -42)).build();
        g.putEdge(1, 2);
        g.putEdge(3, 4);
        Iterator<Integer> int_iter = g.nodes().iterator();
        while (int_iter.hasNext()) {
            System.out.println(int_iter.next());
        }
    }
}