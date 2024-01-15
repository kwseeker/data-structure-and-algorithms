package top.kwseeker.dsaa.leetcode.lc0011;

/**
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 * <p>
 * 示例 1：
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
 * <p>
 * 示例 2：
 * 输入：height = [1,1]
 * 输出：1
 * <p>
 * 提示：
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 */
public class MaxArea11 {

    /**
     * 思路：双指针遍历
     * 两个指针从两头开始（最宽）记录容量，后面的思路就是每次移动数值较小的指针，直到指针相邻
     * 关键是这个证明过程
     * 证明参考官方题解
     */
    public int maxArea(int[] height) {
        int l = 0;
        int r = height.length - 1;
        int cap = 0;
        int newCap = 0;
        while (r - l > 0) {
            if (height[l] < height[r]) {
                newCap = (r - l) * height[l];
                l++;
            } else {
                newCap = (r - l) * height[r];
                r--;
            }
            cap = Math.max(cap, newCap);
        }
        return cap;
    }
}
