package top.kwseeker.dsaa.backtrack;

import java.util.*;

class NQueens2 {

    public static void main(String[] args) {
        NQueens2 nQueens = new NQueens2();
        List<List<String>> lists = nQueens.solveNQueens(4);
        //展示所有结果
        for (List<String> list : lists) {
            for (String s : list) {
                System.out.println(s + " ");
            }
            System.out.println();
        }
    }

    /**
     * @param n N个皇后
     * @return 所有可行解
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> solutions = new ArrayList<>();
        //用于记录已经放置的皇后的位置
        int[] queens = new int[n];
        Arrays.fill(queens, -1);

        Set<Integer> columns = new HashSet<>();
        Set<Integer> diagonals1 = new HashSet<>();
        Set<Integer> diagonals2 = new HashSet<>();
        backtrack(solutions, queens, n, 0, columns, diagonals1, diagonals2);

        return solutions;
    }

    /**
     * 回溯法解决N皇后问题
     * @param solutions 所有可行解的集合
     * @param queens    记录已经放置的皇后的位置，比如[2,2,2,2]分别表示第0/1/2/3列的第2行放置了皇后
     * @param n         N皇后
     * @param row       每次递归的当前行，行的坐标
     * @param columns   本轮递归记录放置皇后位置的列记录（第几列有皇后），会继承上一轮递归“当前可行”的列记录
     * @param diagonals1    本轮递归所有放置了皇后的位置的 行-列　的值
     * @param diagonals2    本轮递归所有防止了皇后的位置的 行+列 的值
     */
    public void backtrack(List<List<String>> solutions, int[] queens, int n, int row, Set<Integer> columns, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        //1 终止条件：递归完成遍历（记录结果）
        if (row == n) {
            List<String> board = generateBoard(queens, n);
            solutions.add(board);
            return;
        }
        //2 当前列依次遍历，寻找与已放置的皇后的位置“安全”的位置
        for (int i = 0; i < n; i++) {   //第row行，第i列
            //2.1 约束条件判断
            if (columns.contains(i)) {  //不同列约束
                continue;
            }
            int diagonal1 = row - i;    //diagonal1+diagonal2不同斜线约束: 即行-列相等或行+列相等的位置一定在同一斜线上
            if (diagonals1.contains(diagonal1)) {
                continue;
            }
            int diagonal2 = row + i;
            if (diagonals2.contains(diagonal2)) {
                continue;
            }
            //2.2 记录当前可行位置
            queens[row] = i;
            columns.add(i);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);
            //2.3 递归下一行继续验证可行性
            backtrack(solutions, queens, n, row + 1, columns, diagonals1, diagonals2);
            //2.4 位置不可行或遍历完成得到可行解，删除此次递归临时数据
            queens[row] = -1;
            columns.remove(i);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
        }
    }

    public List<String> generateBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }
}
