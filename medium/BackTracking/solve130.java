package medium.Backtracking;

/**
 * @Time : 2020年2月24日23:15:02
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
 * 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
 * <p>
 * 示例:
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * 运行你的函数后，矩阵变为：
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * 解释:
 * 被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
 * 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
 * 如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/surrounded-regions
 */
public class solve130 {
    /**
     * 执行用时 :10 ms, 在所有 Java 提交中击败了15.20%的用户
     * 内存消耗 :50.4 MB, 在所有 Java 提交中击败了5.03%的用户
     * 一族O都找到 然后需要判断这族O是否有成员在边上。
     */
    private int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Queue<Integer> pos = null;
    private boolean flag;
    private int row, col;
    private boolean[][] visited;

    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0)
            return;
        row = board.length;
        col = board[0].length;
        pos = new LinkedList<>();
        visited = new boolean[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                if (!visited[i][j] && board[i][j] == 'O') {
                    flag = false;
                    searchO(board, i, j);
                    if (flag)
                        pos.clear();
                    else {
                        while (!pos.isEmpty())
                            board[pos.remove()][pos.remove()] = 'X';
                    }
                }
            }
    }

    private void searchO(char[][] board, int x, int y) {
        visited[x][y] = true;
        pos.add(x);
        pos.add(y);
        flag = flag || isEdge(x, y);
        for (int i = 0; i < 4; i++) {
            int newX = x + direction[i][0];
            int newY = y + direction[i][1];
            if (inArea(newX, newY) && !visited[newX][newY] && board[newX][newY] == 'O')
                searchO(board, newX, newY);
        }
    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }

    private boolean isEdge(int x, int y) {
        return x == 0 || y == 0 || x == row - 1 || y == col - 1;
    }

    /**
     * 换个角度，如果是从边界的O 往里找，属于这族的都标记为ture， 那么没有标记为的O就都改为x
     * 自己做出来了 nice
     * 执行用时 :2 ms, 在所有 Java 提交中击败了97.69%的用户
     * 内存消耗 :42.1 MB, 在所有 Java 提交中击败了32.57%的用户
     */
    private boolean[][] linkedEdge = null;

    public void solve2(char[][] board) {
        if (board.length == 0 || board[0].length == 0)
            return;
        row = board.length;
        col = board[0].length;
        linkedEdge = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O' && !linkedEdge[i][0])
                search2(board, i, 0);
            if (board[i][col - 1] == 'O' && !linkedEdge[i][col - 1])
                search2(board, i, col - 1);
        }
        for (int j = 1; j < col - 1; j++) {
            if (board[0][j] == 'O' && !linkedEdge[0][j])
                search2(board, 0, j);
            if (board[row - 1][j] == 'O' && !linkedEdge[row - 1][j])
                search2(board, row - 1, j);
        }

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if (!linkedEdge[i][j])
                    board[i][j] = 'X';
    }

    private void search2(char[][] board, int x, int y) {
        linkedEdge[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int newX = x + direction[i][0];
            int newY = y + direction[i][1];
            if (inArea(newX, newY) && !linkedEdge[newX][newY] && board[newX][newY] == 'O')
                search2(board, newX, newY);
        }
    }
}
