package easy.String;

/**
 * @Time : 2020年2月15日17:25:26
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * 输入: 121
 * 输出: true
 * 示例 2:
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * 示例 3:
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 进阶:
 * 你能不将整数转为字符串来解决这个问题吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-number
 */
public class isPalindrome {
    /**
     * 执行用时 :14 ms, 在所有 Java 提交中击败了18.32%的用户
     * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了21.39%的用户
     */
    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        String strX = String.valueOf(x);
        for (int i = 0, j = strX.length() - 1; j > i; i++, j--) {
            if (strX.charAt(i) != strX.charAt(j))
                return false;
        }
        return true;
    }

    /**
     * 执行用时 :12 ms, 在所有 Java 提交中击败了33.42%的用户
     * 内存消耗 :46.2 MB, 在所有 Java 提交中击败了5.01%的用户
     * 不转化为字符串的做法
     */
    public boolean isPalindrome2(int x) {
        if (x < 0)
            return false;
        List<Integer> nums = new ArrayList<>();
        while (x != 0) {
            nums.add(x % 10);
            x /= 10;
        }
        int size = nums.size();
        for (int i = 0, j = size - 1; j > i; i++, j--) {
            if (!nums.get(i).equals(nums.get(j)))
                return false;
        }
        return true;
    }

    /**
     * 官方解法
     */
    public boolean isPalindrome3(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) // x除以10余数为0，则其个位为0，如果他不为0，其最高位不可能为0，所以不可能会回文数
            return false;
        int halfReverse = 0;
        while (x > halfReverse) { // 这个条件 很妙, 在翻转过程中x < halfReverse 说明已经翻转了一半的数位。 举例子看返回值与这个循环条件
            halfReverse = halfReverse * 10 + x % 10;
            x /= 10;
        }
        return x == halfReverse || x == halfReverse / 10; // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
    }
}
