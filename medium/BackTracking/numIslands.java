package medium.Backtracking;

/**
 * @Time : 2020年2月24日22:11:50
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。
 * 一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。
 * 你可以假设网格的四个边均被水包围。
 *
 * 示例 1:
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出: 1
 *
 * 示例 2:
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * 输出: 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 */
public class numIslands {
    private int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int row, col;
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了55.11%的用户
     * 内存消耗 :41.6 MB, 在所有 Java 提交中击败了8.21%的用户
     */
    public int numIslands(char[][] grid) {
        if(grid.length == 0 || grid[0].length == 0)
            return 0;
        row = grid.length;
        col = grid[0].length;
        int count = 0;
        for(int i = 0; i < row; i++){
            for (int j = 0; j < col; j++)
                if(grid[i][j] == '1'){
                    count++;
                    searchLand(grid, i, j);
                }
        }
        return count;
    }

    private boolean inArea(int x, int y){
        return x >= 0 && x < row && y >= 0 && y < col;
    }

    private void searchLand(char[][] grid, int x, int y){
        grid[x][y] = 0;
        for(int i = 0; i < 4; i++){
            int newX = x + direction[i][0];
            int newY = y + direction[i][1];
            // 第2个条件，因为我们要把相连的陆地在一次递归中都找到且标注已访问，函数第一行就是进行标注，那么已经被标注的就不必再访问。
            if(inArea(newX, newY) && grid[newX][newY] == '1')
                searchLand(grid, newX, newY);
        }
    }
}
