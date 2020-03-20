package medium.DP;

/**
 * @Time : 2020年3月13日20:54:21
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个整数数组 nums ，找出一个序列中乘积最大的连续子序列（该序列至少包含一个数）。
 *
 * 示例 1:
 * 输入: [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 *
 * 示例 2:
 * 输入: [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-product-subarray
 */
public class maxProduct {
    /**
     * 评论区解法
     * DP 不像求子序列的和 因为这里的负数会改变符号。所以不能只维持最大的。
     */
    public int maxProduct(int[] nums) {
        int res = Integer.MIN_VALUE;
        int min = 1, max = 1; // 表示以i这个元素为结尾的子串的最大/最小积
        for(int i = 0; i < nums.length; i++){
            if(nums[i] < 0){ // 当第i个元素为负数时，截止上一位最大的乘上nums[i]反而可能是最小的；截止上一位最小的乘上nums[i]反而可能是最大的。
                int temp = min; // 所以将min max的值进行交换，这样才能在后续使用统一的代码。
                min = max;
                max = temp;
            }
            max = Math.max(max * nums[i], nums[i]);
            min = Math.min(min * nums[i], nums[i]);
            res = Math.max(res, max);
        }
        return res;
    }
}
