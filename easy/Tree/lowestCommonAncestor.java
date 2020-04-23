package easy.Tree;

/**
 * @Time : 2020年2月29日16:01:14
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

/**
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
 * 一个节点也可以是它自己的祖先）。”
 *
 * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
 * 示例 1:
 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * 输出: 6
 * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
 *
 * 示例 2:
 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * 输出: 2
 * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
 *
 * 说明:
 * 所有节点的值都是唯一的。
 * p、q 为不同节点且均存在于给定的二叉搜索树中。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree
 */
public class lowestCommonAncestor {
    /**
     * 执行用时 :7 ms, 在所有 Java 提交中击败了78.21%的用户
     * 内存消耗 :41.9 MB, 在所有 Java 提交中击败了5.02%的用户
     * 这个做法是把一般二叉树的做法 将调整p，q使得递归函数的第一个参数是值较小的结点，从而简化了部分条件
     * 但没有充分利用BST的性质
     */
    private TreeNode res;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        res = null;
        if(q.val > p.val)
            contains(root, p, q);
        else
            contains(root, q, p);
        return res;
    }

    private boolean contains(TreeNode x, TreeNode p, TreeNode q){
        if(x == null) return false;
        if(x.val < p.val)
            return contains(x.right, p, q);
        if(x.val > q.val)
            return contains(x.left, p, q);
        boolean leftCon = contains(x.left, p, q);
        boolean rightCon = contains(x.right, p, q);
        if((x == p && rightCon) || (x == q && leftCon) || (leftCon && rightCon)) {
            res = x;
            return true;
        }
        return x == p || x == q || leftCon || rightCon;
    }

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val > p.val && root.val > q.val)
            return lowestCommonAncestor2(root.left, p, q);
        if(root.val <p.val && root.val < q.val)
            return lowestCommonAncestor2(root.right, p, q);
        return root;
    }

    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.1 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if(p.val > q.val) return find(root, q, p);
        else return find(root, p, q);
    }

    private TreeNode find(TreeNode x, TreeNode p, TreeNode q){
        if(x == p || x == q) return x;
        if(p.val < x.val && q.val > x.val) return x;
        else if(q.val < x.val) return find(x.left, p, q);
        return find(x.right, p ,q);
    }
}
