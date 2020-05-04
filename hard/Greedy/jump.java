package hard.Greedy;

/**
 * @Time : 2020年5月4日14:18:42
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 示例:
 * 输入: [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 *
 * 说明:
 * 假设你总是可以到达数组的最后一个位置。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/jump-game-ii。
 */
public class jump {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了94.93%的用户
     * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了5.00%的用户
     */
    public int jump(int[] nums) {
        int n = nums.length;
        int[] count = new int[n];
        int start = 0;
        for(int i = 0; i < n && start < n; i++){
            if(i + nums[i] < start)
                continue;
            for(int j = start; j < n && j <= i + nums[i]; j++)
                count[j] = count[i] + 1;
            start = i + nums[i] + 1;
        }
        return count[n - 1] - 1;
    }
}
