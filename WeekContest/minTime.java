import java.util.*;

/**
 * @Time : 2020年5月10日10:44:42
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给你一棵有 n 个节点的无向树，节点编号为 0 到 n-1 ，它们中有一些节点有苹果。
 * 通过树上的一条边，需要花费 1 秒钟。你从 节点 0 出发，请你返回最少需要多少秒，可以收集到所有苹果，并回到节点 0 。
 * 无向树的边由 edges 给出，其中 edges[i] = [fromi, toi] ，表示有一条边连接 from 和 toi 。
 * 除此以外，还有一个布尔数组 hasApple ，其中 hasApple[i] = true 代表节点 i 有一个苹果，否则，节点 i 没有苹果。
 * <p>
 * 示例 1：
 * 输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]
 * 输出：8
 * 解释：上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。
 * <p>
 * 示例 2：
 * 输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,false,true,false]
 * 输出：6
 * 解释：上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。
 * <p>
 * 示例 3：
 * 输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,false,false,false,false,false]
 * 输出：0 
 * <p>
 * 提示：
 * 1 <= n <= 10^5
 * edges.length == n-1
 * edges[i].length == 2
 * 0 <= fromi, toi <= n-1
 * fromi < toi
 * hasApple.length == n
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-time-to-collect-all-apples-in-a-tree
 */
public class minTime {
    private int res;
    private boolean[] hasVisited;
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        res = 0;
        hasVisited = new boolean[n];
        hasVisited[0] = true;
        Arrays.sort(edges, (a, b) -> a[1] - b[1]);
        for(int i = 1; i < hasApple.size(); i++){
            if(hasApple.get(i))
                goPath(edges, i);
        }
        return 2 * res;
    }

    private void goPath(int[][] edges, int i){
        hasVisited[i] = true;
        int next = edges[i - 1][0];
        res++;
        if(hasVisited[next]){
            return;
        }
        goPath(edges, next);
    }
}
