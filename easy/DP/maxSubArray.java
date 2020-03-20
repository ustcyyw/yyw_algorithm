package easy.DP;

/**
 * @Time : 2020年3月7日12:48:07
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * 进阶:
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-subarray
 */
public class maxSubArray {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.6 MB, 在所有 Java 提交中击败了8.46%的用户
     * 贪心算法
     */
    public int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(sum < 0) // 如果sum已经是负资产了，说明前面选定的子序列对于后面不论子序列在哪里结束都是负面影响，从当前元素开始尝试
                sum = nums[i];
            else // 如果sum是正的，当前元素为必然要加上，当前元素为负则加上看sum还是不是正资产，判断交由下一次循环来判断。
                sum += nums[i];
            res = Math.max(res, sum); // 因为每次循环都在记录最大和，所以不会出现遗漏。
        }
        return res;
    }

    /**
     * DP
     * 执行用时 :1 ms, 在所有 Java 提交中击败了98.60%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了8.61%的用户
     *
     * 可以和152对照着看
     */
    public int maxSubArray2(int[] nums) {
       int[] count = new int[nums.length]; // count[i] 表示 0~i 这些元素 所有以nums[i]为结尾的连续子序列中 的最大和
       count[0] = nums[0];
       for(int i = 1; i < nums.length; i++){
           count[i] = Math.max(count[i - 1] + nums[i], nums[i]);
       }
       int res = Integer.MIN_VALUE;
       for(int i : count)
           res = Math.max(res, i);
       return res;
    }
}
