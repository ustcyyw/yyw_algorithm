# 547. 朋友圈
### 原题
班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果`M[i][j] = 1`，表示已知第` i `个和` j `个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。

示例 1:
输入: 
[[1,1,0],
 [1,1,0],
 [0,0,1]]
输出: 2 
说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
第2个学生自己在一个朋友圈。所以返回2。

示例 2:
输入: 
[[1,1,0],
 [1,1,1],
 [0,1,1]]
输出: 1
说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。

注意：
N 在[1,200]的范围内。
对于所有学生，有`M[i][i] = 1`。
如果有`M[i][j] = 1`，则有`M[j][i] = 1`。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/friend-circles)：https://leetcode-cn.com/problems/friend-circles

### 解法
```java
public class findCircleNum {
    public int findCircleNum(int[][] M) {
        int count = 0;
        n = M.length;
        boolean[] marked = new boolean[n];
        for(int i = 0; i < n; i++){
            if(!marked[i]){
                count++;
                marked[i] = true;
                dfs2(M, i, marked);
            }
        }
        return count;
    }

    private void dfs2(int[][] M, int v, boolean[] marked){
        for(int i = 0; i < n; i++){
            if(!marked[i] && M[v][i] == 1){
                marked[i] = true;
                dfs2(M, i, marked);
            }
        }
    }
}
```
思路分析：
* 从题目描述来看，显然这是一个使用邻接矩阵表示的图，图中结点就是编号从0到M的学生，所谓的朋友圈就是一个连通子图。所以问题就变为在无向图中找连通子图的数量。
* 使用` boolean[] marked = new boolean[n];`表示结点的访问情况，`int count`计数连通子图的数量。
* 定义`void dfs2(int[][] M, int v, boolean[] marked)`，使用深度优先访问连通子图的所有结点。
    * 遍历所有结点`[0, n - 1]`，如果节点没有被访问过，且两个结点之间有边连接（`M[i][j] = 1`，表示已知第` i `个和` j `个学生互为朋友关系），就将该结点表示为已访问`marked[i] = true;`，然后递归地调用。
* 主函数遍历所有结点`[0, n - 1]`，每当发现没有被访问的结点，说明这是一个新的连通子图。所以`count++`，然后将该结点设置为已访问，调用` dfs2(M, i, marked);`。这个调用就可以将包括该结点的连通子图中所有结点都访问到。
* 由于会遍历到每一个结点，且遍历每个结点都会有单层循环，时间复杂度为$O(n^2)$。使用了辅助数组`marked`，所以空间复杂度为$O(n)$。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了99.93%的用户
* 内存消耗 :42.2 MB, 在所有 Java 提交中击败了78.86%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹