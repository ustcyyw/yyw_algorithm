package medium.Tree;

/**
 * @Time : 2020年3月13日14:40:37
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个整数 n，求以 1 ... n 为节点组成的二叉搜索树有多少种？
 *
 * 示例:
 * 输入: 3
 * 输出: 5
 * 解释:
 * 给定 n = 3, 一共有 5 种不同结构的二叉搜索树:
 *
 *    1         3     3      2      1
 *     \       /     /      / \      \
 *      3     2     1      1   3      2
 *     /     /       \                 \
 *    2     1         2                 3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/unique-binary-search-trees
 */
public class numTrees96 {
    /**
     * 递归 然后发现有大量重复的计算 可以用记忆话搜索也可以使用dp
     */
    public int numTrees(int n) {
        return count(1, n);
    }

    public int count(int lo, int hi){
        if(lo >= hi) return 1;
        int sum = 0;
        for(int i = lo; i <= hi; i++){
            sum += count(lo, i - 1) * count(i + 1, hi);
        }
        return sum;
    }

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.7 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public int numTrees2(int n) {
        int[] count = new int[n + 1]; // count[i]表示 i个结点可以构成多少种BST
        count[0] = 1;
        count[1] = 1;
        for(int i = 2; i <= n; i++){
            for(int j = 0; j < i; j++)
                count[i] += count[j] * count[i - j - 1];
        }
        return count[n];
    }
}
