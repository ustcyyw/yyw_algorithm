package medium.ArrayAndMatrix;

/**
 * @Time : 2020年2月13日17:28:23
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定两个整数 n 和 k，你需要实现一个数组，这个数组包含从 1 到 n 的 n 个不同整数，同时满足以下条件：
 * ① 如果这个数组是 [a1, a2, a3, ... , an] ，那么数组 [|a1 - a2|, |a2 - a3|, |a3 - a4|, ... , |an-1 - an|] 中应该有且仅有 k 个不同整数；.
 * ② 如果存在多种答案，你只需实现并返回其中任意一种.
 * <p>
 * 示例 1:
 * 输入: n = 3, k = 1
 * 输出: [1, 2, 3]
 * 解释: [1, 2, 3] 包含 3 个范围在 1-3 的不同整数， 并且 [1, 1] 中有且仅有 1 个不同整数 : 1
 *  
 * 示例 2:
 * 输入: n = 3, k = 2
 * 输出: [1, 3, 2]
 * 解释: [1, 3, 2] 包含 3 个范围在 1-3 的不同整数， 并且 [2, 1] 中有且仅有 2 个不同整数: 1 和 2
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/beautiful-arrangement-ii
 */
public class constructArray {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :44.8 MB, 在所有 Java 提交中击败了5.34%的用户
     */
    public int[] constructArray(int n, int k) {
        int[] result = new int[n];
        result[0] = n;
        for (int i = k + 1, temp = n - k - 1; i < n; i++, temp--)
            result[i] = temp;
        for (int i = 1, flag = -1, temp = k; i < k + 1; i++) {
            result[i] = result[i - 1] + temp * flag;
            flag = -1 * flag;
            temp--;
        }
        return result;
    }
}
