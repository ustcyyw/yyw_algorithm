# 1162. 地图分析

### 原题
你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。

我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。

如果我们的地图上只有陆地或者海洋，请返回 -1。

示例 1：
输入：[[1,0,1],[0,0,0],[1,0,1]]
输出：2
解释： 
海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。

示例 2：
输入：[[1,0,0],[0,0,0],[0,0,0]]
输出：4
解释： 
海洋区域 (2, 2) 和所有陆地区域之间的距离都达到最大，最大距离为 4。

提示：

`1 <= grid.length == grid[0].length <= 100`
`grid[i][j] `不是 0 就是 1

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/as-far-from-land-as-possible)：https://leetcode-cn.com/problems/as-far-from-land-as-possible

### 解法：BFS

```java
private int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int n;
    private Queue<int[]> pos;
    private int res;
    public int maxDistance(int[][] grid) {
        n = grid.length;
        pos = new LinkedList<>();
        for(int i = 0; i < n; i++){
            for (int j = 0; j < n; j++)
                if(grid[i][j] == 1)
                    pos.add(new int[]{i, j});
        }
        res = 0;
        while (!pos.isEmpty()){
            int[] temp = pos.remove();
            walk(temp[0], temp[1], grid);
        }
        return res == 0 ? -1 : res - 1;
    }

    private void walk(int x, int y, int[][] grid){
        for(int i = 0; i < 4; i++){
            int newX = x + direction[i][0];
            int newY = y + direction[i][1];
            if(inArea(newX, newY) && grid[newX][newY] == 0){
                pos.add(new int[]{newX, newY});
                grid[newX][newY] = grid[x][y] + 1;
                res = Math.max(res, grid[newX][newY]);
            }
        }
    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
```

思路分析：

* 这个题仔细读懂题目之后发现，他其实和[994. 腐烂的橘子](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/other/orangesRotting.md)。是基本上完全一模一样的，用BFS就可以解决。
* 首先来读懂题目。为什么求最远距离，反而是用BFS？很简单，这个地图上有多个陆地，如果从一个陆地出发使用DFS，这样找到的最远海洋，离别的陆地可能就是最远的。
* 其实看完示例可以发现，所求的曼哈顿距离就是陆地到该位置需要走的步数（上下左右行走，一次只能将横纵坐标之一改变。）要找到最远的海洋，对于各个陆地都是最远，就需要从每个陆地为起点向外走。第一次，以陆地为起点，走一步；第二次，每个第一次访问的点为起点，走一步……直到地图上的所有点都被访问过。
* 就像回溯算法题解中很常见的二维平面的搜索。实例变量`int[][] direction`表示上下左右移动的坐标变化量，并且用`private int n;`表示行列数。且写一个防止数组索引越界的判断函数` private boolean isIn(int x, int y)`。
* 接下来，完成我们BFS函数的设计。
    * 用`res`表示搜索中的最远曼哈顿距离。
    * 需要一个保留结点的队列`private Queue pos;`
    * 起点是每一个陆地，初始时候将所有陆地坐标`grid[i][j] == 1`的坐标放入队列中。
    * 对于某个点的搜索，使用函数`walk(int x, int y, int[][] grid)`，前两个参数表示该点的坐标，将该点上下左右四个点的坐标算出，保证不越界的情况下`isIn(newX, newY)`为真，如果相邻四点是还没有访问过的水域`grid[newX][newY] == 0`。将该处的值设置为上一个点距离起点陆地的距离+1`grid[newX][newY] = grid[x][y] + 1;`，并且更新`res`。然后将该点的坐标放入队列，准备用它为起点访问临近的水域（用它接着进行广度优先遍历）
    * 距离出发陆地的曼哈顿距离直接使用`grid[][]`来记录，规定`grid[i][j] = x `表示距离出发的陆地的曼哈顿距离为x-1。
    * 队列非空时取出一个起点，向上下左右尝试进行访问，通过`walk`函数只会访问相邻的水域。
* 搜索完成后，根据`grid[i][j]`的意义设定，res的值为最远曼哈顿距离+1，所以结果应该为` res - 1`。
* 最后，要注意特殊情况的处理：地图上只有陆地或者海洋，请返回 -1。只有海洋的情况，意味着初始化结束后队列为空，没有进行任何搜索，所以这种情况`res = 0`。只有陆地的情况，意味着任何一个起点在调用函数`walk`后都找不到可访问的点，res不会进行任何更新，所以这种情况`res=0`。在返回的时候判断如果`res ==0`，就返回-1。

运行结果：
* 执行用时 :19 ms, 在所有 Java 提交中击败了75.92%的用户
* 内存消耗 :41.6 MB, 在所有 Java 提交中击败了99.07%的用户