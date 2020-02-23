package easy.Tree;

/**
 * @Time : 2020年2月23日14:57:30
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给定两个二叉树，编写一个函数来检验它们是否相同。
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 * <p>
 * 示例 1:
 * 输入:       1         1
 * / \       / \
 * 2   3     2   3
 * <p>
 * [1,2,3],   [1,2,3]
 * 输出: true
 * <p>
 * 示例 2:
 * 输入:      1          1
 * /           \
 * 2             2
 * <p>
 * [1,2],     [1,null,2]
 * 输出: false
 * <p>
 * 示例 3:
 * 输入:       1         1
 * / \       / \
 * 2   1     1   2
 * <p>
 * [1,2,1],   [1,1,2]
 * 输出: false
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/same-tree
 */
public class isSameTree100 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.2 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        if (p.val != q.val) return false;
        else return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * 迭代采用 bfs 或者 dfs 都可以
     * 执行用时 :1 ms, 在所有 Java 提交中击败了6.17%的用户
     * 内存消耗 :37 MB, 在所有 Java 提交中击败了5.02%
     */
    public boolean isSameTree2(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.add(q);
        queue2.add(p);
        while (!queue1.isEmpty()){
            TreeNode tempQ = queue1.remove();
            TreeNode tempP = queue2.remove();
            if (tempQ == null && tempP == null) continue;
            if (tempQ == null || tempP == null) return false;
            if(tempP.val != tempQ.val) return false;
            queue1.add(tempQ.left);
            queue1.add(tempQ.right);
            queue2.add(tempP.left);
            queue2.add(tempP.right);
        }
        return true;
    }
}
