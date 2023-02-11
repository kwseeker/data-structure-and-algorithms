package top.kwseeker.dsaa.backtrack;

import java.util.*;

/**
 * N皇后问题
 * 主要逻辑就是：状态空间数树的深度优先搜索+可能解约束检查
 * 这里用树存储回溯过程（区别使用递归的局部变量），好处是无视栈深度限制，对比起来使用递归实现代码确实更简单
 */
public class NQueens {

    private static final boolean TRACE = true;
    private static final int N = 8;
    private final List<int[]> solutions = new ArrayList<>();    //所有可行解的集合，外层是可行解的集合，内层数组是某个可行解
                                                                // （数组本身就是个hashtable, 这里用索引代表某行，值代表皇后位于这行的某列）

    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        nQueens.solveByCallTrack();
        nQueens.displaySolution();
    }

    // 回溯法解决N皇后问题(这里使用树回溯，不是使用递归)
    private void solveByCallTrack() {
        Node root;
        for (int ci = 0; ci < N; ci++) {   //ci：第一行（idx=0）的第几列
            //开始的地方（第一列）
            root = new Node(0, ci, null, null);

            //遍历并构造回溯树(一列列地判断可行的位置)
            callTrack(root);
            System.out.println("-----------------");
        }
    }

    private void callTrack(Node root) {
        //1 数据初始化
        //下面三个集合是为了优化约束检查的性能引入并不是必须的，但是会影响代码可读性，暂不引入后面单独写个优化性能的示例
        //Set<Integer> columnOccupied = new HashSet<>();
        //Set<Integer> diagonals1 = new HashSet<>();
        //Set<Integer> diagonals2 = new HashSet<>();
        Node parent = root;

        //2 遍历其他行寻找可行位置
        for (int ri = 1; ri < N; ri++) {        //第ri列
            for (int ci = 0; ci <= N; ci++) {   //(ri, ci)
                //如果此行所有位置都是放不下，回溯(即找到parent节点(ci,ri+1)位置继续遍历)
                if (ci == N) {
                    if (parent == root) {       //回溯到根节点说明已经遍历搜索完毕，退出
                        printTrace("finished");
                        return;
                    }
                    ri = parent.rowIdx;
                    ci = parent.columnIdx;
                    parent = parent.parent;
                    parent.child = null;
                    printTrace("call track: (%d, %d))", ri, ci);
                    continue;
                }
                //约束判断
                if (!isSafe(root, ri, ci)) {
                    continue;
                }
                //可放置
                //即已经找到一个可行解,记录，并回溯（后面的位置不用看了肯定都不能放置）
                if (ri == N-1) {
                    printTrace("found one solution");
                    int[] solution = root.solution();
                    solution[ri] = ci;
                    solutions.add(solution);
                    ri = parent.rowIdx;
                    ci = parent.columnIdx;
                    parent = parent.parent;
                    parent.child = null;
                    continue;
                }
                //记录当前可行位置，然后直接跳转下一列判断
                Node node = new Node(ri, ci, parent, null);   //回溯点
                parent.child = node;
                parent = node;
                printTrace("new node: (%d, %d))", ri, ci);
                break;
            }
        }
    }

    /**
     * 约束检查
     * 可以优化性能
     */
    private boolean isSafe(Node root, int ri, int ci) {
        for (Node node = root;;) {
            //同列约束
            if (node.columnIdx == ci) {
                return false;
            }
            //同斜线约束
            // (y2-y1) = 1*(x2-x1) 或 (y2-y1) = -1*(x2-x1)
            // 即 y2-x2 = y1-x1 或 y2+x2 = y1+x1
            if ((node.rowIdx + node.columnIdx == ri + ci) || (node.rowIdx - node.columnIdx == ri - ci)) {
                return false;
            }

            if ((node = node.child) == null) {
                return true;
            }
        }
    }

    private void displaySolution() {
        System.out.println("solution count: " + solutions.size());
        for (int[] solution : solutions) {
            for (int i : solution) {
                for (int j = 0; j < N; j++) {
                    System.out.print(" " + (j==i ? 1 : 0));
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    /**
     * 退化成了链表
     */
    static class Node {
        private final int rowIdx;
        private final int columnIdx;
        private final Node parent;
        private Node child;

        public Node(int rowIdx, int columnIdx, Node parent, Node child) {
            this.rowIdx = rowIdx;
            this.columnIdx = columnIdx;
            this.parent = parent;
            this.child = child;
        }

        public int[] solution() {
            int[] solution = new int[N];
            Node node = this;
            while (node != null) {
                solution[node.rowIdx] = node.columnIdx;
                node = node.child;
            }
            return solution;
        }
    }

    private void printTrace(String fmt, Object ...args) {
        if (TRACE) {
            System.out.printf(fmt + "%n", args);
        }
    }
}
