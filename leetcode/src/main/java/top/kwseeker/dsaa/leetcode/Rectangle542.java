package top.kwseeker.dsaa.leetcode;

/**
 * 这其实是一个图的遍历问题
 * TODO:后面研究完图的遍历思想再完善
 */
public class Rectangle542 {

    public static void main(String[] args) {
        int[][] matrix = {
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 1, 1, 0, 0}
        };
    }

    //public int[][] updateMatrix(int[][] matrix) {
    //    int h = matrix[0].length;
    //    int v = matrix.length;
    //    int[][] result = new int[v][h];
    //    for(int i=0; i < v; i++) {
    //        for(int j=0; j < h; j++) {
    //            if(matrix[i][j] == 0) {
    //                result[i][j] = 0;
    //            } else {
    //                int count=1;
    //                for (;;count++) {
    //                    //获取(j,i)外围菱形所有数据 |x-j|+|y-i|=count
    //
    //
    //                }
    //                result[i][j] = count;
    //            }
    //        }
    //    }
    //}
}
