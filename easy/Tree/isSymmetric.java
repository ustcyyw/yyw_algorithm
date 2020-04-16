package easy.Tree;

import BaseClass.TreeNode;

import java.util.Stack;

/**
 * @Time : 2020年2月27日23:37:03
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个二叉树，检查它是否是镜像对称的。
 *  
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *  
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *  
 *
 * 进阶：
 * 你可以运用递归和迭代两种方法解决这个问题吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/symmetric-tree
 */
public class isSymmetric {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.6 MB, 在所有 Java 提交中击败了29.11%的用户
     * 递归版本
     */
    public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        return isSame(invert(root.left), root.right);
    }

    private TreeNode invert(TreeNode x){
        if(x == null) return null;
        TreeNode right = invert(x.right);
        x.right = invert(x.left);
        x.left = right;
        return x;
    }

    private boolean isSame(TreeNode p, TreeNode q){
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.val != q.val) return false;
        else return isSame(p.left, q.left) && isSame(p.right, q.right);
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了10.60%的用户
     * 内存消耗 :38 MB, 在所有 Java 提交中击败了15.86%的用户
     * 迭代的解法
     */
    public boolean isSymmetric2(TreeNode root) {
        if(root == null) return true;
        Stack<TreeNode> left = new Stack<>();
        Stack<TreeNode> right = new Stack<>();
        left.add(root);
        right.add(root);
        while(!left.isEmpty()){
            TreeNode tempL = left.pop();
            TreeNode tempR = right.pop();
            if(tempL != null && tempR != null){
                if(tempL.val != tempR.val)
                    return false;
                left.add(tempL.right);
                left.add(tempL.left);
                right.add(tempR.left);
                right.add(tempR.right);
            }
            if((tempL == null && tempR != null) || (tempL != null && tempR == null))
                return false;
        }
        return true;
    }
}
