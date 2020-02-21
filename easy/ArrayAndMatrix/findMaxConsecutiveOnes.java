package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月10日16:28:12
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个二进制数组， 计算其中最大连续1的个数。
 * <p>
 * 示例 1:
 * 输入: [1,1,0,1,1,1]
 * 输出: 3
 * 解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.
 * <p>
 * 注意：
 * 输入的数组只包含 0 和1。
 * 输入数组的长度是正整数，且不超过 10,000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-consecutive-ones
 */
public class findMaxConsecutiveOnes {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了69.75%的用户
     * 内存消耗 :38.7 MB, 在所有 Java 提交中击败了64.94%的用户
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int result = 0;
        int temp = 0;
        for (int num : nums) {
            if (num == 1)
                temp++;
            else {
                result = Math.max(result, temp);
                temp = 0;
            }
        }
        return Math.max(result, temp);
    }

    /**
     * 执行用时 :4 ms, 在所有 Java 提交中击败了36.84%的用户
     * 内存消耗 :39.4 MB, 在所有 Java 提交中击败了36.06%的用户
     */
    public int findMaxConsecutiveOnes2(int[] nums) {
        int pre = -1; // 用来表示上一个为0元素的索引
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                result = Math.max(result, i - pre - 1);
                pre = i;
            }
        }
        result = Math.max(result, nums.length - pre - 1);
        return result;
    }

}
