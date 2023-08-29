package top.kwseeker.dsaa.graph;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 普通迷宫：无向图
 */
public class UndirectedMazeTest {

    /**
     * 4 3 17 18 11 12 5 6 21 31 32 36 35 29 30 14 13 20 19 28 41 42 49 48 54 60 61 67 66 59 58 64 65
     * 这个结果可见DFS并不适合求解最短路线
     */
    @Test
    public void testDFSSoluteMaze() {
        AdjacencyMatrixWeightedGraph<Point> mazeGraph = new AdjacencyMatrixWeightedGraph<>(100, false);
        initData(mazeGraph);
        //mazeGraph.dfsTraverse(4);
        List<Integer> path = mazeGraph.depthFirstPath(4, 65);
        for (Integer idx : path) {
            System.out.print(idx + " ");
        }
    }

    @Test
    public void testBFSSoluteMaze() {
        AdjacencyMatrixWeightedGraph<Point> mazeGraph = new AdjacencyMatrixWeightedGraph<>(100, false);
        initData(mazeGraph);
        AdjacencyMatrixWeightedGraph<Point>.BreadthFirstPath breadthFirstPath = mazeGraph.breadthFirstPath(4);
        Stack<Integer> path = breadthFirstPath.pathTo(65);
        if (path == null) {
            return;
        }
        while(!path.isEmpty()) {
            System.out.print(path.pop() + " ");
        }
    }

    static class Point {
        int x;
        int y;
        int idx;

        public Point(int x, int y, int idx) {
            this.x = x;
            this.y = y;
            this.idx = idx;
        }

        @Override
        public String toString() {
            //return "(" + x + "," + y + ")";
            return String.valueOf(idx);
        }
    }

    private void initData(AdjacencyMatrixWeightedGraph<Point> mazeGraph) {
        mazeGraph.putVex(new Point(0, 0, 0));      //0
        mazeGraph.putVex(new Point(0, 1, 1));      //1
        mazeGraph.putVex(new Point(0, 2, 2));
        mazeGraph.putVex(new Point(0, 3, 3));
        mazeGraph.putVex(new Point(0, 4, 4));      //入口 index=4
        mazeGraph.putVex(new Point(0, 5, 5));
        mazeGraph.putVex(new Point(0, 8,6));
        mazeGraph.putVex(new Point(0, 9,7));
        mazeGraph.putVex(new Point(1, 0,8));
        mazeGraph.putVex(new Point(1, 1,9));
        mazeGraph.putVex(new Point(1, 2,10));
        mazeGraph.putVex(new Point(1, 4,11));
        mazeGraph.putVex(new Point(1, 5,12));
        mazeGraph.putVex(new Point(1, 6,13));
        mazeGraph.putVex(new Point(1, 7,14));
        mazeGraph.putVex(new Point(2, 0,15));
        mazeGraph.putVex(new Point(2, 2,16));
        mazeGraph.putVex(new Point(2, 3,17));
        mazeGraph.putVex(new Point(2, 4,18));
        mazeGraph.putVex(new Point(2, 5,19));
        mazeGraph.putVex(new Point(2, 6,20));
        mazeGraph.putVex(new Point(2, 8,21));
        mazeGraph.putVex(new Point(2, 9,22));
        mazeGraph.putVex(new Point(3, 0,23));
        mazeGraph.putVex(new Point(3, 1,24));
        mazeGraph.putVex(new Point(3, 2,25));
        mazeGraph.putVex(new Point(3, 3,26));
        mazeGraph.putVex(new Point(3, 4,27));
        mazeGraph.putVex(new Point(3, 5,28));
        mazeGraph.putVex(new Point(3, 6,29));
        mazeGraph.putVex(new Point(3, 7,30));
        mazeGraph.putVex(new Point(3, 8,31));
        mazeGraph.putVex(new Point(3, 9,32));
        mazeGraph.putVex(new Point(4, 1,33));
        mazeGraph.putVex(new Point(4, 2,34));
        mazeGraph.putVex(new Point(4, 6,35));
        mazeGraph.putVex(new Point(4, 9,36));
        mazeGraph.putVex(new Point(5, 0,37));
        mazeGraph.putVex(new Point(5, 1,38));
        mazeGraph.putVex(new Point(5, 3,39));
        mazeGraph.putVex(new Point(5, 4,40));
        mazeGraph.putVex(new Point(5, 5,41));
        mazeGraph.putVex(new Point(5, 9,42));
        mazeGraph.putVex(new Point(6, 0,43));
        mazeGraph.putVex(new Point(6, 4,44));
        mazeGraph.putVex(new Point(6, 5,45));
        mazeGraph.putVex(new Point(6, 6,46));
        mazeGraph.putVex(new Point(6, 7,47));
        mazeGraph.putVex(new Point(6, 8,48));
        mazeGraph.putVex(new Point(6, 9,49));
        mazeGraph.putVex(new Point(7, 3,50));
        mazeGraph.putVex(new Point(7, 5,51));
        mazeGraph.putVex(new Point(7, 6,52));
        mazeGraph.putVex(new Point(7, 7,53));
        mazeGraph.putVex(new Point(7, 8,54));
        mazeGraph.putVex(new Point(7, 9,55));
        mazeGraph.putVex(new Point(8, 0,56));
        mazeGraph.putVex(new Point(8, 1,57));
        mazeGraph.putVex(new Point(8, 3,58));
        mazeGraph.putVex(new Point(8, 7,59));
        mazeGraph.putVex(new Point(8, 8,60));
        mazeGraph.putVex(new Point(8, 9,61));
        mazeGraph.putVex(new Point(9, 0,62));
        mazeGraph.putVex(new Point(9, 2,63));
        mazeGraph.putVex(new Point(9, 3,64));
        mazeGraph.putVex(new Point(9, 5,65));
        mazeGraph.putVex(new Point(9, 7,66));
        mazeGraph.putVex(new Point(9, 9,67));

        mazeGraph.putArc(0, 1);
        mazeGraph.putArc(1, 2);
        mazeGraph.putArc(3, 4);
        mazeGraph.putArc(5, 6);
        mazeGraph.putArc(8, 9);
        mazeGraph.putArc(11, 12);
        mazeGraph.putArc(13, 14);
        mazeGraph.putArc(15, 16);
        mazeGraph.putArc(17, 18);
        mazeGraph.putArc(19, 20);
        mazeGraph.putArc(21, 22);
        mazeGraph.putArc(23, 24);
        mazeGraph.putArc(24, 25);
        mazeGraph.putArc(25, 26);
        mazeGraph.putArc(27, 28);
        mazeGraph.putArc(29, 30);
        mazeGraph.putArc(31, 32);
        mazeGraph.putArc(33, 34);
        mazeGraph.putArc(35, 36);
        mazeGraph.putArc(37, 38);
        mazeGraph.putArc(39, 40);
        mazeGraph.putArc(41, 42);
        mazeGraph.putArc(44, 45);
        mazeGraph.putArc(45, 46);
        mazeGraph.putArc(48, 49);
        mazeGraph.putArc(50, 51);
        mazeGraph.putArc(52, 53);
        mazeGraph.putArc(54, 55);
        mazeGraph.putArc(56, 57);
        mazeGraph.putArc(58, 59);
        mazeGraph.putArc(60, 61);
        mazeGraph.putArc(62, 63);
        mazeGraph.putArc(64, 65);
        mazeGraph.putArc(65, 66);
        mazeGraph.putArc(66, 67);

        mazeGraph.putArc(8, 15);
        mazeGraph.putArc(23, 37);
        mazeGraph.putArc(43, 56);
        mazeGraph.putArc(1, 9);
        mazeGraph.putArc(24, 33);
        mazeGraph.putArc(38, 57);
        mazeGraph.putArc(2, 10);
        mazeGraph.putArc(16, 25);
        mazeGraph.putArc(34, 63);
        mazeGraph.putArc(3, 17);
        mazeGraph.putArc(26, 39);
        mazeGraph.putArc(39, 50);
        mazeGraph.putArc(58, 64);
        mazeGraph.putArc(11, 18);
        mazeGraph.putArc(27, 40);
        mazeGraph.putArc(5, 12);
        mazeGraph.putArc(19, 28);
        mazeGraph.putArc(28, 41);
        mazeGraph.putArc(45, 51);
        mazeGraph.putArc(13, 20);
        mazeGraph.putArc(29, 35);
        mazeGraph.putArc(46, 52);
        mazeGraph.putArc(14, 30);
        mazeGraph.putArc(47, 53);
        mazeGraph.putArc(59, 66);
        mazeGraph.putArc(6, 21);
        mazeGraph.putArc(21, 31);
        mazeGraph.putArc(48, 54);
        mazeGraph.putArc(54, 60);
        mazeGraph.putArc(7, 22);
        mazeGraph.putArc(32, 36);
        mazeGraph.putArc(42, 49);
        mazeGraph.putArc(61, 67);
    }
}
