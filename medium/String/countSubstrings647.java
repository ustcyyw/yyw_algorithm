package medium.String;

/**
 * @Time : 2020年2月15日15:15:18
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
 * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
 * <p>
 * 示例 1:
 * 输入: "abc"
 * 输出: 3
 * 解释: 三个回文子串: "a", "b", "c".
 * <p>
 * 示例 2:
 * 输入: "aaa"
 * 输出: 6
 * 说明: 6个回文子串: "a", "a", "a", "aa", "aa", "aaa".
 * <p>
 * 注意:
 * 输入的字符串长度不会超过1000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindromic-substrings
 */

/**
 * 后两种做法都没有冗余信息，也是相当于递推，从回文的最小单位逐渐外扩。
 */
public class countSubstrings647 {
    /**
     * 执行用时 :79 ms, 在所有 Java 提交中击败了14.29%的用户
     * 内存消耗 :34.3 MB, 在所有 Java 提交中击败了81.10%的用户
     */
    public int countSubstrings(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        for (int i = 2; i <= s.length(); i++) {
            for (int j = 0; j + i - 1 < s.length(); j++)
                if (isPalindrome(chars, j, j + i - 1))
                    result++;
        }
        return result + s.length();
    }

    private boolean isPalindrome(char[] chars, int start, int end) {
        for (int i = start, j = end; j > i; i++, j--) {
            if (chars[i] != chars[j])
                return false;
        }
        return true;
    }

    /**
     * 使用动态规划 flag[i][j] 表示从 i-j的子串是否为回文串
     * 状态转移方程为 flag[i][j] = flag[i + 1][j - 1] && char[i] == char[j]
     * 边界条件 i = j 或者 i + 1 = j且char[i]==char[j] 时 flag[i][j] = true
     * <p>
     * 执行用时 :11 ms, 在所有 Java 提交中击败了28.82%的用户
     * 内存消耗 :45.6 MB, 在所有 Java 提交中击败了5.04%的用户
     */
    public int countSubstrings2(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        boolean[][] flag = new boolean[chars.length][chars.length];
        for (int j = 0; j < chars.length; j++) {
            for (int i = j; i >= 0; i--) { // i j 的值设定保证了只使用了 flag[][]的上三角，没有出现重复
                if (chars[i] == chars[j] && (j - i < 2 || flag[i + 1][j - 1])) {
                    flag[i][j] = true;
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * 长度为N的字符串，可能有2N+1个回文字符串的对称中心，N个字母 N-1个字母中间
     * 从回文中心向外扩展。
     *
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :34.3 MB, 在所有 Java 提交中击败了73.06%的用户
     */
    public int countSubstrings3(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length * 2 - 1; i++) { // 对每个可能的回文中心进行循环
            int left = i / 2; // 当中心是两个字母的间歇时i%2 = 1；当中心是字母时 left==right都落在该字母的位置
            int right = left + i % 2;
            while(left >= 0 && right < chars.length && chars[left] == chars[right]){
                left--;
                right++;
                result++;
            }
        }
        return result;
    }
}
