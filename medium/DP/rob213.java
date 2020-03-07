package medium.DP;

/**
 * @Time : 2020年2月27日00:18:41
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的。
 * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
 * 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
 *
 * 示例 1:
 * 输入: [2,3,2]
 * 输出: 3
 * 解释: 你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
 *
 * 示例 2:
 * 输入: [1,2,3,1]
 * 输出: 4
 * 解释: 你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
 *      偷窃到的最高金额 = 1 + 3 = 4 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/house-robber-ii
 */
public class rob213 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.7 MB, 在所有 Java 提交中击败了5.06%的用户
     */
    public int rob(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0]; // 注意这个特殊情况，只有一家的时候不存在第一家与最后一家的概念，直接返回金额就可以
        return Math.max(rob(nums, n, true), rob(nums, n, false));
    }

    private int rob(int[] nums, int n, boolean rob1) { // 第三个参数表示要不要偷第一家
        int[] amount = new int[n + 1]; // amount[i]的意义是截至第i家，能偷的最多的钱为amount[i]
        amount[0] = 0;
        amount[1] = rob1 ? nums[0] : 0; // 如果指定不偷第一家，那么第一家处取得的最大金额就是0
        for (int i = 2; i <= n; i++) {
            amount[i] = Math.max(amount[i - 1], amount[i - 2] + nums[i - 1]);
        }
        return rob1 ? amount[n - 1] : amount[n]; // 如果指定偷第一家，那么最后一家不能偷，如果第n家不偷，其值等于amount[n - 1]
    }
}
