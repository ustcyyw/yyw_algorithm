package medium.BFSorDFS;

/**
 * @Time : 2020年4月15日00:11:20
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
 * 两个相邻元素间的距离为 1 。
 * <p>
 * 示例 1:
 * 输入:
 * 0 0 0
 * 0 1 0
 * 0 0 0
 * 输出:
 * 0 0 0
 * 0 1 0
 * 0 0 0
 * <p>
 * 示例 2:
 * 输入:
 * 0 0 0
 * 0 1 0
 * 1 1 1
 * 输出:
 * 0 0 0
 * 0 1 0
 * 1 2 1
 * 注意:
 * <p>
 * 给定矩阵的元素个数不超过 10000。
 * 给定矩阵中至少有一个元素是 0。
 * 矩阵中的元素只在四个方向上相邻: 上、下、左、右。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/01-matrix
 */
public class updateMatrix {
    /**
     * 执行用时 :23 ms, 在所有 Java 提交中击败了42.73%的用户
     * 内存消耗 :43.6 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    private int row, col;
    private int[][] dires = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public int[][] updateMatrix(int[][] matrix) {
        row = matrix.length;
        col = matrix[0].length;
        return bfs(matrix, new int[row][col]);
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
