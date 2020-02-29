package easy.Tree;

/**
 * @Time : 2020年2月29日15:21:18
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.Stack;

/**
 * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
 *
 * 例如：
 * 输入: 二叉搜索树:
 *               5
 *             /   \
 *            2     13
 * 输出: 转换为累加树:
 *              18
 *             /   \
 *           20     13
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-bst-to-greater-tree
 */
public class convertBST {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.68%的用户
     * 内存消耗 :40.4 MB, 在所有 Java 提交中击败了47.51%的用户
     * 求和要求从右端加到左端，所以这是一个“反着”的中序遍历
     */
    private int sum;
    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        convert(root);
        return root;
    }
    private void convert(TreeNode x){
        if(x == null) return;
        convert(x.right);
        sum = sum + x.val;
        x.val = sum;
        convert(x.left);
    }

    /**
     * 使用迭代也可以
     * 执行用时 :3 ms, 在所有 Java 提交中击败了16.67%的用户
     * 内存消耗 :41 MB, 在所有 Java 提交中击败了36.50%的用户
     */
    public TreeNode convertBST2(TreeNode root) {
        int sum = 0;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null){
            while (node != null){
                stack.push(node);
                node = node.right;
            }
            node = stack.pop();
            sum = node.val + sum;
            node.val = sum;
            node = node.left;
        }
        return root;
    }
}
