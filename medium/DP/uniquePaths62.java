package medium.DP;

/**
 * @Time : 2020年2月25日15:29:39
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * 问总共有多少条不同的路径？
 * 说明：m 和 n 的值均不超过 100。
 *
 * 示例 1:
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向右 -> 向下
 * 2. 向右 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向右
 *
 * 示例 2:
 * 输入: m = 7, n = 3
 * 输出: 28
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths
 */
public class uniquePaths62 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.1 MB, 在所有 Java 提交中击败了5.10%的用户
     */
    public int uniquePaths(int m, int n) {
        int[][] count = new int[n][m];
        for(int[] row : count)
            row[0] = 1;
        Arrays.fill(count[0], 1);
        for(int row = 1; row < n; row++)
            for(int col = 1; col < m; col++)
                count[row][col] = count[row - 1][col] + count[row][col - 1];
        return count[n - 1][m - 1];
    }

    public int uniquePaths2(int m, int n) {
        int[] count = new int[m];
        Arrays.fill(count, 1);
        for(int row = 1; row < n; row++)
            for(int col = 1; col < m; col++)
                count[col] = count[col] + count[col - 1]; // 分别对应上格子与左边格子
        return count[m - 1];
    }
}
