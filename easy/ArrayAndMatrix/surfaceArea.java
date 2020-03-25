package easy.ArrayAndMatrix;

/**
 * @Time : 2020年3月25日19:40:14
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 在 N * N 的网格上，我们放置一些 1 * 1 * 1  的立方体。
 * 每个值 v = grid[i][j] 表示 v 个正方体叠放在对应单元格 (i, j) 上。
 * 请你返回最终形体的表面积。
 *
 * 示例 1：
 * 输入：[[2]]
 * 输出：10
 *
 * 示例 2：
 * 输入：[[1,2],[3,4]]
 * 输出：34
 *
 * 示例 3：
 * 输入：[[1,0],[0,2]]
 * 输出：16
 *
 * 示例 4：
 * 输入：[[1,1,1],[1,0,1],[1,1,1]]
 * 输出：32
 *
 * 示例 5：
 * 输入：[[2,2,2],[2,1,2],[2,2,2]]
 * 输出：46
 *  
 *
 * 提示：
 * 1 <= N <= 50
 * 0 <= grid[i][j] <= 50
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/surface-area-of-3d-shapes
 */
public class surfaceArea {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了98.69%的用户
     * 内存消耗 :41 MB, 在所有 Java 提交中击败了90.44%的用户
     */
    public int surfaceArea(int[][] grid) {
        int sum = 0; // 用来记录几个格子上有放立方体
        int n = grid.length;
        for(int i = 0; i < n; i++){
            if(grid[i][0] != 0) sum += 2;
            for(int j = 1; j < n; j++){
                if(grid[i][j] != 0) sum += 2;
                sum += Math.abs(grid[i][j] - grid[i][j - 1]) + Math.abs(grid[j][i] - grid[j - 1][i]);
            }
            sum += grid[i][n - 1] + grid[i][0] + grid[0][i] + grid[n - 1][i];
        }
        return sum;
    }
}
