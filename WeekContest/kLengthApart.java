package Niuke;

/**
 * @Time : 2020年5月3日10:38:14
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给你一个由若干 0 和 1 组成的数组 nums 以及整数 k。
 * 如果所有 1 都至少相隔 k 个元素，则返回 True ；否则，返回 False 。
 *
 * 示例 1：
 * 输入：nums = [1,0,0,0,1,0,0,1], k = 2
 * 输出：true
 * 解释：每个 1 都至少相隔 2 个元素。
 *
 * 示例 2：
 * 输入：nums = [1,0,0,1,0,1], k = 2
 * 输出：false
 * 解释：第二个 1 和第三个 1 之间只隔了 1 个元素。
 *
 * 示例 3：
 * 输入：nums = [1,1,1,1,1], k = 0
 * 输出：true
 *
 * 示例 4：
 * 输入：nums = [0,1,0,1], k = 1
 * 输出：true
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * 0 <= k <= nums.length
 * nums[i] 的值为 0 或 1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/check-if-all-1s-are-at-least-length-k-places-away
 */
public class kLengthApart {
    public boolean kLengthApart(int[] nums, int k) {
        for (int pre = -100000, next = 0; next < nums.length; next++) {
            if (nums[next] == 1) {
                if (next - pre <= k)
                    return false;
                pre = next;
            }
        }
        return true;
    }
}
