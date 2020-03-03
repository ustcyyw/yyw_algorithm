# 62.二维平面不同路径

### 原题
一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
问总共有多少条不同的路径？
例如，上图是一个7 x 3 的网格。有多少可能的路径？
说明：m 和 n 的值均不超过 100。

示例 1:
输入: m = 3, n = 2
输出: 3
解释:
从左上角开始，总共有 3 条路径可以到达右下角。
1. 向右 -> 向右 -> 向下
2. 向右 -> 向下 -> 向右
3. 向下 -> 向右 -> 向右
4. 
示例 2:
输入: m = 7, n = 3
输出: 28

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/unique-paths)：https://leetcode-cn.com/problems/unique-paths

### 解法：动态规划

```java
	public int uniquePaths(int m, int n) {
        int[][] count = new int[n][m];
        for(int[] row : count)
            row[0] = 1;
        Arrays.fill(count[0], 1);
        for(int row = 1; row < n; row++)
            for(int col = 1; col < m; col++)
                count[row][col] = count[row - 1][col] + count[row][col - 1];
        return count[n - 1][m - 1];
    }
```

思路分析：

* 题目要求共有多少种方法可以到达右下角的格子。只能向下或者向右走，看起来是可以通过回溯来解决的计数问题，但其中存在很多重复计算比如(0,0)->(0,1)->(1,1)与(0,0)->(1,0)->(1,1)两种方式走到(1,1)，之后的走法在回溯中都会重复计算。这样的问题 是典型的动态规划的问题。
* 类似于[爬楼梯](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/DP/climbStairs.md)的那个题目，都是达到最终目的地有多少种走法。
* 第一步，确定状态：要求得最终问题，到达右下角一共多少种走法，得先解决其子问题。到达该点左边的点与上方点一共有多少种方法。所以状态可以用一个二维数组表示`int[][] count = new int[n][m]`表示,`count[row][col]`表示到达(row,col)这个格子一共有多少种方法。
* 第二步，状态转移方程：第一步中由子问题及状态很容易得到，转移方程为`count[row][col] = count[row - 1][col] + count[row][col - 1]`。
* 第三步，边界条件：联系实际到达第一列的点只能一直向下，所以都只有一种方法，每行的第一列都为1；到达第一行的点只能一直向右，所以只有一种方法，第一行都为1。
* 第四步，计算顺序：从左上走向右下，且边界条件也是左上右下，所以顺序就是从左到右，从上到下。
* 时间复杂度为$O(mn)$，空间复杂度为$O(mn)$

代码解释：

* 3~5行，第一行，第一列的边界条件确定。
* 6，7两行都是从下标1开始，因为0下标是边界条件。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :36.1 MB, 在所有 Java 提交中击败了5.10%的用户

### 反思

```java
 public int uniquePaths2(int m, int n) {
        int[] count = new int[m];
        Arrays.fill(count, 1);
        for(int row = 1; row < n; row++)
            for(int col = 1; col < m; col++)
                count[col] = count[col] + count[col - 1]; // 分别对应上格子与左边格子
        return count[m - 1];
    }
```

从修改状态的方程看，这样修改可以将空间复杂度降低为$O(m)$