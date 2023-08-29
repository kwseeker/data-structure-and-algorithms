package top.kwseeker.dsaa.graph;

import org.junit.Test;

import java.util.Iterator;

public class AdjacencyMatrixWeightedGraphTest {

    @Test
    public void testBasic() {
        AdjacencyMatrixWeightedGraph<Integer> graph = new AdjacencyMatrixWeightedGraph<>();
        //这里输入的图是《大话数据结构》图7-7-2， 9个顶点16条边
        //输入所有顶点
        graph.putVex(0);
        graph.putVex(1);
        graph.putVex(2);
        graph.putVex(3);
        graph.putVex(4);
        graph.putVex(5);
        graph.putVex(6);
        graph.putVex(7);
        graph.putVex(8);
        //输入所有边
        graph.putArc(0, 1, 1);
        graph.putArc(0, 2, 5);
        graph.putArc(1, 2, 3);
        graph.putArc(1, 3, 7);
        graph.putArc(1, 4, 5);
        graph.putArc(2, 4, 1);
        graph.putArc(2, 5, 7);
        graph.putArc(3, 4, 2);
        graph.putArc(4, 5, 3);
        graph.putArc(3, 6, 3);
        graph.putArc(4, 6, 6);
        graph.putArc(4, 7, 9);
        graph.putArc(5, 7, 5);
        graph.putArc(6, 7, 2);
        graph.putArc(6, 7, 2);
        graph.putArc(6, 8, 7);
        graph.putArc(7, 8, 4);

        graph.printGraph();

        System.out.println("----------------------");
        Iterator<AdjacencyMatrixWeightedGraph.Vertex<Integer>> iterator = graph.iterator(2);
        System.out.print("与V2相邻的顶点: ");
        while(iterator.hasNext()) {
            System.out.printf(iterator.next().data + "\t");
        }
        System.out.println();

        System.out.println("----------------------");
        int startVexIdx = 0;
        graph.dfsTraverse(startVexIdx);
        startVexIdx = 2;
        graph.dfsTraverse(startVexIdx);

        System.out.println("----------------------");
        startVexIdx = 0;
        graph.bfsTraverse(startVexIdx);
    }

    /**
     * 0	1	1	1	1	0	0	0	0
     * 1	0	0	1	1	0	1	0	0
     * 1	0	0	0	1	1	0	0	0
     * 1	1	0	0	0	1	0	1	0
     * 1	1	1	0	0	0	0	1	0
     * 0	0	1	1	0	0	1	1	0
     * 0	1	0	0	0	1	0	1	0
     * 0	0	0	1	1	1	1	0	0
     * 0	0	0	0	0	0	0	0	0
     * ----------------------
     * DFS start 0: 0 1 3 5 2 4 7 6
     * ----------------------
     * BFS start 0: 0 1 2 3 4 6 5 7
     */
    @Test
    public void testDFSAndBFS() {
        AdjacencyMatrixWeightedGraph<Integer> graph = new AdjacencyMatrixWeightedGraph<>();
        //输入所有顶点
        graph.putVex(0);
        graph.putVex(1);
        graph.putVex(2);
        graph.putVex(3);
        graph.putVex(4);
        graph.putVex(5);
        graph.putVex(6);
        graph.putVex(7);
        //输入所有边
        graph.putArc(0, 1, 1);
        graph.putArc(0, 2, 1);
        graph.putArc(0, 3, 1);
        graph.putArc(0, 4, 1);
        graph.putArc(1, 0, 1);
        graph.putArc(1, 3, 1);
        graph.putArc(1, 6, 1);
        graph.putArc(2, 0, 1);
        graph.putArc(2, 4, 1);
        graph.putArc(2, 5, 1);
        graph.putArc(3, 0, 1);
        graph.putArc(3, 1, 1);
        graph.putArc(3, 5, 1);
        graph.putArc(3, 7, 1);
        graph.putArc(4, 1, 1);
        graph.putArc(4, 2, 1);
        graph.putArc(4, 7, 1);
        graph.putArc(5, 2, 1);
        graph.putArc(5, 3, 1);
        graph.putArc(5, 6, 1);
        graph.putArc(5, 7, 1);
        graph.putArc(6, 1, 1);
        graph.putArc(6, 5, 1);
        graph.putArc(6, 7, 1);
        graph.putArc(7, 3, 1);
        graph.putArc(7, 4, 1);
        graph.putArc(7, 5, 1);
        graph.putArc(7, 6, 1);

        graph.printGraph();

        System.out.println("----------------------");
        int startVexIdx = 0;
        graph.dfsTraverse(startVexIdx);

        System.out.println("----------------------");
        graph.bfsTraverse(startVexIdx);
    }
}