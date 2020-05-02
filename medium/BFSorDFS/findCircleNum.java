package medium.BFSorDFS;

/**
 * @Time : 2020年3月20日11:15:41
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。
 * 如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为 A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。
 * 给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示已知第 i 个和 j 个学生互为朋友关系，否则为不知道。
 * 你必须输出所有学生中的已知的朋友圈总数。
 *
 * 示例 1:
 * 输入:
 * [[1,1,0],
 *  [1,1,0],
 *  [0,0,1]]
 * 输出: 2
 * 说明：已知学生0和学生1互为朋友，他们在一个朋友圈。
 * 第2个学生自己在一个朋友圈。所以返回2。
 *
 * 示例 2:
 * 输入:
 * [[1,1,0],
 *  [1,1,1],
 *  [0,1,1]]
 * 输出: 1
 * 说明：已知学生0和学生1互为朋友，学生1和学生2互为朋友，所以学生0和学生2也是朋友，所以他们三个在一个朋友圈，返回1。
 *
 * 注意：
 * N 在[1,200]的范围内。
 * 对于所有学生，有M[i][i] = 1。
 * 如果有M[i][j] = 1，则有M[j][i] = 1。
 *
 * https://leetcode-cn.com/problems/friend-circles/description/
 */
public class findCircleNum {
    /**
     * 执行用时 :4 ms, 在所有 Java 提交中击败了31.05%的用户
     * 内存消耗 :42.6 MB, 在所有 Java 提交中击败了78.65%的用
     * 这个题就是找连通分量 只不过图的表示使用的是邻接矩阵。
     */
    private int n;
    public int findCircleNum(int[][] M) {
        int count = 0;
        n = M.length;
        for(int i = 0; i < n; i++){
            if(M[i][i] == 1){
                count--;
                M[i][i] = count;
                dfs(M, i, count);
            }
        }
        return -count;
    }

    private void dfs(int[][] M, int v, int count){
        for(int i = 0; i < n; i++){
            if(M[v][i] == 1){
                M[v][i] = count;
                M[i][v] = count;
                M[i][i] = count;
                dfs(M, i, count);
            }
        }
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.93%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了78.86%的用户
     */
    public int findCircleNum2(int[][] M) {
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
