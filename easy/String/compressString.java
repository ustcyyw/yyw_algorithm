package easy.String;

/**
 * @Time : 2020年3月16日10:28:25
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。比如，字符串aabcccccaaa会变为a2b1c5a3。
 * 若“压缩”后的字符串没有变短，则返回原先的字符串。你可以假设字符串中只包含大小写英文字母（a至z）。
 *
 * 示例1:
 *  输入："aabcccccaaa"
 *  输出："a2b1c5a3"
 *
 * 示例2:
 *  输入："abbccd"
 *  输出："abbccd"
 *  解释："abbccd"压缩后为"a1b2c2d1"，比原字符串长度更长。
 *
 * 提示：
 * 字符串长度在[0, 50000]范围内。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/compress-string-lcci
 */
public class compressString {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.4 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public String compressString(String S) {
        int n = S.length();
        if(n <= 1)
            return S;
        char[] chars = S.toCharArray();
        StringBuilder res = new StringBuilder();
        int i = 0, j = 1;
        for(; j < n; j++){
            if(chars[j] != chars[i]){
                res.append(chars[i]);
                res.append(j - i);
                i = j;
            }
        }
        res.append(chars[i]);
        res.append(j - i);
        return res.length() < n ? res.toString() : S;
    }
}
