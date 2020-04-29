# 200. 岛屿数量
### 原题
给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
此外，你可以假设该网格的四条边均被水包围。 

示例 1:
输入:
11110
11010
11000
00000
输出: 1

示例 2:
输入:
11000
11000
00100
00011
输出: 3

解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/number-of-islands)：https://leetcode-cn.com/problems/number-of-islands

### 解法
```java
public class numIslands {
    private int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int row, col;
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
```
思路分析：
* 本题与[695. 岛屿的最大面积](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BackTracking/maxAreaOfIsland.md)基本一致，直接参照695题。
* 本题要找到有多少个岛，只需找到岛的某一个点，然后`count++`，从这个点开始遍历整个岛。然后再去搜索下一个岛。处理已访问过的点的方式与695题一致：将`gird[x][y]`设置为0即可。
* 时间复杂度为$O(n)$，因为最多对每一个坐标进行两次访问。空间复杂度取决于递归调用所占的空间。

运行结果：

 * 执行用时 :3 ms, 在所有 Java 提交中击败了55.11%的用户
 * 内存消耗 :41.6 MB, 在所有 Java 提交中击败了8.21%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹