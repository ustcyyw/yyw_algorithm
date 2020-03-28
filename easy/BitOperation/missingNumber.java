package easy.BitOperation;

/**
 * @Time : 2020年3月27日17:23:05
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个包含 0, 1, 2, ..., n 中 n 个数的序列，找出 0 .. n 中没有出现在序列中的那个数。
 *
 * 示例 1:
 * 输入: [3,0,1]
 * 输出: 2
 *
 * 示例 2:
 * 输入: [9,6,4,2,3,5,7,0,1]
 * 输出: 8
 * 说明:
 * 你的算法应具有线性时间复杂度。你能否仅使用额外常数空间来实现?
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/missing-number
 */


public class missingNumber {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :42.7 MB, 在所有 Java 提交中击败了5.07%的用户
     * 运用等差数列的做法
     */
    public int missingNumber(int[] nums) {
        int sum = 0;
        for(int i : nums)
            sum += i;
        return nums.length * (nums.length + 1) / 2- sum;
    }

    /**
     * 使用位运算
     * 1.异或满足交换律
     * 2.一个数与自己异或等于0。
     * 3.一个数与0异或得到它本身
     * 先将0,1,2……n进行异或，再将nums中的元素与刚才的结果异或。除了缺失的那个数，其余都与自己通过交换律进行了异或变为0
     * 仅有的那个数与一堆0异或，还是它本身
     *
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :42.6 MB, 在所有 Java 提交中击败了5.07%的用户
     */
    public int missingNumber2(int[] nums) {
        int res = 0;
        for(int i = 1; i <= nums.length; i++)
            res ^= i;
        for(int i : nums)
            res ^= i;
        return res;
    }
}
