package medium.DP;

/**
 * @Time : 2020年2月25日20:14:13
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * 说明：每次只能向下或者向右移动一步。
 *
 * 示例:
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-path-sum
 */
public class minPathSum64 {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了84.49%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了19.78%的用户
     */
    public int minPathSum(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0)
            return 0;
        int row = grid.length;
        int col = grid[0].length;
        // 初始条件

        for(int i = 1; i < col; i++)
            grid[0][i] += grid[0][i - 1];
        for(int i = 1; i < row; i++)
            grid[i][0] += grid[i - 1][0];
        for(int i = 1; i < row; i++){
            for(int j = 1; j < col; j++)
                grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
        }
        return grid[row- 1][col - 1];
    }
}
