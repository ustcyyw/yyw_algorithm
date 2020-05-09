package medium.BFSorDFS;

/**
 * @Time : 2020年3月20日11:45:22
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个 m x n 的非负整数矩阵来表示一片大陆上各个单元格的高度。“太平洋”处于大陆的左边界和上边界，而“大西洋”处于大陆的右边界和下边界。
 * 规定水流只能按照上、下、左、右四个方向流动，且只能从高到低或者在同等高度上流动。
 * 请找出那些水流既可以流动到“太平洋”，又能流动到“大西洋”的陆地单元的坐标。
 * <p>
 * 提示：
 * 输出坐标的顺序不重要
 * m 和 n 都小于150
 * <p>
 * 示例：
 * 给定下面的 5x5 矩阵:
 * 太平洋 ~   ~   ~   ~   ~
 * ~  1   2   2   3  (5) *
 * ~  3   2   3  (4) (4) *
 * ~  2   4  (5)  3   1  *
 * ~ (6) (7)  1   4   5  *
 * ~ (5)  1   1   2   4  *
 * *   *   *   *   * 大西洋
 * <p>
 * 返回:
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (上图中带括号的单元).
 * <p>
 * https://leetcode-cn.com/problems/pacific-atlantic-water-flow/
 */
public class pacificAtlantic {
    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了80.11%的用户
     * 内存消耗 :43.1 MB, 在所有 Java 提交中击败了22.92%的用户
     */
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
