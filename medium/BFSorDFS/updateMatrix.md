# 542. 01 矩阵
### 原题
给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
两个相邻元素间的距离为 1 。

示例 1:
输入:
0 0 0
0 1 0
0 0 0
输出:
0 0 0
0 1 0
0 0 0

示例 2:
输入:
0 0 0
0 1 0
1 1 1
输出:
0 0 0
0 1 0
1 2 1

注意:
给定矩阵的元素个数不超过 10000。
给定矩阵中至少有一个元素是 0。
矩阵中的元素只在四个方向上相邻: 上、下、左、右。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/01-matrix)：https://leetcode-cn.com/problems/01-matrix

### 解法
```java
public class updateMatrix {
    private int row, col;
    private int[][] dires = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int[][] updateMatrix(int[][] matrix) {
        row = matrix.length;
        col = matrix[0].length;
        return bfs(matrix,  new int[row][col]);
    }

    private int[][] bfs(int[][] matrix, int[][] res) {
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] pos = queue.remove();
            for (int[] dire : dires) {
                int newX = pos[0] + dire[0];
                int newY = pos[1] + dire[1];
                if (inArea(newX, newY) && matrix[newX][newY] != 0 && res[newX][newY] == 0) {
                    res[newX][newY] = res[pos[0]][pos[1]] + 1;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
        return res;
    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }
}
```
思路分析：
* 0离其自身的距离为0，要找到1离最近的0的距离，换一个角度就是所有的0到某个1的最短距离中最小的那个距离。
* 既然需要求所有0到某个1的最短距离，就是一个多源bfs，并且在bfs的过程中某个1第一次被计算出距离，这个距离就最小的那个距离，因为其余的0到这个点需要相同或者更多的步数。
* 二维平面，只能走上下左右四个方向，就是固定写法的。`int row, col`：表示矩阵边界。`int[][] dires = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};`：代表四个方向。`boolean inArea(int x, int y)`：判断某个坐标是否在矩形内部。
* 函数`int[][] bfs(int[][] matrix, int[][] res)`：
    * 第二个参数即为表示答案的矩阵，如果`res[x][y] == 0`要么这个点是0，要么这个点是1但是没有被访问过。
    * 多源bfs，首先通过二层循环将所有0的坐标放入队列中。
    * 其余就是bfs的模板写法以及二维平面四个方向的模板写法。这里要注意的是，遍历某个元素的条件，要满足三点：1.在矩形区域内`inArea(newX, newY)`；2.该点不是0，0是源头就不用访问了`matrix[newX][newY] != 0`；3.`res[x][y] == 0`，满足条件2说明该点不是0，那么这个点就是还没有访问的1，应该去访问。
    * 在递归调用之前，`res[newX][newY] = res[pos[0]][pos[1]] + 1;`，该点到0的距离就是从其上一个点到0的距离+1，赋值之后这个点的最短距离就求出来了。
* 主函数初始化成员变量，然后返回调用结果。
* 时间复杂度为$O(n)$，n表示元素总数，因为访问了矩阵中的每个元素。空间复杂度也为$O(n)$

运行结果：

 * 执行用时 :23 ms, 在所有 Java 提交中击败了42.73%的用户
 * 内存消耗 :43.6 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹