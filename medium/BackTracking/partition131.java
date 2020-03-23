package medium.Backtracking;

/**
 * @Time : 2020年3月21日19:23:58
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
 * 返回 s 所有可能的分割方案。
 * <p>
 * 示例:
 * 输入: "aab"
 * 输出:
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-partitioning
 */
public class partition131 {
    /**
     * 执行用时 :17 ms, 在所有 Java 提交中击败了6.46%的用户
     * 内存消耗 :42.7 MB, 在所有 Java 提交中击败了6.22%的用户
     * 慢如狗了。
     */
    private List<List<String>> res;
    private char[] chars;
    private int n;
    private boolean[][] isP;
    public List<List<String>> partition(String s) {
        res = new ArrayList<>();
        if(s == null || s.length() == 0)
            return res;
        chars = s.toCharArray();
        for(int i = 1; i <= s.length(); i++) // i 表示拆分成多少个子串
            backTrack(new String[i], i, 0, -1);
        return res;
    }

    /**
     *
     * @param k 拆分成多少个子串
     * @param curIndex 当前在找第几个子串 从0开始
     * @param pre 上一个子串最后一个字符在chars中的索引
     * @param temp 存放子串的数组
     */
    private void backTrack(String[] temp, int k, int curIndex, int pre){
        if(curIndex == k){
            if(pre == chars.length - 1){
                List<String> tempRes = new ArrayList<>();
                for(String i : temp)
                    tempRes.add(i);
                res.add(tempRes);
            }
            return;
        }
        for(int length = 1; length <= chars.length - pre - k + curIndex; length++){
            if(isValid(pre + 1, pre + length)){
                temp[curIndex] = String.valueOf(chars, pre + 1, length);
                backTrack(temp, k, curIndex + 1, pre + length);
            }
        }
    }

    private boolean isValid(int lo, int hi){
        while(lo < hi){
            if(chars[lo++] != chars[hi--])
                return false;
        }
        return true;
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了99.86%的用户
     * 内存消耗 :42.1 MB, 在所有 Java 提交中击败了7.66%的用户
     */

    public List<List<String>> partition2(String s) {
        res = new ArrayList<>();
        if (s == null || s.length() == 0)
            return res;
        chars = s.toCharArray();
        n = chars.length;
        markP();
        backTrack2(new Stack<String>(), 0);
        return res;
    }

    private void backTrack2(Stack<String> stack, int curIndex) {
        if (curIndex == n) {
            List<String> tempRes = new ArrayList<>(stack);
            res.add(tempRes);
        }
        for (int i = curIndex; i < n; i++){
            if(isP[curIndex][i]){
                stack.push(String.valueOf(chars,curIndex, i - curIndex + 1));
                backTrack2(stack, i + 1);
                stack.pop();
            }
        }
    }

    private void markP() {
        isP = new boolean[n][n];
        for (int i = 0; i < n; i++)
            isP[i][i] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 1; i - j >= 0 && i + j < n; j++) { // 以字符为中心的子串的判断
                if (chars[i - j] != chars[i + j]) break;
                isP[i - j][i + j] = true;
            }
            for (int j = 0; i - j >= 0 && i + j + 1 < n; j++) {
                if (chars[i - j] != chars[i + j + 1]) break;
                isP[i - j][i + j + 1] = true;
            }
        }
    }

    public static void main(String[] args) {
        partition131 p = new partition131();
        System.out.println(p.partition("aab"));
    }
}
