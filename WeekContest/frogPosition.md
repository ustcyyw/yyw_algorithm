# 5355. T 秒后青蛙的位置

### 原题
给你一棵由 n 个顶点组成的无向树，顶点编号从 1 到 n。青蛙从 顶点 1 开始起跳。规则如下：
在一秒内，青蛙从它所在的当前顶点跳到另一个 未访问 过的顶点（如果它们直接相连）。
青蛙无法跳回已经访问过的顶点。
如果青蛙可以跳到多个不同顶点，那么它跳到其中任意一个顶点上的机率都相同。
如果青蛙不能跳到任何未访问过的顶点上，那么它每次跳跃都会停留在原地。
无向树的边用数组 edges 描述，其中 `edges[i] = [fromi, toi] `意味着存在一条直接连通` fromi` 和` toi` 两个顶点的边。
返回青蛙在 t 秒后位于目标顶点 target 上的概率。

示例 1：
输入：`n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 2, target = 4`
输出：0.16666666666666666 
解释：上图显示了青蛙的跳跃路径。青蛙从顶点 1 起跳，第 1 秒 有 1/3 的概率跳到顶点 2 ，然后第 2 秒 有 1/2 的概率跳到顶点 4，因此青蛙在 2 秒后位于顶点 4 的概率是 1/3 * 1/2 = 1/6 = 0.16666666666666666 。

示例 2：
输入：`n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 1, target = 7`
输出：0.3333333333333333
解释：上图显示了青蛙的跳跃路径。青蛙从顶点 1 起跳，有 1/3 = 0.3333333333333333 的概率能够 1 秒 后跳到顶点 7 。 

示例 3：
输入：`n = 7, edges = [[1,2],[1,3],[1,7],[2,4],[2,6],[3,5]], t = 20, target = 6`
输出：0.16666666666666666

提示：

```
1 <= n <= 100
edges.length == n-1
edges[i].length == 2
1 <= edges[i][0], edges[i][1] <= n
1 <= t <= 50
1 <= target <= n
与准确值误差在 10^-5 之内的结果将被判定为正确。
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/frog-position-after-t-seconds)：https://leetcode-cn.com/problems/frog-position-after-t-seconds

### 解法：回溯找到所有可能到达目标位置的跳跃方式

```java
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
```

思路分析：

* 要知道到底目的地的概率，那么我们得首先知道到达目的地的所有可能的路径。要找到所有可能的路径，每一个结点都有多个可选方向，并且已经走过的节点不能再走，很像回溯的做法。
* 另外，题目输入的数据结构在进行某一个结点找下一个可能到达的结点时非常不方便，所以先整理一下表示树的数据结构：
    * `List<Integer>[] canGo`使用一个列表数组，数组的索引与结点编号对应，数组的值表示该与该结点连接的所有结点。
    * 6-14行就是进行这个数据结构的初始化。
* 下一步就是通过回溯确定所有可能到达目标结点的路径：
    * 首先这个问题的路径有诸多的限制与坑：不能走重复的路径，所以需要一个状态来表示某个结点是否已经到达过。时间不结束，青蛙会一直往没有去过的结点跳，除非没有未访问过的结点，此时才会原地跳。
    * 所以在定义递归函数时，32行`private void backTrack(int target, int t, int curPos, boolean[] visit, List<Integer> path)`。第二个参数是剩余可以跳跃的时间（次数），第三个参数表示当前所在结点的编号，用它与`canGo`来确定相连结点。第四个参数表示某一结点是否已访问过。第四个参数用于存放到达当前结点的路径。
    * 递归结束的条件：
        * 没有剩余可用的跳跃时间43-44行
        * 或者当前结点已经是target结点（33行）。如果还有剩余跳跃次数及存在没有到达过的相连结点，青蛙跳走了，余下的走法它也回不到target了，所以没必要继续递归查找（34-38行）；如果没有剩余跳跃次数或者没有可到达的相连结点，那么这条路径就是我们要找的可能的路径之一（39-41行）
    * 45-53进行递归，首先找到当前结点所有相连的结点，如果没有访问过，则进行递归。下一结点添加进路径，修改访问状态，剩余可跳跃次数-1。某一路径回溯之后，修改访问状态，移除结点。
* 递归讲清楚之后，要开始递归之前，要将各变量状态设置好，起点一定在路径中且访问状态要设置为true（16-20行）。
* 在得到所有可能到达目标的路径后，最终概率要将每条路径的概率和相加。
* 某一条路径概率的计算：某个点一共有`canGo[p.get(i)].size()`个相连结点，其中有一个是来时候经过的结点，那么他可能到达的结点数量为`canGo[p.get(i)].size()-1`。一路向下，将路径上每个结点处选择正确的概率相乘，得到单条路径的概率。
* 最后不要忘记特殊情况的处理，只有一个结点，青蛙初始就在那，所以概率为1，第4-5行。

运行结果：5ms