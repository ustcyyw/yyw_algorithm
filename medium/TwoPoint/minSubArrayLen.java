package medium.TwoPoint;

/**
 * @Time : 2020年3月2日10:22:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
 *
 * 示例: 
 * 输入: s = 7, nums = [2,3,1,2,4,3]
 * 输出: 2
 * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
 *
 * 进阶:
 * 如果你已经完成了O(n) 时间复杂度的解法, 请尝试 O(n log n) 时间复杂度的解法。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-size-subarray-sum
 */
public class minSubArrayLen {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了89.60%的用户
     * 内存消耗 :42.8 MB, 在所有 Java 提交中击败了5.07%的用户
     */
    public int minSubArrayLen(int s, int[] nums) {
        if(nums.length == 0)
            return 0;
        int min = Integer.MAX_VALUE;
        int product = nums[0];
        int left = 0, right = 1;
        while(right < nums.length || product >= s){
            if(product >= s){
                min = Math.min(min, right - left);
                product = product - nums[left++];
            }
            else {
                product = product + nums[right++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}
