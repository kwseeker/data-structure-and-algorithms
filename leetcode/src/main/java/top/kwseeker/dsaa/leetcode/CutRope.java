package top.kwseeker.dsaa.leetcode;

/**
 * 给你一根长度为n绳子，请把绳子剪成m段（m、n都是整数，n>1并且m≥1）。每段的绳子的长度记为k[0]、k[1]、……、k[m]。k[0]*k[1]*…*k[m]可能的最大乘积是多少？
 * 例如当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到最大的乘积18。
 */
public class CutRope {

    public static void main(String[] args) {
        System.out.println(maxProduct(19));
    }

    //贪心算法,将问题拆分成一个个最优解。但是最优解的组合并不一定是最优的，需要经过数学推导验证。
    public static int maxProduct(int length) {
        if(length < 2) {
            return 0;
        }
        if(length == 2) {
            return 2;
        }
        if(length == 3) {
            return 3;
        }

        int countOf3 = length/3;
        int left = length%3;
        if(left == 1) {
            countOf3--;
        }
        int countOf2 = (length - 3*countOf3)/2;
        int ret = 1;
        for (int i = 0; i < countOf3; i++) {
            ret *= 3;
        }
        for (int i = 0; i < countOf2; i++) {
            ret *= 2;
        }
        return ret;
    }
}
