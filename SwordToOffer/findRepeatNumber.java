package SwordToOffer;

/**
 * @Time : 2020年4月14日00:19:19
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 找出数组中重复的数字。
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
 * 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 * 示例 1：
 * 输入：
 * [2, 3, 1, 0, 2, 5, 3]
 * 输出：2 或 3
 *
 * 限制：
 * 2 <= n <= 100000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof
 */
public class findRepeatNumber {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了92.46%的用户
     * 内存消耗 :47.4 MB, 在所有 Java 提交中击败了100.00%的用
     */
    public int findRepeatNumber(int[] nums) {
        int[] count = new int[nums.length];
        for(int i : nums){
            if(count[i] == 1)
                return i;
            count[i] = 1;
        }
        return -1;
    }
}
