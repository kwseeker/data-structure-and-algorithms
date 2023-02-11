package top.kwseeker.dsaa.backtrack;

/**
 * 这个是ChatGPT通过回溯法实现的解决４皇后问题的示例
 * 总体思路和其他答案都相差无几，不过主要差别在于中间数据的存储，这里的实现的空间利用率并不是很高（对比NQueens2,NQueens2每次遍历占用的内存会更少，不过反过来更容易理解）
 */
public class NQueens1 {
    static final int N = 4;
    static int count = 0;

    public static void main(String[] args) {
        solveNQ();
    }

    static void solveNQ() {
        int[][] board = { { 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 } };

        if (!solveNQUtil(board, 0)) {
            System.out.println("Solution does not exist");
        }
    }

    /**
     * 算法实现部分
     */
    static boolean solveNQUtil(int[][] board, int col) {
        /* base case: If all queens are placed then return true */
        if (col >= N) {
            printSolution(board);
            return true;
        }

        boolean res = false;

        /* Consider this column and try placing this queen in all rows
           one by one */
        for (int i = 0; i < N; i++) {
            // 1 约束条件判断
            if (isSafe(board, i, col)) {
                board[i][col] = 1;

                /* recur to place rest of the queens */
                res = solveNQUtil(board, col + 1) || res;

                /* If placing queen in board[i][col] doesn't lead to a solution then
                   remove queen from board[i][col] */
                board[i][col] = 0; // BACKTRACK
            }
        }

        return res;
    }

    /**
     * 约束条件检查
     */
    static boolean isSafe(int[][] board, int row, int col) {
        int i, j;

        /* Check this row on left side */
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        /* Check upper diagonal on left side */
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        /* Check lower diagonal on left side */
        for (i = row, j = col; j >= 0 && i < N; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    static void printSolution(int[][] board) {
        System.out.print("Solution " + (++count) + ":\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
