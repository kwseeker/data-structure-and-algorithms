package top.kwseeker.dsaa.leecode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SurroundedRegion130 {

    private boolean[][] juge = new boolean[1000][1000];

    public void solve(char[][] board) {
        if (board.length - 1 <= 0 || board[0].length <= 0) return;

        int m = board.length - 1;
        int n = board[0].length - 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (board[i][j] == 'O' && !juge[i][j])
                    bfs(i, j, board, m, n);
            }
        }

    }

    public void bfs(int x, int y, char[][] board, int m, int n) {
        List<int[]> jugehuan = new ArrayList<>();
        boolean isHuan = false;
        //能改变的方向四个
        int[][] change = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        //bfs队列
        Queue<int[]> q = new LinkedList<>();
        int[] one = {x, y};
        //扫描到的第一个0进入队列
        q.add(one);
        board[x][y] = 'X';
        juge[x][y] = true;
        while (!q.isEmpty()) {
            int[] nowxy = q.poll();
            jugehuan.add(nowxy);
            for (int i = 0; i < 4; i++) {
                int changex = nowxy[0] + change[i][0];
                int changey = nowxy[1] + change[i][1];
                if (changex >= 1 && changex < m && changey >= 1 && changey < n) {
                    if (board[changex][changey] == 'O') {
                        int[] now = {changex, changey};
                        q.add(now);
                        board[changex][changey] = 'X';
                        juge[changex][changey] = true;
                    }
                } else {
                    if (board[changex][changey] == 'O')
                        isHuan = true;
                }
            }
        }
        if (isHuan) {
            for (int i = 0; i < jugehuan.size(); i++) {
                board[jugehuan.get(i)[0]][jugehuan.get(i)[1]] = 'O';
            }
        }
    }

    public static void main(String[] args) {
        SurroundedRegion130 s = new SurroundedRegion130();
        char[][] a = {
                {'X', 'X', 'X'},
                {'X', 'O', 'X'},
                {'X', 'X', 'X'}
        };
        s.solve(a);
    }

}
