# 63.二维平面不同路径 且有障碍

### 原题
一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
网格中的**障碍物和空位置分别用 1 和 0 来表示**。

说明：m 和 n 的值均不超过 100。

示例 1:
输入:
[
  [0,0,0],
  [0,1,0],
  [0,0,0]
]
输出: 2
解释:
3x3 网格的正中间有一个障碍物。
从左上角到右下角一共有 2 条不同的路径：

1. 向右 -> 向右 -> 向下 -> 向下
2. 向下 -> 向下 -> 向右 -> 向右

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/unique-paths-ii)：https://leetcode-cn.com/problems/unique-paths-ii

### 解法： 动态规划

```java
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
```

思路分析：

* 这题基本上就是[第62题](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/DP/uniquePaths62.md)加上一个很简单的限制条件。这里只再强调一下，状态`count[i][j]`表示从原点出发，到达`(i,j)`这个点一共有多少种走法。
* 来解决一下这个限制条件就可以。某一个点的值为1表示该点有障碍，那么不能到达该点，不能到达改点的意味着`count[i][j]=0`，到底该点的方法为0种。顺带可以想一下，如果`(i,j)`不能到达，那么`(i,j + 1)`，就只能由`(i-1,j)`到达，状态`count[i][j + 1] = count[i - 1][j] + count[i][j] = count[i - 1][j]`符合逻辑。
* 所以状态方程为`count[i][j] = obstacleGrid[i][j] == 1 ? 0 : count[i - 1][j] + count[i][j - 1]; `
* 另外，边界条件也与62略微不同，就是第一行如果某个格子值为1，那么其后面的格子就都无法到达了，只有该格子之前的格子有一种方法能到达；第一列也同样的。
* 时间复杂度为$O(mn)$，空间复杂度为$O(mn)$

代码解释：

* 6~9行，边界条件的确定。
* 10，11两行都是从下标1开始，因为0下标是边界条件。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了87.11%的用户
* 内存消耗 :38.1 MB, 在所有 Java 提交中击败了46.73%的用户
### 反思

```java
	public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[] count = new int[col];
        count[0] = 1;
        for (int i = 0; i < row; i++) {
            count[0] = obstacleGrid[i][0] == 1 ? 0 : count[0];
            for (int j = 1; j < col; j++)
                count[j] = obstacleGrid[i][j] == 1 ? 0 : count[j] + count[j - 1]; // 分别对应上方格子与左边格子，也要考虑障碍物
        }
        return count[col - 1];
    }
```

改进思路：

* 计算是逐行进行的，那么当我们更新当前行的第`i`个元素时，数组中`i`处的元素是上一行中的结果，更新之后覆盖即可，因为其它元素用不到被覆盖的数据了。不过要注意每一行第一个元素的更新，当前格子无障碍时，能不能到达取决于其上一个元素，如果有障碍时一定不能到达。`count[0] = obstacleGrid[i][0] == 1 ? 0 : count[0];`
* 边界条件就是，原点的位置一点可以到达。`count[0] = 1`
* 这样修改可以将空间复杂度降低为$O(col)$

运行结果：

* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.4 MB, 在所有 Java 提交中击败了61.05%的用户