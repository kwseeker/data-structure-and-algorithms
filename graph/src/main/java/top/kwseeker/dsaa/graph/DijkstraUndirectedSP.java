package top.kwseeker.dsaa.graph;

import java.util.Stack;

/**
 * 加权无向图最短路径 Dijkstra 算法 （SP: shortest path）
 */
public class DijkstraUndirectedSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Edge[] edgeTo;            // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex {@code s} to every
     * other vertex in the edge-weighted graph {@code G}.
     *
     * @param  G the edge-weighted digraph
     * @param  s the source vertex  起点顶点
     * @throws IllegalArgumentException if an edge weight is negative
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public DijkstraUndirectedSP(AdjacencyListWeightedGraph G, int s) {
        for (Edge e : G.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }

        distTo = new double[G.V()];
        edgeTo = new Edge[G.V()];

        validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();    //pq存储各顶点距离起点的排名，选择最近的顶点作为新的起点
            StdOut.println("delMin: " + v);
            for (Edge e : G.adj(v)) //选择一顶点深入，其实相当于DFS遍历
                relax(e, v);
        }

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {   //之前的路径一定不是最短路径，变更为这条更短的路径的距离
            StdOut.println("relax: [" + w + "]: " + distTo[w] +  "->" + (distTo[v] + e.weight()));
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);       //优先队列重新排名
            else
                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path between the source vertex {@code s} and
     * vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return the length of a shortest path between the source vertex {@code s} and
     *         the vertex {@code v}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns true if there is a path between the source vertex {@code s} and
     * vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return {@code true} if there is a path between the source vertex
     *         {@code s} to vertex {@code v}; {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path between the source vertex {@code s} and vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return a shortest path between the source vertex {@code s} and vertex {@code v};
     *         {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e = v-w:            distTo[w] <= distTo[v] + e.weight()
    // (ii) for all edge e = v-w on the SPT: distTo[w] == distTo[v] + e.weight()
    private boolean check(AdjacencyListWeightedGraph G, int s) {

        // check that edge weights are non-negative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    /**
     * Unit tests the {@code DijkstraUndirectedSP} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        //In in = new In(args[0]);
        //AdjacencyListWeightedGraph G = new AdjacencyListWeightedGraph(in);
        //int s = Integer.parseInt(args[1]);
        AdjacencyListWeightedGraph G = new AdjacencyListWeightedGraph(8);
        G.addEdge(new Edge(4, 5, 0.35));
        G.addEdge(new Edge(4, 7, 0.37));
        G.addEdge(new Edge(5, 7, 0.28));
        G.addEdge(new Edge(0, 7, 0.16));
        G.addEdge(new Edge(1, 5, 0.32));
        G.addEdge(new Edge(0, 4, 0.38));
        G.addEdge(new Edge(2, 3, 0.17));
        G.addEdge(new Edge(1, 7, 0.19));
        G.addEdge(new Edge(0, 2, 0.26));
        G.addEdge(new Edge(1, 2, 0.36));
        G.addEdge(new Edge(1, 3, 0.29));
        G.addEdge(new Edge(2, 7, 0.34));
        G.addEdge(new Edge(6, 2, 0.40));
        G.addEdge(new Edge(3, 6, 0.52));
        G.addEdge(new Edge(6, 0, 0.58));
        G.addEdge(new Edge(6, 4, 0.93));
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
