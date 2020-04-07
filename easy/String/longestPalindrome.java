package easy.String;

/**
 * @Time : 2020年2月15日14:06:47
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
 * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
 *
 * 注意:
 * 假设字符串的长度不会超过 1010。
 *
 * 示例 1:
 * 输入:
 * "abccccdd"
 * 输出:
 * 7
 * 解释:
 * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-palindrome
 */
public class longestPalindrome {
    /**
     *执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了5.22%的用户
     * 一个字符出现偶数次，都可以用上；一个字符出现了奇数次，则只能用出现次数-1的元素。
     * 不过有一个出现奇数次的字符可以全用，就是把他放在中间。
     * 所以当没有出现奇数次的字符时，直接返回 s.length()， 否则返回 s.length() - countOdd + 1
     */
    public int longestPalindrome(String s) {
        int[] count = new int[58]; // 大小写字母中间还有6个其它字符
        for (char c : s.toCharArray())
            count[c - 65] += 1;
        int countOdd = 0;
        for (int i : count) {
            if (i % 2 != 0)
                countOdd++;
        }
        if(countOdd == 0)
            return s.length();
        return s.length() - countOdd + 1;
    }
}
