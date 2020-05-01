package medium.Backtracking;

/**
 * @Time : 2020年3月21日16:35:06
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 * <p>
 * 示例:
 * <p>
 * 输入: "25525511135"
 * 输出: ["255.255.11.135", "255.255.111.35"]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/restore-ip-addresses
 */

/**
 * ip地址，是32位的二进制数，为了方便分割成4个8位二进制数，
 * 用'.'隔开，每一部分8位二进制数转化为10进制数方便用户查看
 * 且ip地址可以出现0，但是不能出现01 000 001 010这样的情况
 */
public class restoreIpAddresses {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :38.6 MB, 在所有 Java 提交中击败了7.85%的用户
     */
    private char[] chars;
    private List<String> res;
    private int length;

    public List<String> restoreIpAddresses(String s) {
        res = new ArrayList<>();
        if (s.length() < 4) return res;
        chars = s.toCharArray();
        length = s.length();
        backTrack(new StringBuilder(), 0, -1, 3);
        return res;
    }

    /**
     *
     * @param sb 存放当前已拼凑的字符
     * @param index 当前使用到第几个数字
     * @param num 当前的8位二进制数对应的十进制数字
     * @param dot 还剩余多少个点可以用
     */
    private void backTrack(StringBuilder sb, int index, int num, int dot) {
        if (num > 255) return; // 8位二进制数已经超过上限255的情况 肯定不符合条件
        if (index == length) {
            if (dot == 0)
                res.add(sb.toString());
            return;
        }
        // 当前位置要选用'.'的条件，首先之前选用了数字，其次剩余可用点至少还有一个，其次剩余数字不能超过 3 * dot
        if (num != -1 && dot >= 1 && (length - index) <= 3 * dot) {
            sb.append(".");
            backTrack(sb, index, -1, dot - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        // 当前位置选用数字，要保证后面还有可用的数字与点搭配。并且如果当前已经选择了0为第一个数字，接下来选数字都是不合法的。
        if (length - index >= dot && num != 0) {
            if (num == -1) // 在确定要选数字的时候，num == -1 说明之前没有选数字，那么将其置为0
                num = 0;
            sb.append(chars[index]);
            backTrack(sb, index + 1, num * 10 + (chars[index] - '0'), dot);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
