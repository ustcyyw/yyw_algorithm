package easy.Tree;

/**
 * @Time : 2020年2月25日00:38:19
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import BaseClass.TreeNode;
import javafx.util.Pair;

import java.util.Stack;

/**
 * 计算给定二叉树的所有左叶子之和。
 * <p>
 * 示例：
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-of-left-leaves
 */
public class sumOfLeftLeaves404 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.4 MB, 在所有 Java 提交中击败了5.10%的用户
     */
    public int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeaves(root, false); // 只有一个根结点 则根结点不算左子叶
    }

    private int sumOfLeftLeaves(TreeNode root, boolean isLeftLeave) {
        if (root == null) return 0;
        if (root.left == null && root.right == null && isLeftLeave) return root.val;
        return sumOfLeftLeaves(root.left, true) + sumOfLeftLeaves(root.right, false);
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了14.25%的用户
     * 内存消耗 :37.4 MB, 在所有 Java 提交中击败了5.10%的用户
     */
    private int sumOfLeftLeaves2(TreeNode root){
        if(root == null) return 0;
        int res = 0;
        Stack<Pair<TreeNode, Boolean>> stack = new Stack<>();
        stack.add(new Pair<>(root, false));
        while (!stack.isEmpty()){
            Pair<TreeNode, Boolean> pair = stack.pop();
            TreeNode node = pair.getKey();
            if(node.left != null)
                stack.add(new Pair<>(node.left, true));
            if(node.right != null)
                stack.add(new Pair<>(node.right, false));
            if(node.left == null && node.right == null && pair.getValue())
                res += node.val;
        }
        return res;
    }
}
