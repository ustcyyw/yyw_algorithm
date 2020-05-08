package SwordToOffer;

/**
 * @Time : 2020年4月14日00:33:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 *
 * 示例 1：
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 *
 * 限制：
 * 0 <= s 的长度 <= 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof
 */
public class replaceSpace {
    public String replaceSpace(String s) {
        return s.replace(" ", "%20");
    }

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.2 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public String replaceSpace2(String s) {
        StringBuilder res = new StringBuilder();
        for(char c : s.toCharArray()){
            if(c == ' ')
                res.append("%20");
            else
                res.append(c);
        }
        return res.toString();
    }
}
