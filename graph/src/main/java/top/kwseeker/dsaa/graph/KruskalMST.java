package top.kwseeker.dsaa.graph;

import java.util.Arrays;

public class KruskalMST {

    private Queue<Edge> mst;     // edges in the MST
    private double weight;       // total weight of MST

    //private boolean[] marked;    // marked[v] = true iff v on tree

    public KruskalMST(AdjacencyListWeightedGraph graph) {
        //获取所有边按权重排序
        mst = new Queue<>();
        //marked = new boolean[graph.E()];
        //Arrays.fill(marked, false);
        Edge[] edges = new Edge[graph.E()];
        int t = 0;
        for (Edge edge : graph.edges()) {
            edges[t++] = edge;
        }
        Arrays.sort(edges);

        UF uf = new UF(graph.V());  //并查集
        int edgeMaxSize = graph.V() -1;
        for (int i = 0; i < graph.E() && mst.size() < edgeMaxSize; i++) {
            int either = edges[i].either();
            int other = edges[i].other(either);
            if (uf.find(either) == uf.find(other)) {  //使用并查集判断是否已经连通，已经连通的话，再加入这条边就一定有环了，不能加
                continue;
            }
            uf.union(either, other);
            mst.enqueue(edges[i]);
            weight += edges[i].weight();
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
