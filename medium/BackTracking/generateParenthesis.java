package medium.Backtracking;

/**
 * @Time : 2020年3月6日11:17:38
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 * 例如，给出 n = 3，生成结果为：
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/generate-parentheses
 */
public class generateParenthesis {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.02%的用户
     * 内存消耗 :39.2 MB, 在所有 Java 提交中击败了5.04%的用户
     * 这也是个组合问题 所以想到用回溯来解决
     */
    private int n;
    private List<String> res;
    public List<String> generateParenthesis(int n) {
        this.n = n;
        res = new ArrayList<>();
        backTrack(n, n, new StringBuilder());
        return res;
    }

    /**
     * 左括号如果还剩余 1个 右括号也剩余1个，那么必须先使用左括号。左括号还剩1个，右括号还剩3个，那么都可以使用
     * @param left 剩余可用的左括号
     * @param right 剩余可用的右括号
     */
    private void backTrack(int left, int right, StringBuilder sb){
        if(right == 0){
            res.add(sb.toString());
            return;
        }
        if(left >= right){
            sb.append('(');
            backTrack(left - 1, right, sb);
            sb.deleteCharAt(sb.length() - 1);
        } else {
            if(left > 0){ // 这里要注意 如果没有剩余的左括号 就不能递归 否则无限递归了
                sb.append('(');
                backTrack(left - 1, right, sb);
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(')'); // 为什么右括号不需要检查剩余 因为递归调用一开始的条件，当右括号无剩余时已经找到需要的组合了，就返回了
            backTrack(left, right - 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
