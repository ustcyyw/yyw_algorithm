package easy.Tree;

/**
 * @Time : 2020年2月20日13:57:42
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.Stack;

/**
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。树的高度为结点的最大深度
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 * <p>
 * 示例 1:
 * 给定二叉树 [3,9,20,null,null,15,7]
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 返回 true 。
 * <p>
 * 示例 2:
 * 给定二叉树 [1,2,2,3,3,null,null,4,4]
 * 1
 * / \
 * 2   2
 * / \
 * 3   3
 * / \
 * 4   4
 * 返回 false 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/balanced-binary-tree
 */
public class isBalanced110 {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了17.82%的用户
     * 内存消耗 :41.9 MB, 在所有 Java 提交中击败了5.01%的用户
     * 左右子树的高度相差小于不能保证平衡，因为有可能左右子树分别都不是平衡树，所以在保证高度差<1的情况下，还要保证左右子树都为平衡的。
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (Math.abs(height(root.left) - height(root.right)) < 2) {
            return isBalanced(root.left) && isBalanced(root.right);
        } else return false;
    }

    private int height(TreeNode x) {
        if (x == null) return 0;
        return Math.max(height(x.left), height(x.right)) + 1;
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了17.82%的用户
     * 内存消耗 :41.6 MB, 在所有 Java 提交中击败了6.35%的用户
     * 方法一的迭代版本。
     */
    public boolean isBalanced2(TreeNode root) {
        if (root == null) return true;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode curNode = stack.pop();
            if (curNode != null) {
                TreeNode left = curNode.left;
                TreeNode right = curNode.right;
                if (Math.abs(height(left) - height(right)) > 1)
                    return false;
                stack.push(left);
                stack.push(right);
            }
        }
        return true;
    }

    /**
     * 每个结点的深度只需要计算一次
     * 用结点深度为 -1 表示，该子树是不平衡的
     */
    public boolean isBalanced3(TreeNode root) {
        return depth(root) != -1;
    }
    private int depth(TreeNode x) {
        if (x == null) return 0;
        int leftDepth = depth(x.left);
        if (leftDepth == -1) return -1; // 左子树深度为-1 说明左子树不平衡，当前子树也不平衡，返回标识-1
        int rightDepth = depth(x.right);
        if (rightDepth == -1) return -1;
        return Math.abs(leftDepth - rightDepth) < 2 ? Math.max(leftDepth, rightDepth) + 1 : -1;
    }
}
