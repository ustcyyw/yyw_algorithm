package medium.BFSorDFS;

/**
 * @Time : 2020年3月29日00:07:08
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 你现在手里有一份大小为 N x N 的『地图』（网格） grid，上面的每个『区域』（单元格）都用 0 和 1 标记好了。
 * 其中 0 代表海洋，1 代表陆地，你知道距离陆地区域最远的海洋区域是是哪一个吗？请返回该海洋区域到离它最近的陆地区域的距离。
 * 我们这里说的距离是『曼哈顿距离』（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个区域之间的距离是 |x0 - x1| + |y0 - y1| 。
 * 如果我们的地图上只有陆地或者海洋，请返回 -1。 
 * <p>
 * 示例 1：
 * 输入：[[1,0,1],[0,0,0],[1,0,1]]
 * 输出：2
 * 解释：
 * 海洋区域 (1, 1) 和所有陆地区域之间的距离都达到最大，最大距离为 2。
 * <p>
 * 示例 2：
 * 输入：[[1,0,0],[0,0,0],[0,0,0]]
 * 输出：4
 * 解释：
 * 海洋区域 (2, 2) 和所有陆地区域之间的距离都达到最大，最大距离为 4。
 * <p>
 * 提示：
 * 1 <= grid.length == grid[0].length <= 100
 * grid[i][j] 不是 0 就是 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/as-far-from-land-as-possible
 */
public class maxDistance {
    /**
     * 执行用时 :19 ms, 在所有 Java 提交中击败了75.92%的用户
     * 内存消耗 :41.6 MB, 在所有 Java 提交中击败了99.07%的用户
     */
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
}
