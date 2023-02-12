package top.kwseeker.dsaa.backtrack;

/**
 * 回溯法解数读
 * 来自LeeCode最高赞的Java版本：https://leetcode.cn/problems/sudoku-solver/solutions/155521/37-by-ikaruga/?orderBy=most_votes
 * 注意此解题方法只找到一种可行解就退出了
 * 分析其优化方法：
 * １）使用bit存储各行各列各９宫格已经有的数字（状态压缩，１的偏移量即存在的数字），可以通过位运算提升约束判断的性能
 * ２）按候选项多少对格子进行了排序，降低了回溯的次数，也会提升性能以及内存占用（栈深会降低，局部变量会更少）
 */
class Sudoku1 {
    //下面３个变量是储存已经有值的各行各列各９宫格已经有的数字，并没有存储位置信息
    // 二进制中1表示 对应位置已经有值了
    private int[] rows = new int[9];
    private int[] cols = new int[9];
    private int[][] cells = new int[3][3];

    public static void main(String[] args) {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};

        Sudoku1 sudoku = new Sudoku1();
        sudoku.solveSudoku(board);

    }
    public void solveSudoku(char[][] board) {
        //1 分析各行各列各９宫格已经有的数字
        int cnt = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char c = board[i][j];
                if (c == '.') {
                    cnt++;
                } else {
                    int n = c - '1';
                    fillNumber(i, j, n, true);
                }
            }
        }
        //２ 从候选项最少的格子开始遍历
        //2.1 按候选项多少先排了个序
        backtrace(board, cnt);
    }

    private boolean backtrace(char[][] board, int cnt) {
        if (cnt == 0) {
            return true;
        }
        // 获取当前 候选项最少（即限制最多）的格子下标
        int[] pos = getMinOkMaskCountPos(board);
        int x = pos[0], y = pos[1];
        // okMask 值为1的 位 表示 对应的数字 当前可以填入
        int okMask = getOkMask(x, y);

        for (char c = '1'; c <= '9'; c++) {
            int index = c - '1';
            if (testMask(okMask, index)) {
                fillNumber(x, y, index, true);
                board[x][y] = c;
                if (backtrace(board, cnt - 1)) return true; // 题目假定唯一解
                board[x][y] = '.';
                fillNumber(x, y, index, false);
            }
        }

        return false;
    }

    // n 0..8
    private void fillNumber(int x, int y, int n, boolean fill) {
        // 因为回溯先选择后撤销，所以fill先true后false, false时对应位置一定是1，所以异或可行
        // rows[x] = fill ? rows[x] | (1<<n) : rows[x] ^ (1<<n);
        // cols[y] = fill ? cols[y] | (1<<n) : cols[y] ^ (1<<n);
        // cells[x/3][y/3] = fill ? cells[x/3][y/3] | (1<<n) : cells[x/3][y/3] ^ (1<<n);

        // ture set 1, false set 0
        rows[x] = fill ? rows[x] | (1 << n) : rows[x] & ~(1 << n);
        cols[y] = fill ? cols[y] | (1 << n) : cols[y] & ~(1 << n);
        cells[x / 3][y / 3] = fill ? cells[x / 3][y / 3] | (1 << n) : cells[x / 3][y / 3] & ~(1 << n);
    }

    private int getOkMask(int x, int y) {
        return ~(rows[x] | cols[y] | cells[x / 3][y / 3]);
    }

    // mask 二进制 低9位 中 1的个数
    private int getOneCountInMask(int mask) {
        int res = 0;
        for (int i = 0; i < 9; i++) {
            int test = 1 << i;
            if ((mask & test) != 0) {
                res++;
            }
        }
        return res;
    }

    // mask 二进制 低index位 是否为 1
    private boolean testMask(int mask, int index) {
        return (mask & (1 << index)) != 0;
    }

    // 获取候选项最少的位置
    private int[] getMinOkMaskCountPos(char[][] board) {
        int[] res = new int[2];
        int min = 10;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') {
                    int okMask = getOkMask(i, j);
                    int count = getOneCountInMask(okMask);
                    if (count < min) {
                        min = count;
                        res[0] = i;
                        res[1] = j;
                    }
                }
            }
        }
        return res;
    }
}