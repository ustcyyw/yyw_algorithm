package easy.other;

/**
 * @Time : 2020年3月4日11:24:39
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 * <p>
 * 示例 1：
 * 输入：[[2,1,1],[1,1,0],[0,1,1]]
 * 输出：4
 * <p>
 * 示例 2：
 * 输入：[[2,1,1],[0,1,1],[1,0,1]]
 * 输出：-1
 * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
 * <p>
 * 示例 3：
 * 输入：[[0,2]]
 * 输出：0
 * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotting-oranges
 */
public class orangesRotting {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了71.71%的用户
     * 内存消耗 :38.6 MB, 在所有 Java 提交中击败了52.94%的用户
     * 类似于bfs的搜索 不断腐烂相邻的橙子
     * 有点像回溯，但是回溯是dfs的。这里腐烂的橘子在的位置就将值改为 第几天腐烂 + 2，这样就可以识别哪些是还没腐烂的橙子，腐烂了的就不再去遍历了。
     */

    private static int[][] ds = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private Queue<int[]> queue;
    private int n, m;

    public int orangesRotting(int[][] grid) {
        n = grid.length;
        m = grid[0].length;
        queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                if (grid[i][j] == 2)
                    queue.add(new int[]{i, j}); // 第0天就腐烂的
        }
        while (!queue.isEmpty()) {
            int[] pos = queue.remove();
            newRot(grid, pos);
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++){
                if (grid[i][j] == 1)
                    return -1;
                res = Math.max(res, grid[i][j]);
            }
        }
        return res >= 2 ? res - 2 : 0; // 避免格子中没有放橘子的情况
    }

    private void newRot(int[][] grid, int[] pos) {
        int x = pos[0], y = pos[1];
        for(int[] d : ds){
            int newX = x + d[0];
            int newY = y + d[1];
            if(isIn(newX, newY) && grid[newX][newY] == 1){
                grid[newX][newY] = grid[x][y] + 1;
                queue.add(new int[]{newX, newY});
            }
        }
    }

    private boolean isIn(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
}
