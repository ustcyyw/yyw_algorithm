package medium.ArrayAndMatrix;

/**
 * @Time : 2020年3月6日12:03:55
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.Arrays;

/**
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 * 必须原地修改，只允许使用额外常数空间。
 * <p>
 * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-permutation
 */
public class nextPermutation {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.86%的用户
     * 内存消耗 :39 MB, 在所有 Java 提交中击败了30.38%的用户
     * 要得到下一个更大的数字而且该数字要尽量小，就需要尽量在低位换一个稍大的数字过来。
     * 比如通过改变第i位来使数字更大，交换的数字得从i+1,i+2...找。往i-1，i-2找会使得数字更小。
     * 要找一个更大的数字，而且要让这个更大的数字尽可能的小，那么就在 i+1,i+2...中找比i大的最小值：
     * 如果找不到这样的值，说明i+1,i+2...后面的数字都比i小，不满足题意。
     * 如果找到了，进行交换。交换之后，只能保证从0到i位是尽可能小的，i+1，i+2...还不一定，要进行排序才可以。
     * 从低位开始，就是从数组右侧开始遍历。
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                int j = nums.length - 1;
                for (; j > i && nums[j] <= nums[i]; j--) ;
                exch(nums, i, j);
                reverse(nums, i + 1, n);
                return;
            }
        }
        reverse(nums, 0, n);
    }

    private void exch(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int lo, int hi){
        while(lo < hi){
            exch(nums, lo++, hi--);
        }
    }
}
