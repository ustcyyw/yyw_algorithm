package WeekContest; /**
 * @Time : 2020年3月8日11:30:48
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.*;

/**
 * 给你一棵由 n 个顶点组成的无向树，顶点编号从 1 到 n。青蛙从 顶点 1 开始起跳。规则如下：
 * <p>
 * 在一秒内，青蛙从它所在的当前顶点跳到另一个 未访问 过的顶点（如果它们直接相连）。
 * 青蛙无法跳回已经访问过的顶点。
 * 如果青蛙可以跳到多个不同顶点，那么它跳到其中任意一个顶点上的机率都相同。
 * 如果青蛙不能跳到任何未访问过的顶点上，那么它每次跳跃都会停留在原地。
 * 无向树的边用数组 edges 描述，其中 edges[i] = [fromi, toi] 意味着存在一条直接连通 fromi 和 toi 两个顶点的边。
 * <p>
 * 返回青蛙在 t 秒后位于目标顶点 target 上的概率。
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4
 * 输出：0.16666666666666666
 * 解释：上图显示了青蛙的跳跃路径。青蛙从顶点 1 起跳，第 1 秒 有 1/3 的概率跳到顶点 2 ，然后第 2 秒 有 1/2 的概率跳到顶点 4，因此青蛙在 2 秒后位于顶点 4 的概率是 1/3 * 1/2 = 1/6 = 0.16666666666666666 。
 * 示例 2：
 * <p>
 * <p>
 * <p>
 * 输入：n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 1, target = 7
 * 输出：0.3333333333333333
 * 解释：上图显示了青蛙的跳跃路径。青蛙从顶点 1 起跳，有 1/3 = 0.3333333333333333 的概率能够 1 秒 后跳到顶点 7 。
 * 示例 3：
 * <p>
 * 输入：n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 20, target = 6
 * 输出：0.16666666666666666
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= n <= 100
 * edges.length == n-1
 * edges[i].length == 2
 * 1 <= edges[i][0], edges[i][1] <= n
 * 1 <= t <= 50
 * 1 <= target <= n
 * 与准确值误差在 10^-5 之内的结果将被判定为正确。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/frog-position-after-t-seconds
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class frogPosition {
    private List<List<Integer>> paths;
    private List<Integer>[] canGo;

    public double frogPosition(int n, int[][] edges, int t, int target) {
        if(n == 1)
            return 1;
        canGo = (List<Integer>[]) new ArrayList[n + 1];
        for(int i = 0; i < n + 1; i++)
            canGo[i] = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) { // 准备好各个点可以到达的情况。
            int pos1 = edges[i][0];
            int pos2 = edges[i][1];
            canGo[pos1].add(pos2);
            canGo[pos2].add(pos1);
        }
        // dfs 找到所有可能的到达target的路径。
        paths = new ArrayList<>();
        boolean[] visit = new boolean[n + 1];
        visit[1] = true;
        List<Integer> path = new ArrayList<>();
        path.add(1);
        backTrack(target, t, 1, visit, path);

        double res = 0;
        for (List<Integer> p : paths) {
            double product = canGo[p.get(0)].size();
            for (int i = 1; i < p.size() - 1; i++)
                product *= (canGo[p.get(i)].size() - 1);
            res += 1.0 / product;
        }
        return res;
    }

    private void backTrack(int target, int t, int curPos, boolean[] visit, List<Integer> path) {
        if (curPos == target) {
            if(t != 0)
                for(int next : canGo[curPos]){
                    if(!visit[next])
                        return;
                }
            List<Integer> temp = new ArrayList<>(path);
            paths.add(temp);
            return;
        }
        if (t == 0)
            return;
        for (int i : canGo[curPos]) {
            if (!visit[i]) {
                path.add(i);
                visit[i] = true;
                backTrack(target, t - 1, i, visit, path);
                visit[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}
