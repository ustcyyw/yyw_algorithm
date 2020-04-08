package easy.TwoPoint;

/**
 * @Time : 2020年3月10日13:13:59
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个非空字符串 s，最多删除一个字符。判断是否能成为回文字符串。
 *
 * 示例 1:
 * 输入: "aba"
 * 输出: True
 * 示例 2:
 *
 * 输入: "abca"
 * 输出: True
 * 解释: 你可以删除c字符。
 *
 * 注意:
 * 字符串只包含从 a-z 的小写字母。字符串的最大长度是50000。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-palindrome-ii
 */
public class validPalindrome {
    /**
     * 执行用时 :9 ms, 在所有 Java 提交中击败了75.64%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.09%的用户
     * 双指针的做法 并且删除一个元素 优先删左边和优先删右边是不一样的。
     */
    public boolean validPalindrome(String s) {
        return isValid(s, true) || isValid(s, false);
    }

    private boolean isValid(String s, boolean deleteLeft){
        boolean flag = true;
        for(int i = 0, j = s.length() - 1; i < j; i++, j--){
            if(s.charAt(i) != s.charAt(j)){
                if(flag){
                    if(deleteLeft){
                        if(s.charAt(i + 1) == s.charAt(j)){
                            i++;
                            flag = false;
                        }
                        else return false;
                    }
                    else {
                        if(s.charAt(i) == s.charAt(j - 1)){
                            j--;
                            flag = false;
                        }
                        else return false;
                    }
                }
                else return false;
            }
        }
        return true;
    }

    /**
     *官方标答的做法
     * 执行用时 :9 ms, 在所有 Java 提交中击败了76.34%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.09%的用户
     * 主要是没有生成字符数组 节约了2ms
     */
    public boolean validPalindrome2(String s) {
        for(int i = 0, j = s.length() - 1; i < j; i++, j--){
            if(s.charAt(i) != s.charAt(j)){
                // 第一次出现不等的情况，删左边元素或者删右边元素再检查剩余元素是否为回文
                return isPalindrome(s, i + 1, j) || isPalindrome(s, i, j - 1);
            }
        }
        //循环中没有出现左右两边不相等的情况，不需要删除就是回文，返回true
        return true;
    }

    private boolean isPalindrome(String s, int lo, int hi){
        for(; lo < hi; lo++, hi--){
            if(s.charAt(lo) != s.charAt(hi))
                return false;
        }
        return true;
    }
}
