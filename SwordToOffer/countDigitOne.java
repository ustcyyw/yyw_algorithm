package SwordToOffer;

/**
 * @Time : 2020年5月5日20:11:25
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
 * 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。
 * <p>
 * 示例 1：
 * 输入：n = 12
 * 输出：5
 * <p>
 * 示例 2：
 * 输入：n = 13
 * 输出：6
 *  
 * <p>
 * 限制：
 * 1 <= n < 2^31
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/1nzheng-shu-zhong-1chu-xian-de-ci-shu-lcof
 */
public class countDigitOne {
    private static int[] count = {0, 1, 20, 300, 4000, 50000, 600000, 7000000, 80000000, 900000000};

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.6 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public int countDigitOne(int n) {
        int res = 0, index = 0, pow = 1, pre = 0;
        while (n != 0) {
            int num = n % 10;
            if (num == 1) {
                res += pre + 1 + count[index];
            } else if (num > 1) {
                res += pow + num * count[index];
            }
            pre = pre + num * pow;
            pow *= 10;
            index++;
            n /= 10;
        }
        return res;
    }
}
