package top.kwseeker.dsaa.graph;

import org.junit.Test;

public class DijkstraUndirectedSPTest {

    /**
     * 需要注释掉负值检查
     */
    @Test
    public void testUnSupportNegativeEdge() {
        AdjacencyListWeightedGraph G = new AdjacencyListWeightedGraph(3);
        G.addEdge(new Edge(0, 1, 0.4));
        G.addEdge(new Edge(1, 2, -0.3));
        G.addEdge(new Edge(0, 2, 0.5));
        int s = 0;

        // compute shortest paths
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, s);

        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (Edge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }
}
