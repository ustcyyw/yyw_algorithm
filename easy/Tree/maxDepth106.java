package easy.Tree;

/**
 * @Time : 2020年2月19日23:18:58
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;
import javafx.util.Pair;

import java.util.Stack;

/**
 * 给定一个二叉树，找出其最大深度。
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回它的最大深度 3 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
 */
public class maxDepth106 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39 MB, 在所有 Java 提交中击败了12.47%的用户
     */
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 迭代 DFS
     *参考官方答案 BFS 其实也行，因为都是遍历了每个结点的深度
     */
    public int maxDepth2(TreeNode root) {
        if(root == null) return 0;
        int result = 0;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 1));
        while(!stack.empty()){
            Pair<TreeNode, Integer> curPair = stack.pop();
            TreeNode curNode = curPair.getKey();
            int curDepth = curPair.getValue();
            if(curNode != null){
                result = Math.max(result, curDepth);
                stack.push(new Pair<>(curNode.left, curDepth + 1)); // 从添加顺序看，是先走右子树到底部
                stack.push(new Pair<>(curNode.right, curDepth + 1));
            }
        }
        return result;
    }
}
