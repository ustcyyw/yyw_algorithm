# LCP 07. 传递信息
### 原题
小朋友 A 在和 ta 的小伙伴们玩传信息游戏，游戏规则如下：
有 n 名玩家，所有玩家编号分别为 0 ～ n-1，其中小朋友 A 的编号为 0
每个玩家都有固定的若干个可传信息的其他玩家（也可能没有）。传信息的关系是单向的（比如 A 可以向 B 传信息，但 B 不能向 A 传信息）。
每轮信息必须需要传递给另一个人，且信息可重复经过同一个人
给定总玩家数 n，以及按 [玩家编号,对应可传递玩家编号] 关系组成的二维数组 relation。返回信息从小 A (编号 0 ) 经过 k 轮传递到编号为 n-1 的小伙伴处的方案数；若不能到达，返回 0。

示例 1：
输入：n = 5, relation = [[0,2],[2,1],[3,4],[2,3],[1,4],[2,0],[0,4]], k = 3
输出：3
解释：信息从小 A 编号 0 处开始，经 3 轮传递，到达编号 4。共有 3 种方案，分别是 0->2->0->4， 0->2->1->4， 0->2->3->4。

示例 2：
输入：n = 3, relation = [[0,2],[2,1]], k = 2
输出：0
解释：信息不能从小 A 处经过 2 轮传递到编号 2

限制：
```
2 <= n <= 10
1 <= k <= 5
1 <= relation.length <= 90, 且 relation[i].length == 2
0 <= relation[i][0],relation[i][1] < n 且 relation[i][0] != relation[i][1]
```

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/chuan-di-xin-xi

### 解法
```java
public class numWays {
    private int count;
    public int numWays(int n, int[][] relation, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int[] temp : relation){
            if(!map.containsKey(temp[0]))
                map.put(temp[0], new ArrayList<>());
            map.get(temp[0]).add(temp[1]);
        }
        count = 0;
        backTracking(map, k, n, 0, 0);
        return count;
    }

    private void backTracking(Map<Integer, List<Integer>> map, int k, int n, int cur, int curPerson){
        if(cur == k){
            if(curPerson == n - 1)
                count++;
            return;
        }
        if(!map.containsKey(curPerson))
            return;
        for(int i : map.get(curPerson)){
            backTracking(map, k, n, cur + 1, i);
        }
    }
}
```
思路分析：
* 指定步数的从0到n-1的路线数，要统计总共有多少。并且题目给出了两个人之间传递信息的关系，注意这是有向的。于是这个题目就像是在一个有向图中找到从指定结点0出发的所有长度指定的路径，并且路径的终点结点也是制定的节点n-1。
* 在主函数中我们先把图构建出来，使用哈希表，键为结点，值为该结点通过有向边可以到达的相邻结点构造的List。然后初始化`count = 0`，这个变量表示方案数，然后调用辅助函数（回溯函数）
* 其实BFS,DFS去查找路径都可以。这里我使用回溯的写法，实际上就是DFS。定义回溯函数`void backTracking(Map<Integer, List<Integer>> map, int k, int n, int cur, int curPerson)`
    * 第一个参数就是有向图的数据结构。
    * 第二个参数指定路径长度，也就是传递信息的轮次数
    * 第三个参数表示总共有n个玩家，那么最后一个玩家就是n-1
    * 第四个参数表示现在是第几轮传递信息，从0开始。
    * 第五个参数表示当前玩家的编号。
* 根据参数定义，函数的实现如下：
    * 当`cur == k`，传递信息的次数已经使用完了（路径长度为k了），此时就需要判断当前玩家的编号是不是n-1，如果是则`count++`。然后返回。
    * 否则，传递次数没用完，如果`!map.containsKey(curPerson)`，表示这个玩家无法给别人传递信息，所以直接返回。
    * 除去上述情况，就是说明还要继续向下寻找路径，所有的可能为`for(int i : map.get(curPerson))`，然后调用` backTracking(map, k, n, cur + 1, i);`，第四个参数，传递信息次数+1，第五个参数，下一个要进行传递信息的人变为i。

运行结果：

* 3ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹