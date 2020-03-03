# 64.二维平面最小路径和

### 原题
给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
说明：每次只能向下或者向右移动一步。

示例:
输入:
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
输出: 7
解释: 因为路径 1→3→1→1→1 的总和最小。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/minimum-path-sum)：https://leetcode-cn.com/problems/minimum-path-sum

### 解法：动态规划

```java
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
```

思路分析：

* 这题基本上就是[第62题](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/DP/uniquePaths62.md)换一个目标，由求多少条路径变为求路径的最小值，这也是典型的动态规划的问题。几何结构行走模式都一样。
* 我们可以重新思考一下最后一步解与子问题。要走到右下角，可以从其上方走，也可以从其左边走，那么要得到路径和最小，肯定得从到达左，上两格子路径和最小的格子走过来。类似的，要到达某一个格子，都只有左，上两个路径，都要从到达左，上两格子路径和最小的格子走过来。
* 所以状态明确，`grid[i][j]`表示到达`(i,j)`的最小路径和，转移方程为`grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);`这里直接在`grid`上更改，因为原来每个点的值在使用过后就没有意义了。
* 边界条件的明确，类似的第一行，第一列为边界。不过这里是路径和，所以要进行累加。
* 时间复杂度为$O(mn)$，空间复杂度为$O(1)$

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了84.49%的用户
* 内存消耗 :42.2 MB, 在所有 Java 提交中击败了19.78%的用户

### 反思

```java
	public int minPathSum(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0)
            return 0;
        int row = grid.length;
        int col = grid[0].length;
        int[] count = new int[col];
        count[0] = grid[0][0];
        for(int i = 1; i < col; i++)
            count[i] =count[i - 1] + grid[0][i];
        for(int i = 1; i < row; i++){
            count[0] = grid[i][0] + count[0];
            for(int j = 1; j < col; j++)
                count[j] = grid[i][j] + Math.min(count[j], count[j - 1]);
        }
        return count[col - 1];
    }
```

改进思路：

* 类似62，63题，将存放状态的数组从二维改为一维，实现更快了，不过空间复杂度增加。当然也可以不建新数组，直接用`grid[0]`来当存放信息的一维数组。

运行结果：

* 执行用时 :2 ms, 在所有 Java 提交中击败了98.38%的用户

* 内存消耗 :42 MB, 在所有 Java 提交中击败了25.33%的用户