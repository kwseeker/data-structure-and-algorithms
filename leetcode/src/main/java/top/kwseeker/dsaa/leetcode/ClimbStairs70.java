package top.kwseeker.dsaa.leetcode;

import java.util.Scanner;

public class ClimbStairs70 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(climbStairs(n));
    }

    //动态规划(归纳总结)自底向上迭代,(使用递归的话，有很多重复的过程需要引入容器优化)
    static int climbStairs(int n) {
        //两个边界
        if(n == 1) {
            return 1;
        }
        if( n==2 ) {
            return 2;
        }
        int x = 1;
        int y = 2;
        for (int i = 3; i <= n; i++) {
            int tmp = x + y;
            x = y;
            y = tmp;

        }
        return y;
    }
}
