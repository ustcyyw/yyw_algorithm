package medium.Backtracking;

/**
 * @Time : 2020年2月23日15:32:56
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 * 示例:
 * 输入: n = 4, k = 2
 * 输出:
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combinations
 */
public class combine77 {
    private List<List<Integer>> result = null;

    /**
     * 执行用时 :9 ms, 在所有 Java 提交中击败了72.85%的用户
     * 内存消耗 :42.8 MB, 在所有 Java 提交中击败了11.56%的用户
     */
    public List<List<Integer>> combine(int n, int k) {
        result = new ArrayList<>();
        if (k == 0 || k > n)
            return result;
        backTrack(n, k, 0, new int[k], 0);
        return result;
    }

    private void backTrack(int n, int k, int pre, int[] temp, int curIndex) {
        if (curIndex == k) {
            List<Integer> tempRes = new ArrayList<>();
            for (int i : temp)
                tempRes.add(i);
            result.add(tempRes);
            return;
        }

        for (int i = pre + 1; i <= n; i++) {
            temp[curIndex] = i;
            backTrack(n, k, i, temp, curIndex + 1);
        }
    }

    /**
     * 剪枝过程就是：把 i <= n 改成 i <= n - (k - curIndex) + 1
     * 题解大佬的做法 在backTrack中的for循环 i的截至为 i <= n - (k - curIndex) + 1
     * 可以这么想：
     * 当剩余要选的数为2时，当前可选的最大的数为n-1，因为如果当前选了n，则下一个数没法选
     * 当剩余要选的数为3时，当前可选的最大的数为n-2，当前选了n-1则下一个数选n，但最后一个数就没法选了
     * 所以剩余x位时，可选最大数位 n - x + 1
     * 在选择curIndex位时，剩余要选的数位 k - curIndex
     */
}
