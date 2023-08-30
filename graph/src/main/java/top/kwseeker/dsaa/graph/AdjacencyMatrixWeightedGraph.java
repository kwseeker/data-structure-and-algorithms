package top.kwseeker.dsaa.graph;

import java.util.*;
import java.util.Queue;

/**
 * 邻接矩阵实现的加权图，邻接矩阵支持同时表示有向和无向（无向用两个平行有向边表示）
 */
public class AdjacencyMatrixWeightedGraph<T> {
    //最大顶点数量
    private static int MAX_VEX = 9;
    //代表无穷，没有路径可达就是无穷
    private static final int INFINITY = 65535;

    private final Vertex<T>[] vexs;
    private final Edge[][] arc;
    //实际顶点的数量
    private int numVertex = 0;
    //实际边的数量
    private int numEdge = 0;
    //是否有向, 默认无向
    private boolean directed = false;

    transient int modCount;

    //V0到目标最短路径下标的数组
    private int[] pathMatrix;
    //V0到各顶点最短路径的权值和
    private int[] shortPathTable;

    public AdjacencyMatrixWeightedGraph() {
        this.vexs = new Vertex[MAX_VEX];
        this.arc = new Edge[MAX_VEX][MAX_VEX];
    }

    public AdjacencyMatrixWeightedGraph(boolean directed) {
        this.vexs = new Vertex[MAX_VEX];
        this.arc = new Edge[MAX_VEX][MAX_VEX];
        this.directed = directed;
    }

    public AdjacencyMatrixWeightedGraph(int maxVex, boolean directed) {
        MAX_VEX = maxVex;
        this.vexs = new Vertex[MAX_VEX];
        this.arc = new Edge[MAX_VEX][MAX_VEX];
    }

    public Vertex<T> getVertex(int vexIdx) {
        return vexs[vexIdx];
    }

    public void putVex(T data) {
        putVex(new Vertex<>(data));
    }
    //添加新顶点, 先不考虑不可以添加重复数据（data相同的数据）
    public void putVex(Vertex<T> vex) {
        vexs[numVertex] = vex;
        modCount++;
        vex.idx = numVertex;
        numVertex++;
    }
    //删除顶点 TODO
    public void deleteVex() {
    }
    //添加弧
    public void putArc(int tailIdx, int headIdx) {
        putArc(new Edge(tailIdx, headIdx, 1));
    }
    public void putArc(int tailIdx, int headIdx, int weight) {
        putArc(new Edge(tailIdx, headIdx, weight));
    }
    public void putArc(Edge edge) {
        arc[edge.tailIdx][edge.headIdx] = edge;
        modCount++;
        numEdge++;
        if (!directed) {    //无向的话，每个边在临接矩阵都是双向的
            arc[edge.headIdx][edge.tailIdx] = new Edge(edge.headIdx, edge.tailIdx, edge.weight);
            numEdge++;
        }
    }
    //删除弧
    //获取顶点的值
    //为顶点赋值
    //迭代器实现依次获取从顶点v开始（v作为弧尾）的邻接顶点
    public Iterator<Vertex<T>> iterator(int vexIdx) {
        return new VertexAdjacencyIterator(getVertex(vexIdx));
    }
    //打印图的邻接矩阵
    public void printGraph() {
        for (int i = 0; i < MAX_VEX; i++) {
            for (int j = 0; j < MAX_VEX; j++) {
                System.out.printf((arc[i][j] != null ? 1 : 0) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 深度优先遍历
     * @param startVexIdx   作为起点的顶点的索引
     */
    public void dfsTraverse(int startVexIdx) {
        System.out.printf("DFS start %d: ", startVexIdx);
        dfs(null, startVexIdx, true);
        System.out.println();
    }

    //看一些框架源码都喜欢将一个完整的算法放到一起，所以这里合并
    private void dfs(boolean[] visited, int vexIndex, boolean outer) {
        if (visited == null) {
            visited = new boolean[numVertex];
            Arrays.fill(visited, false);
            outer = true;
        }
        if (outer) {
            for (int i = vexIndex; i < numVertex + vexIndex; i++) {
                int index = i >= numVertex ? i - numVertex : i;
                if (!visited[index]) {
                    System.out.println("遍历次数：+1");    //连通图只需要一次深度遍历
                    dfs(visited, index, false);
                }
            }
        } else {
            System.out.print(vexs[vexIndex].data + " ");
            visited[vexIndex] = true;
            for (int i = 0; i < numVertex; i++) {
                if (arc[vexIndex][i] != null && !visited[i]) {    //有邻接顶点且未被访问过
                    dfs(visited, i, false);
                }
            }
        }
    }

    /**
     * 查找从索引为startIdx的顶点到endIdx的顶点
     */
    public List<Integer> depthFirstPath(int startIdx, int endIdx) {
        System.out.printf("DFS findPath start=%d, end=%d :\n", startIdx, endIdx);
        DepthFirstPath record = new DepthFirstPath(startIdx, endIdx, new ArrayList<>());
        depthFirstPath(record, startIdx, 0);
        return record.pathIdxList;
    }

    //只需要从起点开始遍历一次
    private void depthFirstPath(DepthFirstPath record, int vexIndex, int count) {
        if (record.visited == null) {
            record.visited = new boolean[numVertex];
            Arrays.fill(record.visited, false);
        }

        //System.out.print(vexs[vexIndex].data + " ");
        record.visited[vexIndex] = true;
        record.pathIdxList.add(vexIndex);
        count++;
        if (vexIndex == record.endVexIndex) {
            return;
        }
        for (int i = 0; i < numVertex; i++) {
            if (arc[vexIndex][i] != null && !record.visited[i]) {   //有邻接顶点且未被访问过
                if (count < record.pathIdxList.size()) {            //说明分支不通，路径记录需要返回到上次分叉顶点
                    System.out.println(record.pathIdxList.size() + "->" + count + " : " + vexIndex);
                    record.pathIdxList = record.pathIdxList.subList(0, count);
                }
                depthFirstPath(record, i, count);
            }
        }
    }

    //public void dfsTraverse(int startVexIdx) {
    //    boolean[] visited = new boolean[numVertex];
    //    Arrays.fill(visited, false);
    //    for (int i = startVexIdx; i < numVertex + startVexIdx; i++) {
    //        int index = i;
    //        if (i >= numVertex) {
    //            index = i - numVertex;
    //        }
    //        if (!visited[index]) {
    //            dfs(visited, index);
    //        }
    //    }
    //}
    //
    //private void dfs(boolean[] visited, int vexIndex) {
    //    System.out.println(vexs[vexIndex].data + " ");
    //    visited[vexIndex] = true;
    //    for (int i = 0; i < numVertex; i++) {
    //        if (arc[vexIndex][i] != null && !visited[i]) {    //有邻接顶点且未被访问过
    //            dfs(visited, i);
    //        }
    //    }
    //}

    /**
     * 广度优先遍历
     * @param startVexIdx   作为起点的顶点的索引
     */
    public void bfsTraverse(int startVexIdx) {
        System.out.printf("BFS start %d: ", startVexIdx);
        boolean[] visited = new boolean[numVertex];
        Queue<Integer> queue = new ArrayDeque<>();
        Arrays.fill(visited, false);

        for (int i = startVexIdx; i < numVertex + startVexIdx; i++) {
            int index = i >= numVertex ? i - numVertex : i;
            if (!visited[index]) {
                System.out.print(vexs[index].data + " ");
                visited[i] = true;

                queue.add(i);
                while (!queue.isEmpty()) {
                    Integer idx = queue.poll();
                    for (int j = 0; j < numVertex; j++) {
                        if (arc[idx][j] != null && !visited[j]) {
                            System.out.print(vexs[j].data + " ");
                            visited[j] = true;
                            queue.add(j);
                        }
                    }
                }
            }
        }

        System.out.println();
    }

    public BreadthFirstPath breadthFirstPath(int s) {
        return new BreadthFirstPath(s);
    }

    /**
     * Dijkstra 最短路径算法
     */
    public void shortestPathDijkstra() {

    }

    /**
     * 顶点
     */
    static class Vertex<T> {
        int idx;    //在顶点数组中的索引
        T data;

        public Vertex(T data) {
            this.data = data;
        }
    }

    /**
     * 边
     */
    static class Edge {
        //弧尾顶点在顶点数组中的索引
        int tailIdx;
        //弧头顶点在顶点数组中的索引
        int headIdx;
        //权重
        int weight;

        public Edge(int tailIdx, int headIdx, int weight) {
            this.tailIdx = tailIdx;
            this.headIdx = headIdx;
            this.weight = weight;
        }
    }

    final class VertexAdjacencyIterator implements Iterator<Vertex<T>> {
        Vertex<T> tailVex;  //弧尾顶点
        Vertex<T> current;  //当前弧头顶点
        Vertex<T> next;     //下一个弧头顶点
        int expectedModCount;  // for fast-fail

        public VertexAdjacencyIterator(Vertex<T> vertex) {
            expectedModCount = modCount;
            tailVex = vertex;
            current = next = null;
            for (int idxStart = 0; idxStart < MAX_VEX; idxStart++) {
                if (arc[tailVex.idx][idxStart] != null) {
                    int nextIdx = arc[tailVex.idx][idxStart].headIdx;
                    next = vexs[nextIdx];
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Vertex<T> next() {
            Vertex<T> e = next;
            next = null;
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (e == null) {
                throw new NoSuchElementException();
            }
            //获取下一个
            current = e;
            for (int idxStart = current.idx + 1; idxStart < MAX_VEX; idxStart++) {
                if (arc[tailVex.idx][idxStart] != null) {
                    int nextIdx = arc[tailVex.idx][idxStart].headIdx;
                    next = vexs[nextIdx];
                    break;
                }

            }
            return e;
        }
    }

    static class DepthFirstPath {
        boolean[] visited;
        int startVexIndex;
        int endVexIndex;
        List<Integer> pathIdxList;

        public DepthFirstPath(int startVexIndex, int endVexIndex, List<Integer> pathIdxList) {
            this.startVexIndex = startVexIndex;
            this.endVexIndex = endVexIndex;
            this.pathIdxList = pathIdxList;
        }
    }

    public final class BreadthFirstPath {
        boolean[] visited;
        int[] edgeTo;   //到达每个顶点的前一个顶点索引, 这个正好将顶点构成了链表且 到达某顶点idx的前一个顶点就是 edgeTo[idx]
                        //链表不一定需要指针/引用，数组也可以
        final int s;    //起点

        public BreadthFirstPath(int s) {
            this.visited = new boolean[numVertex];
            this.edgeTo = new int[numVertex];
            this.s = s;
            bfs(s);
        }

        private void bfs(int s) {
            Queue<Integer> queue = new ArrayDeque<>();
            Arrays.fill(visited, false);

            visited[s] = true;
            queue.add(s);
            while (!queue.isEmpty()) {
                Integer idx = queue.poll();
                for (int j = 0; j < numVertex; j++) {
                    if (arc[idx][j] != null && !visited[j]) {
                        edgeTo[j] = idx;    //
                        visited[j] = true;
                        queue.add(j);
                    }
                }
            }
        }

        public boolean hasPathTo(int v) {
            return visited[v];
        }

        public Stack<Integer> pathTo(int v) {
            if (!hasPathTo(v)) {
                return null;
            }
            Stack<Integer> path = new Stack<>();
            for (int i = v; i != s; i = edgeTo[i]) {
                path.push(i);
            }
            path.push(s);
            return path;
        }
    }
}
