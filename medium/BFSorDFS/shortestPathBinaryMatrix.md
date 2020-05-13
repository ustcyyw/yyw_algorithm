# 1091. 二进制矩阵中的最短路径
### 原题
在一个 N × N 的方形网格中，每个单元格有两种状态：空（0）或者阻塞（1）。
一条从左上角到右下角、长度为 k 的畅通路径，由满足下述条件的单元格` C_1, C_2, ..., C_k` 组成：
相邻单元格 `C_i` 和 `C_{i+1} `在八个方向之一上连通（此时，`C_i `和 `C_{i+1} `不同且共享边或角）
`C_1 `位于 (0, 0)（即，值为 `grid[0][0]`）
`C_k` 位于 (N-1, N-1)（即，值为 `grid[N-1][N-1]`）
如果 `C_i` 位于 (r, c)，则 `grid[r][c]` 为空（即，`grid[r][c] == 0`）
返回这条从左上角到右下角的最短畅通路径的长度。如果不存在这样的路径，返回 -1 。 

示例 1：
输入：[[0,1],[1,0]]
输出：2

示例 2：
输入：[[0,0,0],[1,1,0],[1,1,0]]
输出：4

提示：

```
1 <= grid.length == grid[0].length <= 100
grid[i][j] 为 0 或 1
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/shortest-path-in-binary-matrix)：https://leetcode-cn.com/problems/shortest-path-in-binary-matrix

### 解法
```java
public class shortestPathBinaryMatrix {
    private static int[][] directions = {{0,1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {-1, -1}, {-1, 0}, {-1, 1}};
    private int row, col;
    public int shortestPathBinaryMatrix(int[][] grid) {
        row = grid.length;
        col = grid[0].length;
        if(grid[0][0] == 1 || grid[row - 1][col - 1] == 1) return -1;
        Queue<int[]> pos = new LinkedList<>();
        grid[0][0] = 1; // 直接用grid[i][j]记录从起点到这个点的最短路径长。按照题意 起点也有长度1
        pos.add(new int[]{0,0});
        while(!pos.isEmpty() && grid[row - 1][col - 1] == 0){ // 求最短路径 使用BFS
            int[] xy = pos.remove();
            int preLength = grid[xy[0]][xy[1]]; // 当前点的路径长度
            for(int i = 0; i < 8; i++){
                int newX = xy[0] + directions[i][0];
                int newY = xy[1] + directions[i][1];
                if(inGrid(newX, newY) && grid[newX][newY] == 0){
                    pos.add(new int[]{newX, newY});
                    grid[newX][newY] = preLength + 1; // 下一个点的路径长度要+1
                }
            }
        }
        return grid[row - 1][col - 1] == 0 ? -1 : grid[row - 1][col - 1]; // 如果最后终点的值还是0，说明没有到达
    }

    private boolean inGrid(int x, int y){
        return x >= 0 && x < row && y >= 0 && y < col;
    }
}

```
思路分析：
* 要找到左上角到右下角的最短路径，最短路径嘛，自然就想到了使用BFS。
* 在二维平面上，八个方向可以进行移动，使用`int[][] directions`表示八个方向。比如`{1,1}`就表示右下方向。二维平面常规做法，使用函数`boolean inGrid(int x, int y)`判断某个点是否在矩形范围内（防止数组越界）。
* 首先将成员变量，表示矩形行列数的`row, col`初始化。然后如果左上角或者右下角为1，一定无法从左上角到右下角，直接返回-1。
* 然后开始使用队列模拟BFS：
    * 我们需要去判断哪些路径已经走过，并且我们还需要知道走到某一个点时的步数，结合题目规定0是通行，1是不可通行，走过的点也不会再走相当于不可通行。所以我们可以用`grid[newX][newY] == 0`表示没有访问过的可通行的点。
    * 按照题意，起点也有长度1，所以设置`grid[0][0] = 1;`，且` pos.add(new int[]{0,0});`。
    * 用队列模拟的循环条件`!pos.isEmpty() && grid[row - 1][col - 1] == 0`，第二个条件不满足时，说明已经有路径到达右下角了，就可以停止搜索。
    * 弹出某个点的坐标，通过`int preLength = grid[xy[0]][xy[1]];`得到到达该点的长度，然后遍历8个方向，试图访问下一个点，满足`inGrid(newX, newY) && grid[newX][newY] == 0`则可以访问，然后到达下一个点的路径长度就变为`grid[newX][newY] = preLength + 1;`，然后这个点`grid[newX][newY] != 0`了，就不会被重复访问。
* 循环结束后，可能是搜索完成但没有到达右下角，此时`grid[row - 1][col - 1] == 0`；也可能是已经找到到达右下角的路径，按BFS，此时`grid[row - 1][col - 1]`即为答案。所以最后返回`grid[row - 1][col - 1] == 0 ? -1 : grid[row - 1][col - 1];`
* 时间复杂度为$O(n)$，因为每个元素遍历了一次，n为元素的个数。空间复杂度为$O(k)$，k为过程中队列的最大元素个数。

运行结果：

* 执行用时 :15 ms, 在所有 Java 提交中击败了97.87%的用户
* 内存消耗 :42.3 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹