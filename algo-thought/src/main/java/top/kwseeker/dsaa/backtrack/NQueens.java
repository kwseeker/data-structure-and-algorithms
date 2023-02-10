package top.kwseeker.dsaa.backtrack;

/**
 * N皇后问题
 * 主要逻辑就是：树的深度优先搜索+可能解约束检查
 */
public class NQueens {

    private static final int N = 4;

    private int[][] board;

    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        nQueens.initBoard(N);
        nQueens.solveByCallTrack();
        nQueens.displayBoard();
    }

    // 回溯法解决N皇后问题
    private void solveByCallTrack() {
        //1 遍历并构造回溯树（先列后行）
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

            }
        }
    }

    private void initBoard(int len) {
        board = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                board[i][j] = 0;
            }
        }
    }

    private void displayBoard() {
        System.out.println("Board result: ");
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    static class Node {
        private int rowIdx;
        private int columnIdx;
        private Node parent;
        private Node[] children;
    }
}
