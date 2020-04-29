# 695. 岛屿的最大面积
### 原题
给定一个包含了一些 0 和 1 的非空二维数组 grid 。
一个 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在水平或者竖直方向上相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
找到给定的二维数组中最大的岛屿面积。(如果没有岛屿，则返回面积为 0 。) 

示例 1:

```
[[0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]]
```

对于上面这个给定矩阵应返回 6。注意答案不应该是 11 ，因为岛屿只能包含水平或垂直的四个方向的 1 。

示例 2:
[[0,0,0,0,0,0,0,0]]
对于上面这个给定的矩阵, 返回 0

注意: 给定的矩阵grid 的长度和宽度都不超过 50。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/max-area-of-island)：https://leetcode-cn.com/problems/max-area-of-island

### 解法
```java
public class maxAreaOfIsland {
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

```
思路分析：
* 二维平面，并且认为相邻就是上下左右四个方向。这一类二维平面，上下左右移动的题都是同样一个套路：
    * 判断某个坐标是否在区域内，保证数组不会越界，用成员变量`private int row, col;`分别表示矩阵的行和列。辅助函数`boolean isIn(int x, int y)`判断坐标`x,y`是否在矩形区域内，直接返回`return x >= 0 && x < row && y >= 0 && y < col;`
    * 表示上下左右移动的坐标` private static int[][] dires = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};`
    * 另外就是需要处理已访问的坐标，这个根据题目有不同的处理办法。
* 本题要求岛屿的最大面积，就是相邻的1最多有多少个。多源dfs找到每一个岛屿有多少个1，或者你说这是回溯也可以。
* 定义回溯函数`void backTrack(int[][] grid, int x, int y)`。
    * 首先思考，本题怎么处理某个坐标已访问，因为我们只需要去寻找相邻的1，不需要去访问0。当我们访问过某一个1后，也不会再去访问它，所以将`gird[x][y]`设置为0即可，在找下一个要访问的点时，判断`gird[x][y] != 0`再去访问。所以回溯函数一开始，先`grid[x][y] = 0;`
    * 由于要记录当前这个岛屿的面积，所以当访问`x, y`这个点时，岛屿面积`curArea++;`
    * 然后for循环，分别去尝试上下左右四个方向，满足`isIn(newX, newY) && grid[newX][newY] != 0`则访问下一个点`newX, newY`。
* 主函数中，首先将`row, col`进行赋值，如果`row == 0`说明没有任何元素，直接返回。然后遍历矩阵中每个元素，当发现一个元素是1时，说明这是一个需要计算面积的岛，就以此点为起点开始进行dfs。
    * 首先要`curArea = 0;`重置当前岛屿面积为0。
    * 然后调用`backTrack(grid, i, j);`
    * 查找完当前岛屿后更新`maxArea = Math.max(maxArea, curArea);`
* 时间复杂度为$O(n)$，因为最多对每一个坐标进行两次访问。空间复杂度取决于递归调用所占的空间。

运行结果：

  * 执行用时 :3 ms, 在所有 Java 提交中击败了82.11%的用户
  * 内存消耗 :40.8 MB, 在所有 Java 提交中击败了92.44%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹