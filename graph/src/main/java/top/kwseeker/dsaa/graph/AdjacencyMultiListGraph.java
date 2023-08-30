package top.kwseeker.dsaa.graph;

/**
 * 邻接多重表实现的无向图
 * 下面示例的数据结构（大话数据结构提供的一种演示数据结构）也仅仅只是演示，用到业务中除非数据很少，否则远远达不到性能要求。
 * 暂不考虑节点删除（数组本身就不适合做删除、扩缩容）、扩缩容、性能等等。
 */
public class AdjacencyMultiListGraph<T> {

    private int capacity;   //数组容量
    private int vertex;     //顶点数量
    private int edge;       //边数量
    private Node<T>[] nodes;

    public static void main(String[] args) {
        AdjacencyMultiListGraph<String> graph = new AdjacencyMultiListGraph<>(4);
        graph.addNode("V0");
        graph.addNode("V1");
        graph.addNode("V2");
        graph.addNode("V3");
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);

        //遍历

    }

    public AdjacencyMultiListGraph(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + capacity);
        this.capacity = capacity;
    }

    public boolean addNode(T data) {
        if (nodes == null) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Illegal initial capacity: " + capacity);
            }
            nodes = new Node[capacity];
        }
        if (vertex + 1 > capacity) {
            return false;
        }
        Node<T> node = new Node<>(data);
        nodes[vertex] = node;
        vertex++;
        return true;
    }

    public boolean addEdge(int iVex, int jVex) {
        if (iVex >= jVex || iVex < 0 || jVex > vertex) {
            throw new IllegalArgumentException("Illegal argument");
        }
        //无向边是否已经存在
        if (false) {            //TODO
            return false;
        }
        //头插法

        Edge edge = new Edge(iVex, jVex, nodes[iVex].firstEdge, nodes[jVex].firstEdge);
        nodes[iVex].firstEdge = edge;
        nodes[jVex].firstEdge = edge;
        return true;
    }

    public void dfs() {

    }

    /**
     * 顶点节点
     */
    static class Node<T> {
        T data;
        Edge firstEdge;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }
    }

    /**
     * 边节点
     */
    static class Edge {
        //边依附的节点在顶点表中的下标，这里设定 iVex < jVex
        int iVex;
        //边依附的节点在顶点表中的下标
        int jVex;
        //iVex顶点的下一条边
        Edge iLink;
        //jVex顶点的下一条边
        Edge jLink;

        public Edge(int iVex, int jVex, Edge iLink, Edge jLink) {
            this.iVex = iVex;
            this.jVex = jVex;
            this.iLink = iLink;
            this.jLink = jLink;
        }
    }
}
