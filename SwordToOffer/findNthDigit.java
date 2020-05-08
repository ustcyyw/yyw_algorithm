package SwordToOffer;

/**
 * @Time : 2020年5月6日09:55:42
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 数字以0123456789101112131415…的格式序列化到一个字符序列中。
 * 在这个序列中，第5位（从下标0开始计数）是5，第13位是1，第19位是4，等等。
 * 请写一个函数，求任意第n位对应的数字。
 * <p>
 * 示例 1：
 * 输入：n = 3
 * 输出：3
 * <p>
 * 示例 2：
 * 输入：n = 11
 * 输出：0
 * <p>
 * 限制：
 * 0 <= n < 2^31
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof
 */
public class findNthDigit {
    // 0, 一位数，两位数，三位数……的累计数字的和
    private static int[] sum = {10, 190, 2890, 38890, 488890, 5888890, 68888890, 788888890};

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :35.8 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public int findNthDigit(int n) {
        if (n <= 9) return n;
        int i = 0;
        for (; i < sum.length && n >= sum[i]; i++) ;
        int left = n - sum[i - 1];
        int index = left / (i + 1), offset = left % (i + 1);
        String num = String.valueOf((int) Math.pow(10, i) + index);
        return num.charAt(offset) - '0';
    }
}
