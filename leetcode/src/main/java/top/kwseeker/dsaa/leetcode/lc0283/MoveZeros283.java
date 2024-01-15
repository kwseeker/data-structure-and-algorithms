package top.kwseeker.dsaa.leetcode.lc0283;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 283. 移动零
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
 * <p>
 * 示例 1:
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * <p>
 * 示例 2:
 * 输入: nums = [0]
 * 输出: [0]
 * <p>
 * 提示:
 * 1 <= nums.length <= 104
 * -231 <= nums[i] <= 231 - 1
 * <p>
 * 进阶：你能尽量减少完成的操作次数吗？
 */

class MoveZeros283 {

    /**
     * 要求不复制数组
     * 思路1：（最易想到）
     * 很容易被题目带节奏，题目说移动0就老实地真去移动0,从左向右将遇到的每一个0移动到最后；
     * 但是问题也很明显每移动一个0都是要交换大量的数据，复杂度O(n^2)，性能惨不忍睹。
     * 思路2：（性能和内存都表现不好）
     * 需要转换下思路，将0移动到末尾等效于“将非0值移动到数组开头”
     * 从左向右遇到非0值就和最左边的0值交换，然后循环处理即可；需要注意交换后，zp指向的可能不再是最左边的0
     * 思路3：
     * 双指针，先执行外层遍历，直到遇到0, 再执行另一个遍历直到遇到一个非0，执行交换
     * 思路4：（正解）
     * 本质和复制数组同理，只是将值复制给自己而已，每找到一个非0值就赋值给j位置（从0开始）, 最后将j位置后面的值清空
     */
    public void moveZeroes(int[] nums) {
        int j = 0;  //记录
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {    //每找到一个非0值就赋值给j位置（从0开始）
                nums[j] = nums[i];
                j++;
            }
        }
        for (; j < nums.length; j++) {
            nums[j] = 0;
        }
    }

    public void moveZeroes4(int[] nums) {    //思路3
        for (int i = 0, j = 0; i < nums.length && j < nums.length; i++) {
            if (nums[i] != 0) {
                continue;
            }
            j = Math.max(i + 1, j);
            for (; j < nums.length; j++) {
                if (nums[j] == 0) {
                    continue;
                }
                nums[i] = nums[j];
                nums[j] = 0;
                j++;
                break;
            }
        }
    }

    public void moveZeroes3(int[] nums) {    //仅仅测试复制数组的方式的性能和内存消耗,性能应该是最好的
        int[] nums2 = new int[nums.length];
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums2[j] = nums[i];
                j++;
            }
        }
        System.arraycopy(nums2, 0, nums, 0, nums.length);
    }

    public void moveZeroes2(int[] nums) {    //思路2优化，使用队列可以将内部的循环优化掉,但是LinkedList本身使用也比较耗时
        //使用队列存储0值的索引
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == 0) {
                queue.add(i);
            } else {
                Integer peek = queue.peek();
                if (peek != null) {    //即左边有0
                    //交换位置
                    Integer zp = queue.poll();
                    nums[zp] = nums[i];
                    nums[i] = 0;
                    queue.add(i);
                }
            }
        }
    }

    public void moveZeroes1(int[] nums) {    //思路2解法
        int zp = -1;    //最左边0值的索引
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == 0) {
                if (zp == -1) {
                    zp = i;
                }
            } else {
                if (zp >= 0 && i > zp) {    //即i左边有0
                    //交换位置
                    nums[zp] = nums[i];
                    nums[i] = 0;
                    int ozp = zp;   //交换前zp索引
                    zp = i;
                    //交换位置后zp可能指向的不再是第1个0 比如： 1,3,0(zp),0,5,12, zp和5交换后，1,3,5,0,0(zp),12
                    //需要重新遍历ozp到i找到第一个0更新zp
                    for (int j = ozp + 1; j < i; j++) {
                        if (nums[j] == 0) {
                            zp = j;
                            break;
                        }
                    }
                }
            }
        }
    }
}