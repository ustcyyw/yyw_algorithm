package easy.Tree;

/**
 * @Time : 2020年2月28日23:50:18
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import BaseClass.TreeNode;

/**
 * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 * 注意：两个节点之间的路径长度由它们之间的边数表示。
 *
 * 示例 1:
 * 输入:
 *               5
 *              / \
 *             4   5
 *            / \   \
 *           1   1   5
 * 输出:
 * 2
 *
 * 示例 2:
 * 输入:
 *               1
 *              / \
 *             4   5
 *            / \   \
 *           4   4   5
 * 输出:
 * 2
 * 注意: 给定的二叉树不超过10000个结点。 树的高度不超过1000。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-univalue-path
 */
public class longestUnivaluePath {
    /**
     * 执行用时 :10 ms, 在所有 Java 提交中击败了11.12%的用户
     * 内存消耗 :43.5 MB, 在所有 Java 提交中击败了5.02%的用户
     * 此方法借鉴543求最大半径的解法，对每一个结点都计算值与其相同的结点构成的树中的最大半径
     * 所有这些同值树中半径最大的，就是题目要求的相同值的最大路径。
     */
    private int res;
    public int longestUnivaluePath(TreeNode root) {
        res = 0;
        traversal(root);
        return res;
    }

    private void traversal(TreeNode x){
        if(x == null) return;
        traversal(x.left);
        height(x, x.val);
        traversal(x.right);
    }

    private int height(TreeNode x, int val){ // 截至到相同值的结点组成的树高
        if(x == null) return 0;
        if(x.val != val) return 0;
        int leftH = height(x.left, val);
        int rightH = height(x.right, val);
        res = Math.max(res, leftH + rightH);
        return Math.max(leftH, rightH) + 1;
    }

    /**
     * 官方标答
     * 执行用时 :3 ms, 在所有 Java 提交中击败了93.24%的用户
     * 内存消耗 :44 MB, 在所有 Java 提交中击败了5.16%的用户
     */
    public int longestUnivaluePath2(TreeNode root) {
        res = 0;
        sameHeight(root);
        return res;
    }

    /**
     * 以过某根结点的具有相同值构成的树的最大路径，等于左子树树高+右子树树高
     * 某结点的左子结点与其值不同，相当于左子树树高为0。
     * 函数返回与x值相同的树的树高。
     */
    private int sameHeight(TreeNode x){
        if(x == null) return 0;
        int sonLeft = sameHeight(x.left);
        int sonRight = sameHeight(x.right);
        int left = 0, right = 0;
        if(x.left != null && x.left.val == x.val)
            left = sonLeft + 1;
        if(x.right != null && x.right.val == x.val)
            right = sonRight + 1;
        res = Math.max(res, left + right);
        return Math.max(left, right);
    }
}
