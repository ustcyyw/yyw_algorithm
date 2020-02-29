package medium.Tree;

/**
 * @Time : 2020年2月28日13:46:32
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个二叉树，返回它的中序 遍历。
 * <p>
 * 示例:
 * 输入: [1,null,2,3]
 * 1
 * \
 * 2
 * /
 * 3
 * 输出: [1,3,2]
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal
 */
public class inorderTraversal {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.8 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        addMid(root, res);
        return res;
    }

    private void addMid(TreeNode root, List<Integer> res) {
        if (root == null)
            return;
        addMid(root.left, res);
        res.add(root.val);
        addMid(root.right, res);
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了66.30%的用户
     * 内存消耗 :37.8 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curNode = root;
        while (!stack.isEmpty() || curNode != null) { // 特别要注意第二个条件
            while (curNode != null) {
                stack.push(curNode);
                curNode = curNode.left;
            }
            curNode = stack.pop();
            res.add(curNode.val);
            curNode = curNode.right;
        }
        return res;
    }
}
