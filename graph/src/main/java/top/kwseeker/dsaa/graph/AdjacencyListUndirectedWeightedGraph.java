package top.kwseeker.dsaa.graph;

/**
 * 邻接表实现的无向加权图
 */
public class AdjacencyListUndirectedWeightedGraph {
    //最大顶点数量
    private static int MAX_VEX = 9;
    //代表无穷，没有路径可达就是无穷
    private static int INFINITY = 65535;
    //V0到目标最短路径下标的数组
    private int[] pathMatrix;
    //V0到各顶点最短路径的权值和
    private int[] shortPathTable;

    /**
     * Dijkstra 最短路径算法
     */
    public void shortestPathDijkstra() {

    }


}
