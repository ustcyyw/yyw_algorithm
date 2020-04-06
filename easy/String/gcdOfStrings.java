package easy.String;

/**
 * @Time : 2020年3月12日11:25:56
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 对于字符串 S 和 T，只有在 S = T + ... + T（T 与自身连接 1 次或多次）时，我们才认定 “T 能除尽 S”。
 * 返回最长字符串 X，要求满足 X 能除尽 str1 且 X 能除尽 str2。 
 *
 * 示例 1：
 * 输入：str1 = "ABCABC", str2 = "ABC"
 * 输出："ABC"
 *
 * 示例 2：
 * 输入：str1 = "ABABAB", str2 = "ABAB"
 * 输出："AB"
 *
 * 示例 3：
 * 输入：str1 = "LEET", str2 = "CODE"
 * 输出：""
 *  
 *
 * 提示：
 * 1 <= str1.length <= 1000
 * 1 <= str2.length <= 1000
 * str1[i] 和 str2[i] 为大写英文字母
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/greatest-common-divisor-of-strings
 */
public class gcdOfStrings {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了93.98%的用户
     * 内存消耗 :38.7 MB, 在所有 Java 提交中击败了8.80%的用户
     */
    public String gcdOfStrings(String str1, String str2) {
        int n1 = str1.length(), n2 = str2.length();
        char[] chars1 = str1.toCharArray(), chars2 = str2.toCharArray();
        int max = n1 > n2 ? maxProduct(n1, n2) : maxProduct(n2, n1);
        while(max >= 1){
            if(n1 % max == 0 && n2 % max == 0){
                if(isValid(chars1, chars1, n1, max) && isValid(chars2, chars1, n2, max))
                    return str1.substring(0, max);
            }
            max--;
        }
        return "";
    }

    private boolean isValid(char[] chars, char[] target, int n, int length){
        for(int i = 0, j = 0; i < n; i++, j = (j + 1) % length){
            if(chars[i] != target[j])
                return false;
        }
        return true;
    }

    private int maxProduct(int p, int q){
        int r = p % q;
        if(r == 0) return q;
        else return maxProduct(q, r);
    }

    /**
     * 官方的解法
     * 执行用时 :1 ms, 在所有 Java 提交中击败了93.98%的用户
     * 内存消耗 :38.1 MB, 在所有 Java 提交中击败了20.00%的用户
     *
     * 如果 str1 + str2 与 str2 + str1 相等 则两个字符串长度的最大公约数就是解。
     * 必要性：
     * str1 = m * T, str2 = n * T
     * str1 + str2 = (m + n) * T, str2 + str1 = (n + m) * T。对于字符串来说相等。
     * 充分性：
     * 比如一个长度为8，一个长度为4，那假设最优解是2，不是4，对于长度为8的可以理解成，要有4个这样的前缀，长度为4的要有2个这样的前缀。
     * 然后这时候4个这样的前缀，和2个这样的前缀，实际上又可以有共同的约数2来合并，而且不合并的话实际上就不是最优的长度。
     * 无论两个数有多少约数的情况都可以这样类推合并。因此最优解就是在最大公约数，当然，前提你要确定是有解的。
     */
    public String gcdOfStrings2(String str1, String str2) {
        String temp = str1 + str2;
        if(!temp.equals(str2 + str1))
            return "";
        int p = str1.length(), q = str2.length();
        if(p > q)
            return str1.substring(0, maxProduct(p, q));
        else
            return str1.substring(0, maxProduct(q, p));
    }
}
