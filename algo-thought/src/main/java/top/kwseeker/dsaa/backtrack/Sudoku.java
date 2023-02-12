package top.kwseeker.dsaa.backtrack;

/**
 * 回溯法解数读 (LeeCode-37)
 * 先以４×４的四宫格测试，测试通过后转成９×９
 * 往格子中填入 1/2/3/4，每行，每列，每个小宫格数字不重复,比如：
 * 3 ?  ? 4
 * ? 4  3 ?
 * 4 ?  1 ?
 * ? ?  ? ?
 * 这里打印所有可行解
 */
public class Sudoku {

    //private static final int POWER = 2;
    private static final int POWER = 3;
    private static final int N = POWER * POWER;

    public static void main(String[] args) {
        //数据初始化, 0代表空（待填入值）
        int[][] board;
        if (POWER == 2) {
            board = new int[][]{
                    {3, 0, 0, 4},
                    {0, 4, 3, 0},
                    {4, 0, 1, 0},
                    {0, 0, 0, 0}
            };
        } else if (POWER == 3) {
            board = new int[][]{
                    {0, 4, 6, 9, 0, 3, 0, 0, 0},
                    {0, 0, 3, 0, 5, 0, 0, 6, 0},
                    {9, 0, 0, 0, 0, 2, 0, 0, 3},
                    {0, 0, 5, 0, 0, 6, 0, 0, 0},
                    {8, 0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 1, 0, 7, 8, 0, 2, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 5, 0},
                    {0, 8, 1, 3, 0, 0, 0, 0, 7},
                    {0, 0, 0, 8, 0, 0, 1, 0, 4}
            };
        }

        long start = System.currentTimeMillis();
        resolve(board, 0, 0);
        System.out.println("Cost: " + (System.currentTimeMillis() - start));
    }

    private static void resolve(int[][] board, int row, int column) {
        if (row == N) {
            return;
        }
        if (board[row][column] != 0) {
            callTrack(board, row, column);
            return;
        }

        //空格子
        for (int k = 1; k <= N; k++) {
            //1 约束条件判断
            boolean ok = restrict(board, row, column, k);
            if (!ok) {
                if (k == N) {   //无值可取，回溯
                    return;
                }
                continue;
            }
            //临时可行的值
            board[row][column] = k;
            callTrack(board, row, column);
            //回溯后处理
            //System.out.println("callTrack: (" + row + "," + column + ") =" + board[row][column]);
            board[row][column] = 0;
        }
    }

    private static void callTrack(int[][] board, int row, int column) {
        if (row == N - 1 && column == N - 1) {
            System.out.println("Found one solution: ");
            printSolution(board);
            return;
        }
        if (column == N - 1) {
            resolve(board, row + 1, 0);
        } else {
            resolve(board, row, column + 1);
        }
    }

    /**
     * 约束条件判断
     */
    private static boolean restrict(int[][] board, int row, int column, int value) {
        //行约束
        for (int i : board[row]) {
            if (i == value) {
                return false;
            }
        }
        //列约束
        for (int i = 0; i < N; i++) {
            if (board[i][column] == value) {
                return false;
            }
        }
        //小宫格约束
        for (int i = 0; i < N; i += POWER) {
            if (row < i || row >= i + POWER) {
                continue;
            }
            for (int j = 0; j < N; j += POWER) {
                if (column < j || column >= j + POWER) {
                    continue;
                }
                for (int m = i; m < i + POWER; m++) {
                    for (int n = j; n < j + POWER; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static void printSolution(int[][] solution) {
        for (int[] ints : solution) {
            for (int value : ints) {
                System.out.print(" " + value);
            }
            System.out.println();
        }
    }
}
