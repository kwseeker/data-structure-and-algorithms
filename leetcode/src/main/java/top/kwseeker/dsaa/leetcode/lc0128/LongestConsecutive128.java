package top.kwseeker.dsaa.leetcode.lc0128;

import java.util.HashSet;
import java.util.Set;

/**
 * 128. 最长连续序列
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * 示例 1：
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * <p>
 * 提示：
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */
public class LongestConsecutive128 {

    /**
     * 思路：
     * 思路1：（比较容易想到）
     * 对数组排序，然后统计最长，但是排序复杂度超过了O(n)，Arrays.sort() 基于TimeSort算法，复杂度O(nlogn);
     * 但是实际测试，先排序再统计的性能比思路2快很多，仅凭复杂度估计算法性能并不准确，从实际过程倒是比较好理解为何思路2时间复杂度O(n)性能却更差
     * 思路2：（官方解法）
     * 1）以元素为key存哈希表并去重，以值为key (这样就可以开始按值查找了), 将判断连续问题转化成了元素E相邻值E+1是否存在于哈希表的问题
     * 2）后面的逻辑就比较清晰了
     */
    public int longestConsecutive(int[] nums) {
        //以num为key存哈希表并去重
        Set<Integer> numSet = new HashSet<>();  //HashSet和HashMap一样内部都是哈希表+链表/红黑树
        for (int num : nums) {
            numSet.add(num);
        }

        int maxLen = 0;
        for (Integer num : numSet) {
            if (numSet.contains(num - 1)) { //(优化点) 为了确保从极值开始统计，减少重复循环次数，虽然减少了一些重复循环但是还是有重复的，思路1先排序则可以完全杜绝无效的循环统计
                continue;
            }
            int len = 1;
            //只需要向一个方向进行统计，这里选择升序统计，从大数降序统计和从小数升序统计效果是一样的，
            //比如 4, 6, 5, 3, 1, 2； 从4开始先向上再向下统计，与从1只向上统计效果一样，所以只需要选择一个方向统计
            while (numSet.contains(num + 1)) {
                len++;
                num++;
            }
            if (len > maxLen) {
                maxLen = len;
            }
        }

        return maxLen;
    }
}
