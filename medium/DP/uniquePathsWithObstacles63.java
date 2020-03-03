package medium.DP;

/**
 * @Time : 2020年2月26日22:41:54
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
 * 说明：m 和 n 的值均不超过 100。
 * <p>
 * 示例 1:
 * 输入:
 * [
 *   [0,0,0],
 *   [0,1,0],
 *   [0,0,0]
 * ]
 * 输出: 2
 * <p>
 * 解释:
 * 3x3 网格的正中间有一个障碍物。
 * 从左上角到右下角一共有 2 条不同的路径：
 * 1. 向右 -> 向右 -> 向下 -> 向下
 * 2. 向下 -> 向下 -> 向右 -> 向右
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-paths-ii
 */
public class uniquePathsWithObstacles63 {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了87.11%的用户
     * 内存消耗 :38.1 MB, 在所有 Java 提交中击败了46.73%的用户
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[][] count = new int[row][col];
        // 边界条件，注意在最上面或者最左边，一旦某处地方出现障碍，其右边/下边都不可达 count为0
        for (int i = 0; i < col && obstacleGrid[0][i] != 1; i++)
            count[0][i] = 1;
        for (int i = 0; i < row && obstacleGrid[i][0] != 1; i++)
            count[i][0] = 1;
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++)
                count[i][j] = obstacleGrid[i][j] == 1 ? 0 : count[i - 1][j] + count[i][j - 1]; // 障碍处，不可达，count为0
        }
        return count[row - 1][col - 1];
    }

    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[] count = new int[col];
        count[0] = 1; // 初状态 初始的位置总是可以到达的
        for (int i = 0; i < row; i++) {
            count[0] = obstacleGrid[i][0] == 1 ? 0 : count[0];
            for (int j = 1; j < col; j++)
                count[j] = obstacleGrid[i][j] == 1 ? 0 : count[j] + count[j - 1]; // 分别对应上方格子与左边格子，也要考虑障碍物
        }
        return count[col - 1];
    }
}
