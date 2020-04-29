package medium.Backtracking;

/**
 * @Time : 2020年3月15日10:22:30
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个包含了一些 0 和 1的非空二维数组 grid , 一个 岛屿 是由四个方向 (水平或垂直) 的 1 (代表土地) 构成的组合。
 * 你可以假设二维矩阵的四个边缘都被水包围着。
 * 找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为0。)
 *
 * 示例 1:
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * 对于上面这个给定矩阵应返回 6。注意答案不应该是11，因为岛屿只能包含水平或垂直的四个方向的‘1’。
 *
 * 示例 2:
 * [[0,0,0,0,0,0,0,0]]
 * 对于上面这个给定的矩阵, 返回 0。
 *
 * 注意: 给定的矩阵grid 的长度和宽度都不超过 50。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-area-of-island
 */
public class maxAreaOfIsland {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了82.11%的用户
     * 内存消耗 :40.8 MB, 在所有 Java 提交中击败了92.44%的用户
     */
    private static int[][] dires = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int curArea;
    private int row, col;
    public int maxAreaOfIsland(int[][] grid) {
        row = grid.length;
        if(row == 0)
            return 0;
        col = grid[0].length;
        int maxArea = 0;
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(grid[i][j] != 0){
                    curArea = 0;
                    backTrack(grid, i, j);
                    maxArea = Math.max(maxArea, curArea);
                }
            }
        }
        return maxArea;
    }

    private void backTrack(int[][] grid, int x, int y){
        grid[x][y] = 0;
        curArea++;
        for(int[] dire : dires){
            int newX = x + dire[0];
            int newY = y + dire[1];
            if(isIn(newX, newY) && grid[newX][newY] != 0)
                backTrack(grid, newX, newY);
        }
    }

    private boolean isIn(int x, int y){
        return x >= 0 && x < row && y >= 0 && y < col;
    }
}
