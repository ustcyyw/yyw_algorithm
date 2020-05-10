# 5406. 收集树上所有苹果的最少时间
### 原题
给你一棵有 n 个节点的无向树，节点编号为 0 到 n-1 ，它们中有一些节点有苹果。通过树上的一条边，需要花费 1 秒钟。你从 节点 0 出发，请你返回最少需要多少秒，可以收集到所有苹果，并回到节点 0 。
无向树的边由 edges 给出，其中 edges[i] = [fromi, toi] ，表示有一条边连接 from 和 toi 。除此以外，还有一个布尔数组 hasApple ，其中 hasApple[i] = true 代表节点 i 有一个苹果，否则，节点 i 没有苹果。

示例 1：
输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,true,true,false]
输出：8 
解释：上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。

示例 2：
输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,true,false,false,true,false]
输出：6
解释：上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。

示例 3：
输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], hasApple = [false,false,false,false,false,false,false]
输出：0

提示：

```
1 <= n <= 10^5
edges.length == n-1
edges[i].length == 2
0 <= fromi, toi <= n-1
fromi < toi
hasApple.length == n
```

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/minimum-time-to-collect-all-apples-in-a-tree

### 解法
```java
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
```
思路分析：
* 先来看几个关键的题目数据的限制:
    * `edges.length == n-1`，连通图中的结点总共有n个，但是只有n-1条边，图中不存在环。所以每个结点有且仅有一条边能到达其祖先结点。
    * `fromi < toi`，那么边的组合可能会出现[0, 3], [1, 3]这样的情况吗？注意题目中的根结点是0，也就是说1最终会连接到0，这样的话[0, 3],[1,3]再加上从1到0的路径就成环了，但这个图是无环的，于是矛盾。也就是说不会同时出现[0, 3], [1, 3]。所以`toi`的值互不相同，并且`toi != 0`，那么`toi`的值就是1到n-1。
    * 结合以上两条，如果将`edges`按数组的第二个元素排序，那么`edges[i - 1]`就是结点`i`到其父亲结点的边。（因为`toi`的值最小为1，排序后这一条边成为`edges`索引为0的元素，所以索引`i - 1`与节点`i`对应）。从子节点能到达的父节点就可以用`edges[i - 1][0]`表示。
* 为了不出现重复路径的计数（看题目中的示意，要找的是不重复路径，一条边只来去一次），从子结点往父节点走（子结点到父节点的路径唯一），如果某个结点往父节点走的时候发现下一个结点已经访问过，那么接下来的路就和下一个结点一致，再走就重复了，所以就停止行走。定义`void goPath(int[][] edges, int i)`表示从子结点往0走：
    * 成员变量`res`表示所走的不重复路径。
    * 用成员变量`boolean[] hasVisited;`表示某个结点是否被访问过，所以当前节点设置为`true`。
    * 首先不论下一个结点是否被访问过，走到下一个节点都会使得不重复所走路径长度+1，`res++`。
    * 下一个节点为`int next = edges[i - 1][0];`，如果已经被访问过，避免走重复路径，直接返回。否则访问下一个结点`goPath(edges, next);`
* 在主函数
    * 中首先初始化成员变量，注意`hasVisited[0] = true;`，这样才能使从子结点往父节点行走的时候，到底0这个根结点就停止。
    * 然后按一开始所说将`edges`按数组的第二个元素排序，使用lambda表达式`Arrays.sort(edges, (a, b) -> a[1] - b[1]);`
    * 然后遍历除根以外的所有结点，如果该结点有苹果就开始从该结点往0走。（0这个结点即使有苹果也不需要走，贡献路径长0；另外函数`goPath`的第二行，如果`i = 0`，索引会变为-1，出错）。
    * 最后返回`res * 2`，因为变量`res`统计的是路径长，从题目给的图示来看，答案是不重复路径长的2倍。

运行结果：

* 11ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹