package easy.Tree;

/**
 * @Time : 2020年2月28日15:38:13
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import BaseClass.TreeNode;
import javafx.util.Pair;

import java.util.Stack;

/**
 * 给定一个二叉树，找出其最小深度。
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例:
 *
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最小深度  2.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-depth-of-binary-tree
 */
public class minDepth {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.6 MB, 在所有 Java 提交中击败了5.06%的用户
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        return min(root);
    }

    private int min(TreeNode root){
        if(root.left == null && root.right == null)
            return 1;
        if(root.left  == null)
            return min(root.right) + 1;
        if(root.right == null)
            return min(root.left) + 1;
        return Math.min(min(root.left), min(root.right)) + 1;
    }

    /**
     * 执行用时 :5 ms, 在所有 Java 提交中击败了33.05%的用户
     * 内存消耗 :40.9 MB, 在所有 Java 提交中击败了5.06%的用户
     */
    public int minDepth2(TreeNode root) {
        if(root == null) return 0;
        int result = Integer.MAX_VALUE;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.add(new Pair<>(root, 1));
        while(!stack.isEmpty()){
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int depth = pair.getValue();
            if(node.left != null)
                stack.push(new Pair<>(node.left, depth + 1));
            if(node.right != null)
                stack.push(new Pair<>(node.right, depth + 1));
            if(node.left == null && node.right == null)
                result = Math.min(result, depth);
        }
        return result;
    }
}
