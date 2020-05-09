# 417. 太平洋大西洋水流问题
### 原题
给定一个 m x n 的非负整数矩阵来表示一片大陆上各个单元格的高度。“太平洋”处于大陆的左边界和上边界，而“大西洋”处于大陆的右边界和下边界。
规定水流只能按照上、下、左、右四个方向流动，且只能从高到低或者在同等高度上流动。
请找出那些水流既可以流动到“太平洋”，又能流动到“大西洋”的陆地单元的坐标。

提示：
输出坐标的顺序不重要
m 和 n 都小于150

示例：
给定下面的 5x5 矩阵:

```
  太平洋 ~   ~   ~   ~   ~ 
       ~  1   2   2   3  (5) *
       ~  3   2   3  (4) (4) *
       ~  2   4  (5)  3   1  *
       ~ (6) (7)  1   4   5  *
       ~ (5)  1   1   2   4  *
          *   *   *   *   * 大西洋
```

返回:
[[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (上图中带括号的单元).

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/pacific-atlantic-water-flow)：https://leetcode-cn.com/problems/pacific-atlantic-water-flow

### 解法
```java
public class pacificAtlantic {
    private static int[][] dires = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int m, n;
    private int[][] matrix;

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        m = matrix.length;
        if (m == 0)
            return res;
        n = matrix[0].length;
        if (n == 0)
            return res;
        this.matrix = matrix;
        boolean[][] canReachP = new boolean[m][n];
        boolean[][] canReachA = new boolean[m][n];
        for (int i = 0; i < n; i++) {
            dfs(0, i, canReachP);
            dfs(m - 1, i, canReachA);
        }
        for (int i = 0; i < m; i++) {
            dfs(i, 0, canReachP);
            dfs(i, n - 1, canReachA);
        }
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(canReachA[i][j] && canReachP[i][j]){
                    List<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    res.add(temp);
                }
            }
        }
        return res;
    }
    /**
     * 换一种思路，从边界往里面走，只能走到比自己更高或者等高的地方。边界能走到的地方，就是能流入对应海洋的地方。
     */
    private void dfs(int x, int y, boolean[][] canReach) {
        canReach[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int newX = x + dires[i][0];
            int newY = y + dires[i][1];
            if (isIn(newX, newY) && matrix[x][y] <= matrix[newX][newY] && !canReach[newX][newY]) {
                dfs(newX, newY, canReach);
            }
        }
    }

    private boolean isIn(int x, int y) {
        return x >= 0 && x < m && y >= 0 && y < n;
    }
}
```
思路分析：
* 二维平面上的上下左右四个方向可以行走，与其余类似题目一样，固定的写法有
    * 表示四个方向的数组`int[][] dires = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};`
    * 表示二维平面的行数与列数`int m, n;`
    * 判断某个坐标`x,y`是否在矩形区域内的辅助函数`boolean isIn(int x, int y)`
* 要同时满足可以到达大西洋与太平洋，所以一个点需要进行两次路径的行走，一次以太平洋为目标，一次以大西洋为目标。从内部的点以边界为目标去进行路径行走比较麻烦，但是如果换一个思路，从边缘往里面走。就类似于[130. 被围绕的区域](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BackTracking/solve130.md)的第二种解法。
* 从边缘向里走就修改通行规则，要往高度比当前点高或者相等的点走。
* 定义函数`dfs(int x, int y, boolean[][] canReach)`，第三个参数代表大西洋/太平洋相邻的点可以访问到的点，这些点也就是可以流到相应大洋的点。
    * 首先将`canReach[x][y] = true;`，将当前点设置为已访问。
    * 然后对上下左右四个方向的点进行遍历，如果满足：在矩形内`isIn(newX, newY)`，高度比当前点更高或者相等`matrix[x][y] <= matrix[newX][newY]`且还没有访问过，就对其访问。
* 主函数中，首先将各个成员变量初始化。然后生成表示大西洋/太平洋访问状态的` boolean[][] canReachP/canReachA = new boolean[m][n];`。然后对于矩形的上下左右四条边界的点分别调用`dfs()`，进行从大西洋/太平洋到内部的访问。
* 最后对二维平面内的所有点进行遍历，找到`canReachA[i][j] && canReachP[i][j]`的点，就是可以同时到达两个大洋。
* 时间复杂度为$O(n)$，因为只对每一个点进行了最多三次遍历，n表示坐标点的个数。空间复杂度除去递归调用占用的空间为$O(n)$。

运行结果：

* 执行用时 :6 ms, 在所有 Java 提交中击败了80.11%的用户
* 内存消耗 :43.1 MB, 在所有 Java 提交中击败了22.92%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹