package top.kwseeker.dsaa.leetcode;

/**
 * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 *
 * 示例 1：
 *
 * 输入：[-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 *
 * 提示：
 *
 * 1 <= A.length <= 10000
 * -10000 <= A[i] <= 10000
 * A 已按非递减顺序排序。
 *
 * 来源：力扣（LeetCode）
 */
public class SquaresSorter997 {

    public static void main(String[] args) {
        //int[] arr1 = {-4,-1,0,3,10};
        int[] arr1 = {-7,-3,2,3,11};
        int[] ret = sortedSquares(arr1);
        for (int val : ret) {
            System.out.println(val + " ");
        }
    }

    //从左右同时开始
    static int[] sortedSquares(int[] A) {
        int leftMax = 0;
        int rightMax = 0;
        int len = A.length;
        int[] ret = new int[len];

        for (int i = 0, j = len-1; i <= j;) {
            if(i==0 && j==len-1) {
                leftMax = A[i]*A[i];
                rightMax = A[j]*A[j];
            }
            if(i==j) {
                ret[j-i] = A[i]*A[i];
                break;
            }

            if(leftMax > rightMax) {
                ret[j-i] = leftMax;
                i++;
                leftMax = A[i]*A[i];
            } else {
                ret[j-i] = rightMax;
                j--;
                rightMax = A[j]*A[j];
            }
        }

        return ret;
    }
}
